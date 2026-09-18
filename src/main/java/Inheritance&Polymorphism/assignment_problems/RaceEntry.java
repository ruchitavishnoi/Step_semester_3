public class RaceEntry {
    private String bibNumber;
    private double entryFee;
    private double paidAmount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.bibNumber = bibNumber.trim();
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

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        if (bibNumbers != null) {
            for (String bib : bibNumbers) {
                try {
                    new RaceEntry(bib, entryFee);
                    registered++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        public String getCategory() {
            return category;
        }
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());

        String[] batch = {"BIB1", "B1", "BIB2"};
        System.out.println(RaceEntry.registerBatch(batch, 80));
    }
}
