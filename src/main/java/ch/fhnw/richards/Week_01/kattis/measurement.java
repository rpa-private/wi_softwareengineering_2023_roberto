package ch.fhnw.richards.Week_01.kattis;

import java.util.HashMap;
import java.util.Scanner;

public class measurement {
    private static final HashMap<String, Integer> units = new HashMap<>();
    public static void main(String[] args) {
        // Add all the conversions from units (and their abbreviations) into thous
        units.put("thou" , 1);             units.put("th" , 1);
        units.put("inch" , 1000);          units.put("in" , 1000);
        units.put("foot" , 12000);         units.put("ft" , 12000);
        units.put("yard" , 36000);         units.put("yd" , 36000);
        units.put("chain" , 792000);       units.put("ch" , 792000);
        units.put("furlong" , 7920000);    units.put("fur" , 7920000);
        units.put("mile" , 63360000);      units.put("mi" , 63360000);
        units.put("league" , 190080000);   units.put("lea" , 190080000);

        Scanner in = new Scanner(System.in);
        String[] parts = in.nextLine().split(" ");

        double distance = Long.parseLong(parts[0]);
        String start = parts[1];
        String end = parts[3];

        double result = distance * units.get(start) / units.get(end);
        System.out.println(result);
    }
}
