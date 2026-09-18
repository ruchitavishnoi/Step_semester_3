import java.util.StringBuilder;

public class RaceAnnouncerManager {

    public static class RaceEntry {
        private String bibNumber;
        private double entryFee;
        private double paidAmount;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.paidAmount = 0.0;
        }

        public void pay(double amount) {
            this.paidAmount += amount;
        }

        public double getBalanceDue() {
            return this.entryFee - this.paidAmount;
        }

        public String getBibNumber() {
            return bibNumber;
        }

        public String announce() {
            return "Base Entry | Bib: " + bibNumber + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        public String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }

        @Override
        public String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {
            report.append(entry.announce());

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 90.0, "Open 10K");
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300.0, 4);

        RaceEntry[] fleet = { runnerEntry, relayEntry };
        System.out.println(announceAll(fleet));
    }
}
