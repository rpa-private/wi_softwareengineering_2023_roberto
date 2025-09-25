package ch.fhnw.richards.Week_07.json.orgJsonDemos;

import ch.fhnw.richards.Week_07.json.dataClasses.Person;
import org.json.JSONObject;

public class JsonObjectsSimple {
    public static void main(String[] args) {
        Person p = new Person(13);
        p.setName("Freddy Mercury");

        // Generate JSON from object
        org.json.JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", p.getId());
        jsonObject.put("name", p.getName());
        String json = jsonObject.toString();
        System.out.println("JSON generated: " + json);

        // Generate object from JSON
        Person p2 = new Person(jsonObject.getInt("id"));
        p2.setName(jsonObject.getString("name"));

        System.out.println("Objects are equal?  " + p.equals(p2));
    }
}
