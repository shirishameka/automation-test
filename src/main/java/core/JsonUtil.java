package core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

@Slf4j
public class JsonUtil {
    public static JSONObject toJsonObject(Response response) {
        return new JSONObject(response.asString());
    }

    public static JSONObject toJsonObject(Object object) {
        return new JSONObject(getObjectAsString(object));
    }

    public static JSONArray toJsonArray(Response response) {
        return new JSONArray(response.asString());
    }

    public static JSONArray toJsonArray(Object object) {
        return new JSONArray(getObjectAsString(object));
    }

    /**
     * get object as a string
     * @param object
     * @return
     */
    private static String getObjectAsString(Object object) {
        String objectAsString = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            objectAsString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectAsString;
    }

    /**
     * to pojo
     * @param jsonString
     * @param classType
     * @param <T>
     * @return
     */
    public static <T> T toPOJO(String jsonString, Class<T> classType) {
        Object object = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            object = mapper.readValue(jsonString, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean compareJsonObjectsStrictOrder(JSONObject obj1, JSONObject obj2) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node1 = null;
        JsonNode node2 = null;
        try {
            node1 = mapper.readTree(obj1.toString());
            node2 = mapper.readTree(obj2.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node1.equals(node2);
    }

    /**
     * compare json obs if they are equal
     * @param ob1
     * @param ob2
     * @return
     */
    public static boolean areEqualJsons(Object ob1, Object ob2) {
        Object obj1Converted = convertJsonElement(ob1);
        Object obj2Converted = convertJsonElement(ob2);
        return obj1Converted.equals(obj2Converted);
    }

    /**
     * convert json Element
     * @param elem
     * @return
     */
    private static Object convertJsonElement(Object elem) {
        if (elem instanceof JSONObject) {
            JSONObject obj = (JSONObject) elem;
            Iterator<String> keys = obj.keys();
            Map<String, Object> jsonMap = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                jsonMap.put(key, convertJsonElement(obj.get(key)));
            }
            return jsonMap;
        } else if (elem instanceof JSONArray) {
            JSONArray arr = (JSONArray) elem;
            Set<Object> jsonSet = new HashSet<>();
            for (int i = 0; i < arr.length(); i++) {
                jsonSet.add(convertJsonElement(arr.get(i)));
            }
            return jsonSet;
        } else {
            return elem;
        }
    }

}