import java.util.HashMap;
import java.util.Map;

public class PeriodCounter {
    public static int countPeriods(int[] transactions, long k) {
        int count = 0;
        long currentSum = 0;
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1);

        for (int val : transactions) {
            currentSum += val;
            if (prefixSumMap.containsKey(currentSum - k)) {
                count += prefixSumMap.get(currentSum - k);
            }
            prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] transactions1 = {3, 4, -7, 1, 3, 3, 1, -4};
        long k1 = 7;
        System.out.println(countPeriods(transactions1, k1));

        int[] transactions2 = {1, 2, 3};
        long k2 = 10;
        System.out.println(countPeriods(transactions2, k2));
    }
}
