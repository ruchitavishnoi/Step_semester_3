import java.util.Stack;

class Solution {
    public int[] priceStreak(int[] price) {
        int n = price.length;
        int[] streaks = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }
            
            if (stack.isEmpty()) {
                streaks[i] = i + 1;
            } else {
                streaks[i] = i - stack.peek();
            }
            
            stack.push(i);
        }
        
        return streaks;
    }
}
