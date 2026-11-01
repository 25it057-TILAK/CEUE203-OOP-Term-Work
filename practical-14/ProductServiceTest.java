package minishop.service;

import minishop.model.Product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @TempDir
    Path tempDir;

    // Test 1: Adding product increases count
    @Test
    public void testAddProduct() {

        ProductService service = new ProductService();

        assertEquals(0, service.getCount());

        service.addProduct(
                new Product(1, "Laptop", 50000));

        assertEquals(1, service.getCount());
    }

    // Test 2: Invalid action throws exception
    @Test
    public void testInvalidAction() {

        ProductService service = new ProductService();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.performAction(99)
        );
    }

    // Test 3: Save and reload data
    @Test
    public void testSaveAndLoadData() {

        ProductService service1 = new ProductService();

        service1.addProduct(
                new Product(1, "Laptop", 50000));

        service1.addProduct(
                new Product(2, "Mobile", 20000));

        String fileName =
                tempDir.resolve("test-products.dat").toString();

        service1.saveData(fileName);

        ProductService service2 = new ProductService();

        service2.loadData(fileName);

        assertEquals(2, service2.getCount());
    }
}