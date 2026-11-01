import java.util.*;

class OutOfStockException extends Exception {

    private int shortfall;

    public OutOfStockException(int shortfall) {

        super("Out of stock! Shortfall: " + shortfall);

        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {

    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {

    private Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {

        stock.put("Laptop", 10);
        stock.put("Mobile", 15);
        stock.put("Keyboard", 20);
        stock.put("Mouse", 25);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero!");
        }

        if (!stock.containsKey(item)) {
            System.out.println("Item not found: " + item);
            return;
        }

        int available = stock.get(item);

        if (qty > available) {

            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);

        System.out.println("Issued " + qty + " " + item);
        System.out.println("Remaining Stock: " + stock.get(item));
    }

    public void displayStock() {

        System.out.println("\nAvailable Warehouse Stock:");

        for (Map.Entry<String, Integer> entry : stock.entrySet()) {

            System.out.println(
                    entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class StockIssue {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        String[] items = {
            "Laptop",
            "Mobile",
            "Keyboard",
            "Mouse",
            "Laptop",
            "Tablet"
        };

        int[] quantities = {
            5, 20, 0, 10, 15, 3
        };

        System.out.println("Warehouse Stock Issue System\n");

        for (int i = 0; i < items.length; i++) {

            System.out.println("\nRequest: " + items[i]);
            System.out.println("Quantity: " + quantities[i]);

            try {

                warehouse.issue(items[i], quantities[i]);

            } catch (OutOfStockException e) {

                System.out.println(e.getMessage());

            } catch (InvalidQuantityException e) {

                System.out.println(e.getMessage());
            }
        }

        warehouse.displayStock();
    }
}