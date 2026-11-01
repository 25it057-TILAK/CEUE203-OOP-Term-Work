import java.util.*;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Double> prices = new ArrayList<>();

        prices.add(1000.0);
        prices.add(2000.0);
        prices.add(3000.0);
        prices.add(500.0);

        DiscountRule tenPercent = price -> price * 0.90;

        DiscountRule twentyPercent = price -> price * 0.80;

        DiscountRule fixedDiscount = price -> price - 100;

        System.out.println("Discount Options:");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Rs. 100 Fixed Discount");

        System.out.print("Choose discount: ");
        int choice = sc.nextInt();

        DiscountRule selectedRule;

        switch (choice) {

            case 1:
                selectedRule = tenPercent;
                break;

            case 2:
                selectedRule = twentyPercent;
                break;

            case 3:
                selectedRule = fixedDiscount;
                break;

            default:
                System.out.println("Invalid Choice");
                sc.close();
                return;
        }

        System.out.println("\nOriginal Prices:");

        for (double price : prices) {
            System.out.println(price);
        }

        System.out.println("\nPrices After Discount:");

        double total = 0;

        for (double price : prices) {

            double discountedPrice =
                    Math.max(0, selectedRule.apply(price));

            System.out.println(discountedPrice);

            total += discountedPrice;
        }

        System.out.println("\nTotal Amount: " + total);

        sc.close();
    }
}