package ch.fhnw.richards.Week_01.kattis;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class busyschedule {
    /**
     * A very simple class
     */
    private static class Appointment {
        public LocalTime time;
        public String text;

        public Appointment(LocalTime time, String text) { this.time = time; this.text = text; }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int numAppts = Integer.parseInt(in.nextLine().trim());
        while (numAppts > 0) {
            ArrayList<Appointment> appts = new ArrayList<>();
            for (int i = 1; i <= numAppts; i++) {
                String text = in.nextLine();
                String[] parts = text.split(" ");
                String[] parts2 = parts[0].split(":");
                int hours = Integer.parseInt(parts2[0]);
                int min = Integer.parseInt(parts2[1]);
                boolean isAM = parts[1].equals("a.m.");

                if (hours == 12 && isAM) hours = 0; // 12:00 am == 00:00 and 12:00 pm = 12:00
                if (!isAM && hours < 12) hours += 12; // Otherwise just add 12 if pm
                appts.add(new Appointment(LocalTime.of(hours,min), text));
            }

            Collections.sort(appts, Comparator.comparing(a -> a.time));

            for (Appointment appt : appts) System.out.println(appt.text);
            System.out.println();

            // Next set of appointments
            numAppts = Integer.parseInt(in.nextLine().trim());
        }
    }
}
