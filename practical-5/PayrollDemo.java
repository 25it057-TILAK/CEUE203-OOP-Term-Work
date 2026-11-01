abstract class Employee {

    protected String name;
    protected int id;

    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    abstract double monthlySalary();
}

class FullTime extends Employee {

    private double salary;

    FullTime(String name, int id, double salary) {
        super(name, id);
        this.salary = salary;
    }

    @Override
    double monthlySalary() {
        return salary;
    }
}

class PartTime extends Employee {

    private int hours;
    private double rate;

    PartTime(String name, int id, int hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    double monthlySalary() {
        return hours * rate;
    }
}

class Intern extends Employee {

    private double stipend;

    Intern(String name, int id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    @Override
    double monthlySalary() {
        return stipend;
    }
}

public class PayrollDemo {

    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Rahul", 101, 30000),
            new PartTime("Amit", 102, 80, 200),
            new Intern("Priya", 103, 10000),
            new FullTime("Karan", 104, 40000),
            new PartTime("Neha", 105, 60, 150)
        };

        double total = 0;

        for (Employee e : employees) {

            double salary = e.monthlySalary();

            System.out.println("\nEmployee: " + e.name);
            System.out.println("ID: " + e.id);
            System.out.println("Monthly Salary: " + salary);

            if (e instanceof Intern) {
                System.out.println("Note: This employee is an Intern.");
            }

            total += salary;
        }

        System.out.println("\nTotal Payroll: " + total);
    }
}