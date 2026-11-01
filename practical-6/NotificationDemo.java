@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class EmailSender implements Notifier, Urgent {

    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSSender implements Notifier {

    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

class UrgentEmailSender implements Notifier, Urgent {

    @Override
    public void send(String message) {
        System.out.println("Urgent Email: " + message);
    }
}

public class NotificationDemo {

    public static void main(String[] args) {

        Notifier email = message ->
                System.out.println("Email: " + message);

        Notifier sms = message ->
                System.out.println("SMS: " + message);

        Notifier urgentEmail = new UrgentEmailSender();

        Notifier[] senders = {
            new EmailSender(),
            sms,
            urgentEmail
        };

        String message = "Your order has been shipped!";

        System.out.println("Normal Broadcast:");

        for (Notifier sender : senders) {
            sender.send(message);
        }

        System.out.println("\nUrgent Notifications:");

        for (Notifier sender : senders) {

            if (sender instanceof Urgent) {
                sender.send(message);
                sender.send(message);
            }
        }
    }
}