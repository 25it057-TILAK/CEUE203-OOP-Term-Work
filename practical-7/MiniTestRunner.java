import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class TestCases {

    @Run
    public void testAdd() {
        int result = 10 + 20;
        System.out.println("Test Add: " + (result == 30 ? "PASS" : "FAIL"));
    }

    @Run
    public void testMultiply() {
        int result = 5 * 4;
        System.out.println("Test Multiply: " + (result == 20 ? "PASS" : "FAIL"));
    }

    @Run
    public void testSubtract() {
        int result = 50 - 20;
        System.out.println("Test Subtract: " + (result == 30 ? "PASS" : "FAIL"));
    }

    public void normalMethod() {
        System.out.println("This method will not run");
    }
}

public class MiniTestRunner {

    public static void main(String[] args) {

        TestCases test = new TestCases();

        int count = 0;

        Method[] methods = test.getClass().getDeclaredMethods();

        System.out.println("Running Tests:\n");

        for (Method method : methods) {

            if (method.isAnnotationPresent(Run.class)
                    && method.getParameterCount() == 0) {

                try {

                    method.invoke(test);

                    count++;

                } catch (Exception e) {

                    System.out.println("Test Failed: " + method.getName());
                }
            }
        }

        System.out.println("\nTotal Tests Run: " + count);
    }
}