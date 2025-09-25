package ch.fhnw.richards.Week_03.recursion;

import java.util.Arrays;
import java.util.List;

public class FindInList {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Anna", "Beat", "Colin", "Davide", "Elisa", "Frank", "Georg", "Hanna");
        System.out.println( "Colin found? " + findInList(names, "Colin") );
        System.out.println( "Xavier found? " + findInList(names, "Xavier") );
    }

    private static boolean findInList(List<String> list, String target) {
        return findInList(list, target, 0, list.size()-1);
    }

    private static boolean findInList(List<String> list, String target, int start, int end) {
        if (start > end) { // first base case
            return false;
        } else {
            int middle = (start + end) / 2;
            int compareResult = target.compareTo(list.get(middle));
            if (compareResult == 0) { // second base case
                return true;
            } else if (compareResult < 0) { // first recursive case
                return findInList(list, target, start, middle-1);
            } else { // second recursive case
                return findInList(list, target, middle+1, end);
            }
        }
    }
}
