import java.io.*;

class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String name;
    double marks;
    transient String password;

    Student(int id, String name, double marks, String password) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.password = password;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Marks: " + marks);
        System.out.println("Password: " + password);
        System.out.println("--------------------");
    }
}

public class SaveLoadDemo {

    public static void main(String[] args) {

        Student[] students = {
            new Student(1, "Tilak", 85.5, "pass123"),
            new Student(2, "Rahul", 90.0, "pass456"),
            new Student(3, "Amit", 78.5, "pass789")
        };

        String fileName = "students.dat";

        // Save objects into file
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            out.writeObject(students);
            System.out.println("Students saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }

        // Load objects from file
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            Student[] loadedStudents = (Student[]) in.readObject();

            System.out.println("\nLoaded Student Details:");

            for (Student student : loadedStudents) {
                student.display();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}