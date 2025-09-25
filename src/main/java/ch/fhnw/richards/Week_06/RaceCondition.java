package ch.fhnw.richards.Week_06;

/**
 * This program demonstrates the need for synchronization. It creates several
 * threads that communicate via a common object. Theoretically, the program
 * ought to count from 1 to 100 (each thread doing part of the work).
 *
 * @author Brad Richards
 *
 */
class RaceCondition extends Thread {

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        CentralCounter centralCounter = new CentralCounter();
        for (int i = 0; i < 10; ++i) {
            threads[i] = new RaceCondition("Counter-" + i, centralCounter);
        }
        for (Thread t : threads) {
            t.start();
        }
    }

    // Object attributes
    private String threadName;
    private CentralCounter centralCounter;

    public RaceCondition(String name, CentralCounter comm) {
        super(name);
        this.threadName = name;
        this.centralCounter = comm;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Count " + centralCounter.increment() + " (" + threadName + ")");
        }
    }

    private static class CentralCounter {
        private int counter;

        CentralCounter() {
            counter = 0;
        }

        int increment() {
            int startValue = counter;
            int result = expensiveCalculations(startValue);
            // We finally have our result (really just ++)
            counter = ++startValue;
            return startValue;
        }

        private int expensiveCalculations(int in) {
            // Lots of time-consuming calculations, as though we
            // actually had something to do.
            double x = 1.0, y = 0.0, z = 0.0;
            for (int i = 0; i < 10000; ++i) {
                x = Math.sin((x * i % 35) * 1.13);
                y = Math.log(x + 10.0);
                z = Math.sqrt(x + y);
            }
            return (in - (int) z);
        }
    }
}
