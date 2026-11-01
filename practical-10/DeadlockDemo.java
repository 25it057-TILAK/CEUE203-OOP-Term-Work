public class DeadlockDemo {

    static final Object lockA = new Object();
    static final Object lockB = new Object();

    static void createDeadlock() {

        Thread thread1 = new Thread(() -> {

            synchronized (lockA) {
                System.out.println("Thread 1 locked A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (lockB) {
                    System.out.println("Thread 1 locked B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {

            synchronized (lockB) {
                System.out.println("Thread 2 locked B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                synchronized (lockA) {
                    System.out.println("Thread 2 locked A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    static void preventDeadlock() {

        Thread thread1 = new Thread(() -> {

            synchronized (lockA) {
                System.out.println("Thread 1 locked A");

                synchronized (lockB) {
                    System.out.println("Thread 1 locked B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {

            synchronized (lockA) {
                System.out.println("Thread 2 locked A");

                synchronized (lockB) {
                    System.out.println("Thread 2 locked B");
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {

        System.out.println("1. Deadlock Example:");
        createDeadlock();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n2. Deadlock Prevention:");
        System.out.println("Use the same lock order: A then B.");
        preventDeadlock();
    }
}