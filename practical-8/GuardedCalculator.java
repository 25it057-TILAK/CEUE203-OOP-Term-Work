import java.util.Scanner;

class DivideByZeroException extends Exception {

    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {

    public static double calculate(double a, double b, char operator)
            throws DivideByZeroException {

        switch (operator) {

            case '+':
                return a + b;

            case '-':
                return a - b;

            case '*':
                return a * b;

            case '/':
                if (b == 0) {
                    throw new DivideByZeroException(
                            "Cannot divide by zero!");
                }
                return a / b;

            default:
                throw new IllegalArgumentException("Invalid operator!");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean success = false;

        while (!success) {

            try {

                System.out.print("Enter first number: ");
                double a = Double.parseDouble(sc.nextLine());

                System.out.print("Enter operator (+, -, *, /): ");
                char operator = sc.nextLine().charAt(0);

                System.out.print("Enter second number: ");
                double b = Double.parseDouble(sc.nextLine());

                double result = calculate(a, b, operator);

                System.out.println("Result: " + result);

                success = true;

            } catch (NumberFormatException e) {

                System.out.println("Invalid number input!");

            } catch (DivideByZeroException e) {

                System.out.println(e.getMessage());

            } catch (IllegalArgumentException e) {

                System.out.println(e.getMessage());

            } catch (StringIndexOutOfBoundsException e) {

                System.out.println("Operator cannot be empty!");

            } finally {

                System.out.println("Calculation attempt completed.\n");
            }
        }

        sc.close();
    }
}