import java.util.*;

public class WordFrequency {

    public static void main(String[] args) {

        String paragraph = "Java is a programming language. Java is simple and Java is powerful. Programming is fun.";

        paragraph = paragraph.toLowerCase().replaceAll("[^a-z ]", "");

        String[] words = paragraph.split("\\s+");

        HashMap<String, Integer> frequency = new HashMap<>();

        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(frequency.entrySet());

        list.sort((a, b) -> {
            int result = b.getValue().compareTo(a.getValue());

            if (result == 0) {
                return a.getKey().compareTo(b.getKey());
            }

            return result;
        });

        System.out.println("Word Frequency:");

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}