package ch.fhnw.richards.Week_03.recursion;

public class Factorial {
    public static void main(String[] args) {
        System.out.println("Factorial of 6 is: " + factorial(6));
    }

    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
