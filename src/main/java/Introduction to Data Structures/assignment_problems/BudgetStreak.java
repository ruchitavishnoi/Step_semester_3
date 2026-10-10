public class BudgetStreak {
    public static class Result {
        public int length;
        public int startIndex;

        public Result(int length, int startIndex) {
            this.length = length;
            this.startIndex = startIndex;
        }

        @Override
        public String toString() {
            return "(" + length + ", " + startIndex + ")";
        }
    }

    public static Result longestStreak(int[] costs, long budget) {
        int maxLength = 0;
        int bestStartIndex = -1;
        long currentSum = 0;
        int left = 0;

        for (int right = 0; right < costs.length; right++) {
            currentSum += costs[right];

            while (currentSum > budget && left <= right) {
                currentSum -= costs[left];
                left++;
            }

            if (currentSum <= budget) {
                int currentLength = right - left + 1;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    bestStartIndex = left;
                }
            }
        }

        return new Result(maxLength, bestStartIndex);
    }

    public static void main(String[] args) {
        int[] costs1 = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        long budget1 = 8;
        System.out.println(longestStreak(costs1, budget1));

        int[] costs2 = {9, 10};
        long budget2 = 8;
        System.out.println(longestStreak(costs2, budget2));
    }
}
