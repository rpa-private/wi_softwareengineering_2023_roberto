package ch.fhnw.richards.Week_06.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program uses executors to do the same counting task as "RaceCondition".
 * It is properly synchronized, so no race-condition occurs. Note the way the
 * threads interleave, depending on the number of processors available.
 *
 * @author Brad Richards
 *
 */
class Counters implements Runnable {

    public static void main(String[] args) {
        int numProcessors = 4;
        // numProcessors = Runtime.getRuntime().availableProcessors(); // For the real number available
        System.out.printf("Working with %d processors%n", numProcessors);
        ExecutorService executor = Executors.newFixedThreadPool(numProcessors);

        Runnable[] tasks = new Runnable[10];
        CentralCounter centralCounter = new CentralCounter();
        for (int i = 0; i < 10; ++i) {
            tasks[i] = new Counters("Counter-" + i, centralCounter);
        }
        for (Runnable t : tasks) {
            executor.execute(t);
        }
        executor.shutdown();
    }

    // Object attributes
    private String threadName;
    private CentralCounter centralCounter;

    public Counters(String name, CentralCounter comm) {
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

        synchronized int increment() {
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
