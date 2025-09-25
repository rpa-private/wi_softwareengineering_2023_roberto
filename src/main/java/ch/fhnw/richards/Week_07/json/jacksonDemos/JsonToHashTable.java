package ch.fhnw.richards.Week_07.json.jacksonDemos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Storing the JSON content in a HashMap is useful, when we are receiving data for which we
 * do not have a corresponding class. For example, an industrial machine may send sensor data
 * as JSON - possibly dozens of values - and we only care about a few of these. Rather than
 * creating a class with an attribute for each value, it is easier just to pick the values we
 * want out of a HashMap. Typically, the JSON data will be in <String, String> format, which
 * is what we look at in this example.
 */
public class JsonToHashTable {
    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("sensor1", "1.111");
        data.put("sensor2", "22.22");
        data.put("sensor3", "333.3");

        // Generate JSON from the Map
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("JSON generated: " + json);

        // Generate map from JSON
        // First, we have to describe the kind of Map that we want
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
        Map<String, String> data2 = null;
        try {
            data2 = mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // Check that the contents are the same
        boolean ok = data.size() == data2.size() &&
                data.get("sensor1").equals(data2.get("sensor1")) &&
                data.get("sensor2").equals(data2.get("sensor2")) &&
                data.get("sensor3").equals(data2.get("sensor3"));
        System.out.println("Data is identical? " + ok);
    }
}
