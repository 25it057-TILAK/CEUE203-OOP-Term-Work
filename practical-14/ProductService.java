package minishop.service;

import java.io.*;
import java.util.ArrayList;
import minishop.model.Product;

public class ProductService {

    private ArrayList<Product> products = new ArrayList<>();

    // Add product
    public void addProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product cannot be null.");
        }

        products.add(product);
    }

    // Get total product count
    public int getCount() {
        return products.size();
    }

    // Display all products
    public void displayProducts() {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Perform menu action
    public void performAction(int action) {

        switch (action) {
            case 1:
                System.out.println("Add Product selected.");
                break;

            case 2:
                displayProducts();
                break;

            case 3:
                System.out.println("Total Products: " + getCount());
                break;

            case 0:
                System.out.println("Exiting application.");
                break;

            default:
                throw new IllegalArgumentException(
                        "Invalid menu action: " + action);
        }
    }

    // Save products into file
    public void saveData(String fileName) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(fileName))) {

            out.writeObject(products);

            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error saving data.", e);
        }
    }

    // Load products from file
    @SuppressWarnings("unchecked")
    public void loadData(String fileName) {

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(fileName))) {

            products = (ArrayList<Product>) in.readObject();

            System.out.println("Data loaded successfully.");

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(
                    "Error loading data.", e);
        }
    }
}