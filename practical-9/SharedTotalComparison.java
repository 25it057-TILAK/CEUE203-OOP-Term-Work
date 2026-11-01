import java.util.concurrent.atomic.AtomicLong;

public class SharedTotalComparison {

    static final int SIZE = 10_000_000;
    static final int THREADS = 4;

    static long sharedTotal = 0;
    static AtomicLong atomicTotal = new AtomicLong(0);

    static class SumTask implements Runnable {

        private final int[] numbers;
        private final int start;
        private final int end;
        private final int method;

        SumTask(int[] numbers, int start, int end, int method) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
            this.method = method;
        }

        @Override
        public void run() {
            long localSum = 0;

            for (int i = start; i < end; i++) {
                localSum += numbers[i];
            }

            if (method == 1) {
                // Without synchronization
                sharedTotal += localSum;
            } else if (method == 2) {
                // Using synchronized block
                synchronized (SharedTotalComparison.class) {
                    sharedTotal += localSum;
                }
            } else if (method == 3) {
                // Using AtomicLong
                atomicTotal.addAndGet(localSum);
            }
        }
    }

    static void runThreads(int[] numbers, int method)
            throws InterruptedException {

        Thread[] threads = new Thread[THREADS];

        int chunkSize = SIZE / THREADS;

        for (int i = 0; i < THREADS; i++) {
            int start = i * chunkSize;
            int end = (i == THREADS - 1) ? SIZE : start + chunkSize;

            threads[i] = new Thread(
                    new SumTask(numbers, start, end, method));

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int[] numbers = new int[SIZE];
        long expectedTotal = 0;

        for (int i = 0; i < SIZE; i++) {
            numbers[i] = i + 1;
            expectedTotal += numbers[i];
        }

        System.out.println("Expected Total: " + expectedTotal);

        // Method 1: Without synchronization
        sharedTotal = 0;

        long startTime = System.nanoTime();
        runThreads(numbers, 1);
        long endTime = System.nanoTime();

        System.out.println("\nWithout Synchronization:");
        System.out.println("Total: " + sharedTotal);
        System.out.println("Time: " + (endTime - startTime) / 1_000_000.0
                + " ms");

        // Method 2: Using synchronized block
        sharedTotal = 0;

        startTime = System.nanoTime();
        runThreads(numbers, 2);
        endTime = System.nanoTime();

        System.out.println("\nUsing Synchronization:");
        System.out.println("Total: " + sharedTotal);
        System.out.println("Time: " + (endTime - startTime) / 1_000_000.0
                + " ms");

        // Method 3: Using AtomicLong
        atomicTotal.set(0);

        startTime = System.nanoTime();
        runThreads(numbers, 3);
        endTime = System.nanoTime();

        System.out.println("\nUsing AtomicLong:");
        System.out.println("Total: " + atomicTotal.get());
        System.out.println("Time: " + (endTime - startTime) / 1_000_000.0
                + " ms");
    }
}