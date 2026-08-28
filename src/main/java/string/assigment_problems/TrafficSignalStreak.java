import java.util.Scanner;

public class TrafficSignalStreak {

    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            return;
        }

        char maxChar = signalLog.charAt(0);
        int maxLength = 1;

        char currentChar = signalLog.charAt(0);
        int currentLength = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentChar) {
                currentLength++;
            } else {
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    maxChar = currentChar;
                }
                currentChar = signalLog.charAt(i);
                currentLength = 1;
            }
        }

        if (currentLength > maxLength) {
            maxLength = currentLength;
            maxChar = currentChar;
        }

        System.out.println("Longest Streak: '" + maxChar + "' repeated " + maxLength + " times");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter signal log: ");
        String signalLog = sc.nextLine();

        findLongestStreak(signalLog);

        sc.close();
    }
}
