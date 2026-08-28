import java.util.Scanner;
public class Palindrome Checker {
    public static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;
        
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) {
            return true;
        }
        
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    public static boolean isPalindromeArrayReversal(String text) {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];
        
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        
        return text.equals(new String(reversed));
    }

    private static String formatResult(boolean isPalindrome) {
        return isPalindrome ? "Palindrome" : "Not Palindrome";
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter text: ");
        String input = sc.nextLine();
        
        boolean iterativeResult = isPalindromeIterative(input);
        boolean recursiveResult = isPalindromeRecursive(input);
        boolean arrayResult = isPalindromeArrayReversal(input);
        
        System.out.printf("Iterative: %s | Recursive: %s | Array Reversal: %s\n",
                formatResult(iterativeResult),
                formatResult(recursiveResult),
                formatResult(arrayResult));
                
        sc.close();
    }
}
