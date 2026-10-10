public class ProductPriceFinder {
    public static class RangeResult {
        public int lowerIndex;
        public int upperIndex;
        public int count;

        public RangeResult(int lowerIndex, int upperIndex, int count) {
            this.lowerIndex = lowerIndex;
            this.upperIndex = upperIndex;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Lower bound index " + lowerIndex + "\n" +
                   "Upper bound index " + upperIndex + "\n" +
                   "Count " + count;
        }
    }

    public static RangeResult countInPriceRange(int[] prices, int minPrice, int maxPrice) {
        int lowerIndex = findLowerBound(prices, minPrice);
        int upperIndex = findUpperBound(prices, maxPrice);
        int count = Math.max(0, upperIndex - lowerIndex);
        return new RangeResult(lowerIndex, upperIndex, count);
    }

    private static int findLowerBound(int[] prices, int target) {
        int left = 0;
        int right = prices.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (prices[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static int findUpperBound(int[] prices, int target) {
        int left = 0;
        int right = prices.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (prices[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};
        RangeResult result = countInPriceRange(prices, 150, 300);
        System.out.println(result);
    }
}
