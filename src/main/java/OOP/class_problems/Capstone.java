class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
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
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    public SrmStudent(String name, String regNo, double totalFee) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = new HostelFeeAccount(regNo, totalFee);
        this.room = null;
        totalStudents++;
    }

    public String fullStatus() {
        String roomNumber = (room != null) ? room.roomNo : "unallotted";
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomNumber;
    }
}

public class Capstone {
    public static void main(String[] args) {
        SrmStudent s1 = new SrmStudent("Ravi", "RA101", 200000);
        SrmStudent s2 = new SrmStudent("Anitha", "RA102", 200000);
        SrmStudent s3 = new SrmStudent("Karthik", "RA103", 200000);

        s1.room = new HostelRoom("C-214", 3, 2);
        s2.room = new HostelRoom("C-507", 2, 1);

        s1.feeAccount.pay(60000);
        s2.feeAccount.pay(20000);
        s2.feeAccount.pay(-5000);

        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}
