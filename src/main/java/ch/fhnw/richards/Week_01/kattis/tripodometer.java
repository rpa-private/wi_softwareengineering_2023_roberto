package ch.fhnw.richards.Week_01.kattis;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class tripodometer {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numTrips = in.nextInt();
        ArrayList<Integer> tripList = new ArrayList<>();

        int total = 0;
        for (int i = 0; i < numTrips; i++) {
            int trip = in.nextInt();
            total += trip;
            tripList.add(trip);
        }

        // Convert to set, to remove duplicates
        HashSet<Integer> tripSet = new HashSet<>(tripList);

        // Subtract each trip from the total, creating a new collection
        int finalTotal = total;
        List<Integer> resultList = tripSet.stream()
                .map( trip -> (finalTotal - trip) )
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        System.out.println(resultList.size() + " ");
        resultList.forEach(it -> System.out.print(it + " "));
    }
}
