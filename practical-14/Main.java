package minishop.app;

import java.util.Scanner;
import minishop.model.Product;
import minishop.service.ProductService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductService service = new ProductService();

        int choice;

        do {
            System.out.println("\n===== PRODUCT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Count Products");
            System.out.println("4. Save Data");
            System.out.println("5. Load Data");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {

                switch (choice) {

                    case 1:
                        System.out.print("Enter Product ID: ");
                        int id = sc.nextInt();

                        sc.nextLine();

                        System.out.print("Enter Product Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Product Price: ");
                        double price = sc.nextDouble();

                        service.addProduct(
                                new Product(id, name, price));

                        System.out.println("Product added successfully.");
                        break;

                    case 2:
                        service.performAction(2);
                        break;

                    case 3:
                        service.performAction(3);
                        break;

                    case 4:
                        service.saveData("products.dat");
                        break;

                    case 5:
                        service.loadData("products.dat");
                        break;

                    case 0:
                        service.performAction(0);
                        break;

                    default:
                        service.performAction(choice);
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);

        sc.close();
    }
}