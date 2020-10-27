import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataLoader {
    //loads data from db.

    public static List<CovidReport> loadCovidReportsFromDB(){
        MongoClient mongoClient = new MongoClient(Property.mongoHost, Property.mongoPort);
        MongoDatabase database = mongoClient.getDatabase(Property.mongoDatabaseName);
        MongoCollection<Document> collection = database.getCollection(Property.MongoCollectionCovidReport);

        BasicDBObject whereQuery = new BasicDBObject();

        //add state to use
        whereQuery.put("state", "NY");
        FindIterable<Document> documents = collection.find(whereQuery);

        List<CovidReport> covidReportList = new ArrayList<>();

        for (Document document : documents) {
            System.out.println();
//            System.out.println(document.getString("_id"));
            CovidReport covidReport = new CovidReport();
            covidReport.date = document.getInteger("date");
            covidReport.state = document.getString("state");
            covidReport.cases = document.getInteger("cases");
            String rsvpStr = document.getString("rsvp");
            System.out.println(rsvpStr);
            if (rsvpStr != null && rsvpStr.contains("Some")) {
                covidReport.rsvp = Integer.parseInt(rsvpStr.substring(5, rsvpStr.length()-1));
                System.out.println(covidReport.rsvp);
                System.out.println();
            } else {
                covidReport.rsvp = 0;
            }
            covidReportList.add(covidReport);
        }
        mongoClient.close();

        covidReportList.sort(new Comparator<CovidReport>() {
            @Override
            public int compare(CovidReport o1, CovidReport o2) {
                if (o1.date == o2.date) {
                    return 0;
                } else if (o1.date > o2.date) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (CovidReport covidReport : covidReportList) {
            System.out.println(covidReport);
        }
        return covidReportList;
    }
}