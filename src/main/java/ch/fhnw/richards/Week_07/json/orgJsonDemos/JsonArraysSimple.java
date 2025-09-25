package ch.fhnw.richards.Week_07.json.orgJsonDemos;

import org.json.JSONArray;

public class JsonArraysSimple {
    public static void main(String[] args) {
        int[] values = {1, 2, 3};

        // Generate JSON from array
        org.json.JSONArray jsonArray = new JSONArray();
        for (int value : values) {
            jsonArray.put(value);
        }
        String json = jsonArray.toString();
        System.out.println("JSON generated: " + json);

        // Generate array from JSON
        int[] values2 = new int[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            values2[i] = jsonArray.getInt(i);
        }

        // Verify equality
        boolean equals = values.length == values2.length;
        for (int i = 0; i < values.length; i++) {
            equals &= values[i] == values2[i];
        }
        System.out.println("Objects are equal?  " + equals);
    }
}
