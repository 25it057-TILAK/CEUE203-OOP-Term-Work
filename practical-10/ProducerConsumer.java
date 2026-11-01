import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    static class SharedBuffer {

        private final Queue<Integer> buffer = new LinkedList<>();
        private final int capacity = 3;

        public synchronized void produce(int value)
                throws InterruptedException {

            while (buffer.size() == capacity) {
                wait();
            }

            buffer.add(value);
            System.out.println("Produced: " + value);

            notifyAll();
        }

        public synchronized int consume()
                throws InterruptedException {

            while (buffer.isEmpty()) {
                wait();
            }

            int value = buffer.remove();
            System.out.println("Consumed: " + value);

            notifyAll();

            return value;
        }
    }

    public static void main(String[] args) {

        SharedBuffer sharedBuffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    sharedBuffer.produce(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    sharedBuffer.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Producer and Consumer finished.");
    }
}