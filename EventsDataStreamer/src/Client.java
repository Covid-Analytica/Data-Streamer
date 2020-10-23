import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Client {

    static int i = 0;

    public static void main(String[] args) {
        Client groupsClient = new Client();
//        groupsClient.getGroupsPerState(20);
//        groupsClient.getEvents("Control-Irrational-Emotions-Find-Happiness-and-Confidence");
        groupsClient.getEventsPerGroup();
    }

    public void getGroupsPerState(int pageSize) {
        for (String us_state : Property.US_STATES) {
            if (i < 5) {
                i++;
                continue;
            }
            System.out.println("STATE: " + us_state);
            this.getGroups(us_state, pageSize);
            return;
        }
    }


    public void getGroups(String usState, int pageSize) {
        String path = "https://api.meetup.com/find/groups?country=United%20States&location=" + usState + "&page=" + Integer.toString(pageSize);
        System.out.println(path);
        try {
            // Sending get request
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + Property.BEARER_TOKEN);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;

            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            // printing result from response
            System.out.println("Response:-" + response.toString());

            //save groups to mongo.
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase database = mongoClient.getDatabase("covidData");
            System.out.println("db created");
//            boolean auth = database.authenticate("username", "pwd".toCharArray());
            mongoClient.getDatabaseNames().forEach(System.out::println);
            System.out.println("^^^list of dbs.");
            MongoCollection<Document> collection = database.getCollection("groups");
            System.out.println("created collection");

//            JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonArray();
            Group[] groups = new Gson().fromJson(response.toString(), Group[].class);
            List<Document> jsonList = new ArrayList<Document>();
            for (Group group : groups) {
                Document document = Document.parse(new Gson().toJson(group));
                jsonList.add(document);
            }
            collection.insertMany(jsonList);
            System.out.println("Saved to mongo.");
            mongoClient.close();

        } catch (IOException e) {
            System.out.println(">>>>Failed Groups import");
            e.printStackTrace();
        }

    }
    public void getEventsPerGroup(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("covidData");
        MongoCollection<Document> collection = database.getCollection("groups");

        BasicDBObject allQuery = new BasicDBObject();
        FindIterable<Document> documents = collection.find(allQuery, Document.class);
        for (Document document : documents) {
            String urlname = document.getString("urlname");
            System.out.println();
            System.out.println(">>>>>>>>>>"+urlname);
            System.out.println();
            System.out.println(getEvents(urlname, mongoClient));
        }
        mongoClient.close();
    }

    public String getEvents(String groupName, MongoClient mongoClient) {
        try {


            String path = "https://api.meetup.com/" + groupName + "/events";
            System.out.println(path);
            // Sending get request
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + Property.BEARER_TOKEN);

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;

            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            in.close();
            // printing result from response
            System.out.println("Response:-" + response.toString());
            return response.toString();

        } catch (IOException e) {
            System.out.println(">>>>Failed");
            e.printStackTrace();
        }
        return null;
    }

    /*public static void try1(){

        String state = "";
        String path = "https://api.meetup.com/find/groups?zip=90001&page=200000";

        //Creating a HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpGet httpget = new HttpGet(path);


        //Executing the Get request
        HttpResponse httpresponse;

        {
            try {
                httpresponse = httpclient.execute(httpget);
            } catch (IOException e) {
                System.out.println("Error while making request");
            }
        }

        Scanner sc = new Scanner(httpresponse.getEntity().getContent());

        //Printing the status line
        System.out.println(httpresponse.getStatusLine());
        while(sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }*/
}
