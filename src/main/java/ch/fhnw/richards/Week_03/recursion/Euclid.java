package ch.fhnw.richards.Week_03.recursion;

public class Euclid {
    public static void main(String[] args) {
        System.out.println("GCD of 568 and 208 is: " + gcd(568, 208));
    }

    // Given X >= y)
    public static long gcd(long x, long y) {
        if (y == 0) {
            return x;
        } else {
            return gcd(y, x % y);
        }
    }
}
