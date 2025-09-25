package ch.fhnw.richards.Week_06;

public class ThreadStopper extends Thread {
    private TestThread t;

    public static void main(String[] args) {
        new ThreadStopper();
    }

    private ThreadStopper() {
        t = new TestThread();
        t.start();
        this.start();
    }

    @Override
    public void run() {
        // Initial wait of 50ms - give the other thread time to start
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
        }

        // try to stop the other thread
        int attempts = 0;
        while (t.isAlive()) {
            attempts++;
            t.stop = true;
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Attempts " + attempts);
    }

    private static class TestThread extends Thread {
        private boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
            }
        }
    }
}
