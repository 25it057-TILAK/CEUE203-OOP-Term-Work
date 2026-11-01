import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Student {

    @Column(name = "student_id")
    private int id;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_email")
    private String email;

    @Column(name = "student_age")
    private int age;

    public void display() {

        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
        System.out.println("Student Email: " + email);
        System.out.println("Student Age: " + age);
    }
}

public class ColumnDemo {

    public static void populate(
            Object obj, String[] header, String[] data) {

        Class<?> clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);

            String columnName = column.name();

            int index = -1;

            for (int i = 0; i < header.length; i++) {

                if (header[i].equals(columnName)) {
                    index = i;
                    break;
                }
            }

            if (index == -1 || index >= data.length) {
                System.out.println("Missing column: " + columnName);
                continue;
            }

            try {

                field.setAccessible(true);

                String value = data[index];

                if (field.getType() == int.class) {
                    field.setInt(obj, Integer.parseInt(value));
                } else if (field.getType() == String.class) {
                    field.set(obj, value);
                }

            } catch (Exception e) {

                System.out.println("Error in column: " + columnName);
            }
        }
    }

    public static void main(String[] args) {

        String[] header = {
            "student_id",
            "student_name",
            "student_email",
            "student_age"
        };

        String[] data = {
            "101",
            "Tilak",
            "tilak@gmail.com",
            "20"
        };

        Student student = new Student();

        populate(student, header, data);

        System.out.println("\nStudent Details:");

        student.display();
    }
}