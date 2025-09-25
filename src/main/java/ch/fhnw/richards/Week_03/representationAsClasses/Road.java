package ch.fhnw.richards.Week_03.representationAsClasses;

public class Road {
    private String name;
    private Intersection end1;
    private Intersection end2;

    public Road(String name, Intersection end1, Intersection end2) {
        this.name = name;
        this.end1 = end1;
        this.end2 = end2;
    }

    public String getName() {
        return name;
    }

    public Intersection getEnd1() {
        return end1;
    }

    public Intersection getEnd2() {
        return end2;
    }
}
