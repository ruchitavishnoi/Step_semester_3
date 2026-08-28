import java.util.Scanner;

public class StudentRecord {

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
        } else {
            System.out.println("Name: " + fields[0].trim() + " | Roll No: " + fields[1].trim() + " | Dept: " + fields[2].trim());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter CSV line: ");
        String csvLine = sc.nextLine();

        parseStudentRecord(csvLine);

        sc.close();
    }
}
