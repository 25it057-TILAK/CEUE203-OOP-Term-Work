import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int totalToll = 0;

        int bike = 0;
        int car = 0;
        int truck = 0;

        while (true) {

            System.out.print("Enter vehicle number (done to stop): ");
            String number = sc.nextLine();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type: ");
            String type = sc.nextLine().toLowerCase();

            Vehicle v = new Vehicle(number, type);

            int toll = switch (v.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> 0;
            };

            if (toll == 0) {
                System.out.println("Invalid vehicle type");
                continue;
            }

            totalToll += toll;

            switch (v.type()) {
                case "bike" -> bike++;
                case "car" -> car++;
                case "truck" -> truck++;
            }
        }

        System.out.println("Total toll: " + totalToll);

        if (bike >= car && bike >= truck) {
            System.out.println("Most frequent: bike");
        } else if (car >= bike && car >= truck) {
            System.out.println("Most frequent: car");
        } else {
            System.out.println("Most frequent: truck");
        }

        sc.close();
    }
}