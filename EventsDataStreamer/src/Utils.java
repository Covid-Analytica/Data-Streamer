import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Document> getJSONArray(String jsonArray){
        List<Document> jsonList = new ArrayList<Document>();
        net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(jsonArray);
        for (Object object : array) {
            net.sf.json.JSONObject jsonStr = (net.sf.json.JSONObject) JSONSerializer.toJSON(object);
            Document jsnObject = Document.parse(jsonStr.toString());
            jsonList.add(jsnObject);
        }
        return jsonList;
    }

    public static List<Document> getJSONArray(Object[] array){
        List<Document> jsonList = new ArrayList<Document>();
        for (Object object : array) {
            net.sf.json.JSONObject jsonStr = (net.sf.json.JSONObject) JSONSerializer.toJSON(object);
            Document jsnObject = Document.parse(jsonStr.toString());
            jsonList.add(jsnObject);
        }
        return jsonList;
    }

}

