import java.util.Arrays;

public class Canteen implements Comparable<Canteen> {
    private String canteenCode;
    private String canteenName;
    private int trustScore;

    private static final int DEFAULT_TRUST_SCORE = 3;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getTrustScore() {
        return trustScore;
    }

    @Override
    public int compareTo(Canteen other) {
        if (other == null) return -1;

        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        int codeCaseInsensitiveCmp = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCaseInsensitiveCmp != 0) {
            return codeCaseInsensitiveCmp;
        }

        int codeCaseSensitiveCmp = this.canteenCode.compareTo(other.canteenCode);
        if (codeCaseSensitiveCmp != 0) {
            return codeCaseSensitiveCmp;
        }

        if (this.canteenName.length() != other.canteenName.length()) {
            return Integer.compare(this.canteenName.length(), other.canteenName.length());
        }

        return this.canteenName.compareToIgnoreCase(other.canteenName);
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null || canteens.length <= 1) {
            return canteens;
        }

        Canteen[] sorted = canteens.clone();

        for (int i = 1; i < sorted.length; i++) {
            Canteen key = sorted[i];
            int j = i - 1;

            while (j >= 0 && key.compareTo(sorted[j]) < 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }

        return sorted;
    }

    @Override
    public String toString() {
        return "\"" + canteenCode + "\"";
    }

    public static void main(String[] args) {
        Canteen[] input = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = rankCanteens(input);
        System.out.println(Arrays.toString(ranked));
    }
}
