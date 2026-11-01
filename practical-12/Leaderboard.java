import java.util.*;

public class Leaderboard {

    public static void main(String[] args) {

        Map<String, Integer> scores = new HashMap<>();

        scores.put("Rahul", 950);
        scores.put("Tilak", 1200);
        scores.put("Amit", 1100);
        scores.put("Priya", 1200);
        scores.put("Neha", 850);
        scores.put("Karan", 1000);

        int topN = 3;

        List<Map.Entry<String, Integer>> players =
                new ArrayList<>(scores.entrySet());

        players.sort((p1, p2) -> {

            int scoreCompare =
                    p2.getValue().compareTo(p1.getValue());

            if (scoreCompare == 0) {
                return p1.getKey().compareTo(p2.getKey());
            }

            return scoreCompare;
        });

        System.out.println("TOP " + topN + " PLAYERS");
        System.out.println("----------------------");

        for (int i = 0; i < Math.min(topN, players.size()); i++) {

            Map.Entry<String, Integer> player = players.get(i);

            System.out.println(
                    (i + 1) + ". " + player.getKey()
                    + " - " + player.getValue() + " points"
            );
        }
    }
}