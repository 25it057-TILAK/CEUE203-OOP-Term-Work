public class SeatBookingRace {

    static class BookingSystem {
        int seatsLeft = 5;
        int successfulBookings = 0;

        void bookWithoutSynchronization() {
            if (seatsLeft > 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                seatsLeft--;
                successfulBookings++;
                System.out.println(Thread.currentThread().getName()
                        + " booked a seat.");
            }
        }

        synchronized void bookWithSynchronization() {
            if (seatsLeft > 0) {
                seatsLeft--;
                successfulBookings++;
                System.out.println(Thread.currentThread().getName()
                        + " booked a seat.");
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " booking failed. No seats available.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Without synchronization
        BookingSystem system1 = new BookingSystem();

        Thread[] threads = new Thread[10];

        System.out.println("Without Synchronization:");

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(
                    system1::bookWithoutSynchronization, "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Successful Bookings: "
                + system1.successfulBookings);
        System.out.println("Seats Left: " + system1.seatsLeft);

        // With synchronization
        BookingSystem system2 = new BookingSystem();

        System.out.println("\nWith Synchronization:");

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(
                    system2::bookWithSynchronization, "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Successful Bookings: "
                + system2.successfulBookings);
        System.out.println("Seats Left: " + system2.seatsLeft);
    }
}