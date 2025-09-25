package ch.fhnw.richards.Week_01.kattis;

import java.util.Scanner;

/**
 * We must implement our own data structure, including required operations.
 * This includes an inner class to represent individual elements.
 * <p>
 * To keep it simple, we include our main method directly in the class.
 * <p>
 * Note: Some of the test cases are large: it is ok if the later test cases
 * give you "Time Limit Exceeded". We want a "pretty" implementation, not
 * a super-fast one.
 */
public class teque<T> {
    private static class tequeElement<T> {
        tequeElement<T> prev;
        tequeElement<T> next;
        T value;

        public tequeElement(tequeElement<T> prev, tequeElement<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    ;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numCommands = in.nextInt();
        in.nextLine();

        teque<Integer> teque = new teque<>();

        for (int i = 0; i < numCommands; i++) {
            String[] lineParts = in.nextLine().split(" ");
            Integer value = Integer.parseInt(lineParts[1]);
            String command = lineParts[0];

            if (command.equals("push_back"))
                teque.push_back(value);
            else if (command.equals("push_front"))
                teque.push_front(value);
            else if (command.equals("push_middle"))
                teque.push_middle(value);
            else // "get"
                System.out.println(teque.get(value));
        }
    }


    // Object attributes
    int size = 0;
    tequeElement<T> head = null;
    tequeElement<T> tail = null;

    // Optimize access to middle of list (not functionally necessary)
    tequeElement<T> middle = null;
    int middlePos = -1;

    public void push_back(T value) {
        tequeElement<T> newElt = new tequeElement(tail, null, value);
        if (head == null) {
            head = newElt;
            middle = newElt;
            middlePos = 0;
        } else {
            tail.next = newElt;
        }
        tail = newElt;
        size++;
    }

    public void push_front(T value) {
        tequeElement<T> newElt = new tequeElement(null, head, value);
        if (tail == null) {
            tail = newElt;
            middle = newElt;
            middlePos = 0;
        } else {
            head.prev = newElt;
            middlePos++;
        }
        head = newElt;
        size++;
    }

    public void push_middle(T value) {
        tequeElement<T> newElt = new tequeElement(null, null, value);
        if (head == null) {
            head = newElt;
            tail = newElt;
            middle = newElt;
            middlePos = 0;
        } else if (size == 1) {
            newElt.prev = head;
            head.next = newElt;
            tail = newElt;
            middle = newElt;
            middlePos = 1;
        } else {
            int prevIndex = (size + 1) / 2 - 1; // Position of element before insertion
            while (middlePos < prevIndex) {
                middle = middle.next;
                middlePos++;
            }
            while (middlePos > prevIndex) {
                middle = middle.prev;
                middlePos--;
            }
            newElt.next = middle.next;
            newElt.prev = middle;
            middle.next.prev = newElt;
            middle.next = newElt;
        }
        size++;
    }

    public T get(int target) {
        // Functionally unnecessary, but we optimize by finding whether the desired position is closer to the start, middle or end
        int endDiff = size - target;
        int middleDiff = Math.abs(size - middlePos);

        if (target < middleDiff) {
            return getForward(target, 0, head);
        } else if (middleDiff < endDiff) {
            if (target <= middlePos) {
                return getForward(target, middlePos, middle);
            } else {
                return getBackward(target, middlePos, middle);
            }
        } else { // endDiff
            return getBackward(target, size - 1, tail);
        }

    }

    private T getForward(int target, int startPos, tequeElement<T> startObj) {
        tequeElement<T> cursor = startObj;
        int pos = startPos;
        while (pos < target) {
            cursor = cursor.next;
            pos++;
        }
        return cursor.value;
    }

    private T getBackward(int target, int startPos, tequeElement<T> startObj) {
        tequeElement<T> cursor = startObj;
        int pos = startPos;
        while (pos > target) {
            cursor = cursor.prev;
            pos--;
        }
        return cursor.value;
    }
}
