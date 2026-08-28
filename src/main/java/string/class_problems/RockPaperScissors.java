import java.util.Scanner;
public class RockPaperScissors {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] moves = {"Rock", "Paper", "Scissors"};
        int win = 0, loss = 0, draw = 0;
        System.out.print("Enter number of rounds (N): ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.print("Round " + i + " — Enter your move: ");
            String playerMove = sc.next();

            String computerMove = moves[(int)(Math.random() * 3)];
            System.out.println("Computer move: " + computerMove);

            String result = playRound(playerMove, computerMove);
            System.out.println("Output: " + result + "\n");

            if (result.equals("Player Wins")) {
                win++;
            } else if (result.equals("Computer Wins")) {
                loss++;
            } else {
                draw++;
            }
        }
        double winPercentage = (win * 100.0) / n;
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%\n", 
                          win, loss, draw, winPercentage);
        sc.close();
    }
}
