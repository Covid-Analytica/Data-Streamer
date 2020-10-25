import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        Client groupsClient = new Client();
        if (Property.getTestDataOnly) {
            groupsClient.getGroupsPerState(10);
            groupsClient.getEventsPerGroup();
        } else {
            groupsClient.getGroupsPerState(100);
            groupsClient.getEventsPerGroup();
        }
    }

    public void getGroupsPerState(int pageSize) {
        int i = 0;
        for (String us_state : Property.US_STATES) {
            System.out.println("STATE: " + us_state);
            this.getGroups(us_state, pageSize);
            i++;
            if (i == 5 && Property.getTestDataOnly) {
                break;
            }
            //return;
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

            MongoClient mongoClient;
            if (StringUtils.isNotBlank(Property.mongoUser) && StringUtils.isNotBlank(Property.mongoPassword)) {
                MongoCredential credential = MongoCredential.createCredential(Property.mongoUser, Property.mongoDatabaseName, Property.mongoPassword.toCharArray());
                mongoClient = new MongoClient(new ServerAddress(Property.mongoHost, Property.mongoPort), Arrays.asList(credential));
            } else {
                mongoClient = new MongoClient(Property.mongoHost, Property.mongoPort);
            }
            MongoDatabase database = mongoClient.getDatabase(Property.mongoDatabaseName);
            System.out.println("db created");
            mongoClient.getDatabaseNames().forEach(System.out::println);
            System.out.println("^^^list of dbs.");
            MongoCollection<Document> collection = database.getCollection(Property.MongoCollectionGroup);
            System.out.println("created collection");

//            JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonArray();
            Group[] groups = new Gson().fromJson(response.toString(), Group[].class);
            if (groups.length > 1) {
                List<Document> jsonList = new ArrayList<Document>();
                for (Group group : groups) {
                    Document document = Document.parse(new Gson().toJson(group));
                    jsonList.add(document);
                }
                collection.insertMany(jsonList);
            }
            System.out.println("Saved to mongo.");
            mongoClient.close();

        } catch (IOException e) {
            System.out.println(">>>>Failed Groups import");
            e.printStackTrace();
        }

    }

    public void getEventsPerGroup() {
        MongoClient mongoClient = new MongoClient(Property.mongoHost, Property.mongoPort);
        MongoDatabase database = mongoClient.getDatabase(Property.mongoDatabaseName);
        MongoCollection<Document> collection = database.getCollection(Property.MongoCollectionGroup);
        BasicDBObject allQuery = new BasicDBObject();
        FindIterable<Document> documents = collection.find(allQuery, Document.class);
        for (Document document : documents) {
            Group group = new Group();
            group.urlname = document.getString("urlname");
            group.state = document.getString("state");
            group.id = document.getInteger("id");

            System.out.println();
            System.out.println(">>>>>>>>>>" + group.urlname);
            System.out.println(">>>>>>>>>>" + group.id);
            System.out.println();
            System.out.println(getEvents(database, group));
        }
        System.out.println("Saved, closing mongo connection");
        mongoClient.close();
    }

    public String getEvents(MongoDatabase database, Group group) {
        try {
            String path = "https://api.meetup.com/" + group.urlname + "/events?no_earlier_than=2020-02-15T00:00:00.000&status=past";
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

            MongoCollection<Document> collection = database.getCollection(Property.MongoCollectionEvents);
            Event[] events = new Gson().fromJson(response.toString(), Event[].class);
            if (events.length > 0) {
                List<Document> jsonList = new ArrayList<Document>();
                for (Event event : events) {
                    event.group_id = group.id;
                    event.urlname = group.urlname;
                    event.state = group.state;
                    event.dt_time = new Date(event.time);
                    SimpleDateFormat sdf = new SimpleDateFormat(Property.mongoDateFormat);
                    String eventDate = sdf.format(new Date(event.time));
                    event.st_time = Integer.parseInt(eventDate);

                    if (event.is_online_event) {
                        continue;
                    }
                    Document document = Document.parse(new Gson().toJson(event));
                    jsonList.add(document);
                }
                if (jsonList.size() > 0) {
                    collection.insertMany(jsonList);
                }
            }

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
