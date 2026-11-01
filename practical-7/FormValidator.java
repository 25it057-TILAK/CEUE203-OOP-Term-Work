import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {

    @NotBlank
    private String name;

    @NotBlank
    @MaxLength(30)
    private String email;

    @NotBlank
    @MaxLength(10)
    private String username;

    @NotBlank
    private String password;

    public SignupForm(String name, String email,
                      String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}

public class FormValidator {

    public static List<String> validate(Object obj) {

        List<String> errors = new ArrayList<>();

        Class<?> clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            try {

                Object value = field.get(obj);

                if (field.isAnnotationPresent(NotBlank.class)) {

                    if (value == null ||
                        value.toString().trim().isEmpty()) {

                        errors.add(field.getName() + " cannot be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {

                    MaxLength max = field.getAnnotation(MaxLength.class);

                    if (value != null &&
                        value.toString().length() > max.value()) {

                        errors.add(field.getName() +
                                " exceeds maximum length " + max.value());
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Cannot access field: " + field.getName());
            }
        }

        return errors;
    }

    public static void main(String[] args) {

        SignupForm form = new SignupForm(
                "",
                "verylongemailaddress123456789@example.com",
                "student12345",
                "password123"
        );

        List<String> errors = validate(form);

        if (errors.isEmpty()) {
            System.out.println("Form is valid");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}