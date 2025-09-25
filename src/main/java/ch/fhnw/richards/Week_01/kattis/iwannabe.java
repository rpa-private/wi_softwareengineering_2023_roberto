package ch.fhnw.richards.Week_01.kattis;

import java.util.*;

public class iwannabe {
    /**
     * You could also define this class in a separate file. Putting it here
     * makes it easier to submit to Kattis. We keep the class simple, by
     * making all attributes public.
     *
     * This class *must* use longs, because the problem specifies *unsigned*
     * 32-bit integers.
     */
    private static class pokenom {
        private static int nextID = 0;
        public final int id = nextID++;
        public long attack;
        public long defense;
        public long health;

        public pokenom(long attack, long defense, long health) {
            this.attack = attack;
            this.defense = defense;
            this.health = health;
        }

        // Override equals to use id
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            pokenom pokenom = (pokenom) o;
            return id == pokenom.id;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        in.nextLine();

        ArrayList<pokenom> pokenoms = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            long a = in.nextLong();
            long d = in.nextLong();
            long h = in.nextLong();
            in.nextLine();
            pokenoms.add(new pokenom(a, d, h));
        }

        // As we select them, put them into a Set, which removes duplicates
        Set<pokenom> selected = new HashSet<>();

        // Sort and pick the last k (those have the highest values)
        Collections.sort(pokenoms, Comparator.comparing(a -> a.attack));
        for (int i = 0; i < k; i++) {
            selected.add(pokenoms.get(pokenoms.size() - i - 1));
        }
        Collections.sort(pokenoms, Comparator.comparing(a -> a.defense));
        for (int i = 0; i < k; i++) {
            selected.add(pokenoms.get(pokenoms.size() - i - 1));
        }
        Collections.sort(pokenoms, Comparator.comparing(a -> a.health));
        for (int i = 0; i < k; i++) {
            selected.add(pokenoms.get(pokenoms.size() - i - 1));
        }
        System.out.println(selected.size());
    }

}
