public class BusRoute {
    private String routeCode;
    private String routeName;
    private int priority;
    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0); 
    }  
    public int compareTo(BusRoute other) {
        
        if (this.priority != other.priority) {
            return other.priority - this.priority; 
        }
        String thisCodeLower = this.routeCode.toLowerCase();
        String otherCodeLower = other.routeCode.toLowerCase();
        
        int codeCompare = thisCodeLower.compareTo(otherCodeLower);
        if (codeCompare != 0) {
            return codeCompare;
        }
        String thisNameLower = this.routeName.toLowerCase();
        String otherNameLower = other.routeName.toLowerCase();
        
        return thisNameLower.compareTo(otherNameLower);
    }

    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        if (routes == null) return null;
        
        int n = routes.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (routes[j].compareTo(routes[j + 1]) > 0) {
                    BusRoute temp = routes[j];
                    routes[j] = routes[j + 1];
                    routes[j + 1] = temp;
                }
                
            }
        }
        
        return routes;
    }
    public String getRouteCode() {
        return this.routeCode;
    }
    @Override
    public String toString() {
        return "\"" + this.routeCode + "\"";
    }
}
