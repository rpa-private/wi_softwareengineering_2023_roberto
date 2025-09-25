package ch.fhnw.richards.Week_09.annotations.toString;

public class Main {
    public static void main(String[] args) {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        System.out.println(ToStrings.toString(rect));
    }
}
