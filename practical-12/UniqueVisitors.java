import java.util.*;

public class UniqueVisitors {

    public static void main(String[] args) {

        List<String> loginNames = Arrays.asList(
                "Tilak",
                "Rahul",
                "Amit",
                "Tilak",
                "Priya",
                "Rahul",
                "Neha",
                "Amit",
                "Karan"
        );

        HashSet<String> uniqueUsers = new HashSet<>();

        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>();

        for (String name : loginNames) {
            uniqueUsers.add(name);
            orderedUsers.add(name);
        }

        System.out.println("Total Login Entries: " + loginNames.size());

        System.out.println("Unique Visitors: " + uniqueUsers.size());

        System.out.println("First Three Distinct Visitors:");

        int count = 0;

        for (String name : orderedUsers) {
            if (count == 3) {
                break;
            }

            System.out.println(name);
            count++;
        }
    }
}