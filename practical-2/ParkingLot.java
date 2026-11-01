public class ParkingLot {

    private int twoWheelers;
    private int fourWheelers;

    private final int twoCap;
    private final int fourCap;

    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;

        twoWheelers = 0;
        fourWheelers = 0;
    }

    public boolean park(String type) {

        if (type.equalsIgnoreCase("two")) {

            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Two Wheeler Parked");
                return true;
            }

        } else if (type.equalsIgnoreCase("four")) {

            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Four Wheeler Parked");
                return true;
            }
        }

        System.out.println("Full");
        return false;
    }

    public void leave(String type) {

        if (type.equalsIgnoreCase("two")) {

            if (twoWheelers > 0) {
                twoWheelers--;
                System.out.println("Two Wheeler Left");
            } else {
                System.out.println("No Two Wheeler Available");
            }

        } else if (type.equalsIgnoreCase("four")) {

            if (fourWheelers > 0) {
                fourWheelers--;
                System.out.println("Four Wheeler Left");
            } else {
                System.out.println("No Four Wheeler Available");
            }

        } else {
            System.out.println("Invalid Vehicle Type");
        }
    }

    public void display() {

        System.out.println("\nFinal Occupancy:");

        System.out.println("Two Wheelers: " + twoWheelers);
        System.out.println("Four Wheelers: " + fourWheelers);

        System.out.println("Total Revenue: " + revenue);
    }

    public static void main(String[] args) {

        ParkingLot parking = new ParkingLot(2, 2);

        System.out.println("Parking Lot System\n");

        parking.park("two");
        parking.park("two");
        parking.park("two");

        parking.park("four");
        parking.park("four");
        parking.park("four");

        parking.leave("two");
        parking.leave("four");

        parking.park("two");
        parking.park("four");

        parking.display();
    }
}