package ch.fhnw.richards.Week_01.kattis;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Use a map to represent the relationship between normal letters
 * and the new alphabet. The painful part is initializing the map.
 */
public class anewalphabet {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().toLowerCase();
        Map<Character, String> map = getMap();
        for (char c : input.toCharArray()) {
            System.out.print( Character.isLetter(c) ? map.get(c) : c );
        }
    }

    private static Map<Character, String> getMap() {
        HashMap<Character, String> map = new HashMap<>();
        map.put('a' , "@");          map.put('b' , "8");        map.put('c' , "(");        map.put('d' , "|)");
        map.put('e' , "3");          map.put('f' , "#");        map.put('g' , "6");        map.put('h' , "[-]");
        map.put('i' , "|");          map.put('j' , "_|");       map.put('k' , "|<");       map.put('l' , "1");
        map.put('m' , "[]\\/[]");    map.put('n' , "[]\\[]");   map.put('o' , "0");        map.put('p' , "|D");
        map.put('q' , "(,)");        map.put('r' , "|Z");       map.put('s' , "$");        map.put('t' , "']['");
        map.put('u' , "|_|");        map.put('v' , "\\/");      map.put('w' , "\\/\\/");   map.put('x' , "}{");
        map.put('y' , "`/");         map.put('z' , "2");
        return map;
    }
}
