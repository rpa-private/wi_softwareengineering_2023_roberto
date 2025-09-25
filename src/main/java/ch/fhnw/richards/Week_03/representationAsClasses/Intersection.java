package ch.fhnw.richards.Week_03.representationAsClasses;

import java.util.ArrayList;

public class Intersection {
    private String name;
    private final ArrayList<Road> roads = new ArrayList<>();

    public Intersection(String name, Road... roads) {
        this.name = name;
        for (Road road : roads) this.roads.add(road);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }
}
