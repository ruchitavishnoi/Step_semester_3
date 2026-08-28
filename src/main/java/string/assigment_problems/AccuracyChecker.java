import java.util.Scanner;

public class Main AccuracyChecker{

    public static void checkTypingAccuracy(String original, String typed) {
        int total = original.length();
        int matched = 0;
        int firstMismatchPos = -1;
        char expectedChar = ' ';
        char actualChar = ' ';

        for (int i = 0; i < total; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i + 1;
                expectedChar = original.charAt(i);
                actualChar = typed.charAt(i);
            }
        }

        double accuracy = (matched * 100.0) / total;

        System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | ", matched, total, accuracy);

        if (firstMismatchPos != -1) {
            System.out.printf("First Mismatch at position %d ('%c' vs '%c')\n", 
                    firstMismatchPos, expectedChar, actualChar);
        } else {
            System.out.println("No Mismatches");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter original passage: ");
        String original = sc.nextLine();

        System.out.print("Enter typed text: ");
        String typed = sc.nextLine();

        checkTypingAccuracy(original, typed);

        sc.close();
    }
}
