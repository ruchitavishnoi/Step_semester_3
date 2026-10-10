interface Exportable {
    String exportData();
}

class ReportGenerator implements Exportable {
    private String reportName;

    public ReportGenerator(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    @Override
    public String exportData() {
        Main.incrementExports();
        return "Exported report: " + reportName;
    }
}

class UserProfile implements Exportable {
    private String username;

    public UserProfile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String exportData() {
        Main.incrementExports();
        return "Exported profile: " + username;
    }
}

public class DataExport {
    private static int totalExports = 0;

    public static void incrementExports() {
        totalExports++;
    }

    public static int getTotalExports() {
        return totalExports;
    }

    public static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            System.out.println(item.exportData());
        }
    }

    public static void main(String[] args) {
        ReportGenerator r = new ReportGenerator("Sales Q1");
        System.out.println(r.exportData());

        UserProfile u = new UserProfile("jane_doe");
        System.out.println(u.exportData());

        Exportable ref = r;
        exportAll(new Exportable[]{ ref, u });
        System.out.println(getTotalExports());
    }
}
