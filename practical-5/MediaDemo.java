abstract class Media {

    protected String title;
    protected int lateDays;

    Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = lateDays;
    }

    abstract double calculateLateFee();
}

class Book extends Media {

    Book(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double calculateLateFee() {
        return lateDays * 2.0;
    }
}

class DVD extends Media {

    DVD(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double calculateLateFee() {
        return lateDays * 5.0;
    }
}

class Magazine extends Media {

    Magazine(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double calculateLateFee() {
        return lateDays * 1.5;
    }
}

public class MediaDemo {

    public static void main(String[] args) {

        Media[] items = {
            new Book("Java Programming", 5),
            new DVD("Avengers Movie", 3),
            new Magazine("Technology Today", 4),
            new Book("Data Structures", 2)
        };

        double totalFees = 0;

        for (Media m : items) {

            double fee = m.calculateLateFee();

            System.out.println("\nMedia: " + m.title);
            System.out.println("Late Days: " + m.lateDays);
            System.out.println("Late Fee: " + fee);

            totalFees += fee;
        }

        System.out.println("\nTotal Late Fees: " + totalFees);
    }
}