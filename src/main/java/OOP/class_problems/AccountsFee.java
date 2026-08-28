class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        }
    }

    public double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;

    public ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
        super(regNo, totalFee);
        this.scholarshipPercent = scholarshipPercent;
    }

    public double effectiveDue() {
        return getDue() * (1 - scholarshipPercent / 100);
    }
}

public class AccountsFee {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("S101", 150000);
        FeeAccount hostel = new HostelFeeAccount("S102", 200000);
        FeeAccount scholarship = new ScholarshipFeeAccount("S103", 180000, 20);

        plain.pay(150000);

        FeeAccount[] accounts = { plain, hostel, scholarship };

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount s = (ScholarshipFeeAccount) acc;
                System.out.println("Scholarship account effective due: Rs " + s.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                HostelFeeAccount h = (HostelFeeAccount) acc;
                h.payInTwoInstallments(60000);
                System.out.println("Hostel account due: Rs " + h.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}
