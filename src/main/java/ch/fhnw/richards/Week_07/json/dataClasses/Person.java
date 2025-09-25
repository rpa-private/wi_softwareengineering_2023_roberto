package ch.fhnw.richards.Week_07.json.dataClasses;

public class Person {
    // Jackson requires attributes to begin with a small letter, per Java naming conventions
    private int id;
    private String name;

    // Jackson requires a default constructor. This means you cannot have final attributes
    // initialized in your constructor. For such attributes, simply omit a setter-method
    public Person() {} // Required for Jackson
    public Person(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            Person p = (Person) o;
            return this.id == p.id && this.name.equals(p.name);
        }
        return false;
    }
}
