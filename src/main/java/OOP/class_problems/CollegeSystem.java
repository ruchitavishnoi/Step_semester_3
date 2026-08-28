import java.util.Scanner;

class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }
}
class SrmStudent {
    String name;
    String regNo;
    int attendance;

    static String university = "SRM University";
    static int admissionCount = 0;
    private static final int BASE_REG_ID = 1010;

    public SrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        admissionCount++;
        this.regNo = "RA23110030" + (BASE_REG_ID + admissionCount);
    }

    public void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class CollegeSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name for student 1: ");
        String name1 = sc.next();
        System.out.print("Enter attendance for student 1: ");
        int att1 = sc.nextInt();

        System.out.print("Enter name for student 2: ");
        String name2 = sc.next();
        System.out.print("Enter attendance for student 2: ");
        int att2 = sc.nextInt();

        System.out.println("\n--- Broken Version ---");
        BrokenSrmStudent b1 = new BrokenSrmStudent(name1, "RA101", att1);
        BrokenSrmStudent b2 = new BrokenSrmStudent(name2, "RA102", att2);

        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name);
        System.out.println("(" + name1 + "'s data was overwritten \u2013 both students now show \"" + name2 + "\")\n");

        System.out.println("--- Fixed Version ---");
        SrmStudent s1 = new SrmStudent(name1, att1);
        SrmStudent s2 = new SrmStudent(name2, att2);

        s1.printIdCard();
        s2.printIdCard();
        SrmStudent.printTotalAdmissions();

        sc.close();
    }
}
