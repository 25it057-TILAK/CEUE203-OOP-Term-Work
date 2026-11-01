abstract class Shape {
    abstract double area();
}

class Circle extends Shape {

    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {

    private double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}

class Triangle extends Shape {

    private double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }
}

public class ShapeDemo {

    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(10, 5),
            new Triangle(8, 6),
            new Circle(3)
        };

        double total = 0;
        double largest = 0;

        for (Shape s : shapes) {

            double a = s.area();

            System.out.printf("Area: %.2f%n", a);

            total += a;

            if (a > largest) {
                largest = a;
            }

            System.out.printf("Running Total: %.2f%n", total);
        }

        System.out.printf("%nTotal Area: %.2f%n", total);
        System.out.printf("Largest Area: %.2f%n", largest);
    }
}