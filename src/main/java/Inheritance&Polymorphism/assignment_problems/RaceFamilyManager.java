public class RaceFamilyManager {

    public static class BaseRaceEntry {
        private String bibNumber;
        private double entryFee;
        private double paidAmount;

        public BaseRaceEntry(String bibNumber, double entryFee) {
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

        public double getEntryFee() {
            return entryFee;
        }

        public String announce() {
            return "Base Entry | Bib: " + bibNumber + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class SingleRunnerEntry extends BaseRaceEntry {
        private String category;

        public SingleRunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class EliteRunnerEntry extends SingleRunnerEntry {
        private double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        public double getSponsorBonus() {
            return sponsorBonus;
        }

        @Override
        public String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory() + " | Sponsor Bonus: " + String.format("%.1f", sponsorBonus) + " | Balance: " + String.format("%.1f", getBalanceDue());
        }
    }

    public static class RelayTeamEntry extends BaseRaceEntry {
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

    public static String classifyGeneration(BaseRaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof SingleRunnerEntry) {
            return "Single-inheritance child (2 generations deep)";
        }
        return "Base class (1st generation)";
    }

    public static double getTotalBalanceDue(BaseRaceEntry[] entries) {
        double total = 0.0;
        if (entries != null) {
            for (BaseRaceEntry entry : entries) {
                if (entry != null) {
                    total += entry.getBalanceDue();
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        SingleRunnerEntry runnerEntry = new SingleRunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runnerEntry.announce());
        System.out.println(eliteEntry.announce());
        System.out.println(relayEntry.announce());

        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));

        BaseRaceEntry[] entries = { runnerEntry, eliteEntry, relayEntry };
        System.out.println(getTotalBalanceDue(entries));
    }
}
