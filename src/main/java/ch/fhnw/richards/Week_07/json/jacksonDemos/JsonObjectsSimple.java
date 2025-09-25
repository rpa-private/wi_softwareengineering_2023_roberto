package ch.fhnw.richards.Week_07.json.jacksonDemos;

import ch.fhnw.richards.Week_07.json.dataClasses.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectsSimple {
    public static void main(String[] args) {
        Person p = new Person(13);
        p.setName("Freddy Mercury");

        // Generate JSON from object
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("JSON generated: " + json);

        // Generate object from JSON
        Person p2 = null;
        try {
            p2 = objectMapper.readValue(json, Person.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Objects are equal?  " + p.equals(p2));
    }
}
