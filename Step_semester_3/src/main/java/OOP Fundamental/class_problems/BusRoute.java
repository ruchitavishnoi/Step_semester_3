import java.util.Arrays;

public class BusRoute implements Comparable<BusRoute> {
    private String routeCode;
    private String routeName;
    private int priority;

    private static final int DEFAULT_PRIORITY = 0;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, DEFAULT_PRIORITY);
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(BusRoute other) {
        if (other == null) return -1;

        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }

        int codeCaseInsensitiveCmp = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCaseInsensitiveCmp != 0) {
            return codeCaseInsensitiveCmp;
        }

        int codeCaseSensitiveCmp = this.routeCode.compareTo(other.routeCode);
        if (codeCaseSensitiveCmp != 0) {
            return codeCaseSensitiveCmp;
        }

        int nameCaseInsensitiveCmp = this.routeName.compareToIgnoreCase(other.routeName);
        if (nameCaseInsensitiveCmp != 0) {
            return nameCaseInsensitiveCmp;
        }

        return this.routeName.compareTo(other.routeName);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null || routes.length <= 1) {
            return routes;
        }

        BusRoute[] sorted = routes.clone();

        for (int i = 1; i < sorted.length; i++) {
            BusRoute key = sorted[i];
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
        return "\"" + routeCode + "\"";
    }

    public static void main(String[] args) {
        BusRoute[] input = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(input);
        System.out.println(Arrays.toString(ranked));
    }
}
