public class CounterRace {

    static int counter = 0;
    static final int THREADS = 10;
    static final int INCREMENTS = 100000;

    static synchronized void incrementSynchronized() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {

        // Without synchronization
        counter = 0;

        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    counter++;
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Without Synchronization: " + counter);
        System.out.println("Expected Result: " + (THREADS * INCREMENTS));

        // With synchronization
        counter = 0;

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    incrementSynchronized();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("With Synchronization: " + counter);
        System.out.println("Expected Result: " + (THREADS * INCREMENTS));
    }
}