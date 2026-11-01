import java.util.Random;
import java.util.Scanner;

public class RPSLS {

    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    public static int winner(Move a, Move b) {

        if (a == b) {
            return 0;
        }

        return switch (a) {

            case ROCK ->
                (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;

            case PAPER ->
                (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;

            case SCISSORS ->
                (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;

            case LIZARD ->
                (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;

            case SPOCK ->
                (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        Move[] moves = Move.values();

        for (int round = 1; round <= 5; round++) {

            System.out.println("\nRound " + round);

            System.out.print("Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");

            Move player = Move.valueOf(sc.next().toUpperCase());

            Move computer = moves[random.nextInt(moves.length)];

            System.out.println("Your move: " + player);
            System.out.println("Computer move: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("This round is a tie!");
            }

            System.out.println("Score: " + playerScore + " - " + computerScore);
        }

        System.out.println("\nFinal Result:");

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "–" + computerScore);
        } else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "–" + playerScore);
        } else {
            System.out.println("Match Draw " + playerScore + "–" + computerScore);
        }

        sc.close();
    }
}