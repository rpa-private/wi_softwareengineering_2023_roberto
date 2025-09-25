package ch.fhnw.richards.Week_09.annotations.solutions;

@Todo(content = "Person is incomplete, we need to finish it")
public class Person {
    @Todo(content = "The ID should be final")
    private int ID;
    @Todo(content = "We really need a first-name and a last-name")
    private String name;

    @Todo(content = "Should use the parameter!")
    public Person(int ID) {
        this.ID = 0;
    }

    @Todo(content = "Should actually return the name")
    public String getName() {
        return "Who is this?";
    }
}
