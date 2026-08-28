import java.util.Scanner;

class SrmStudent {
    String name;
    String regNo;
    int attendance;

    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }

    public boolean isEligible() {
        return this.attendance >= 75;
    }

    public static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) {
            return 0.0;
        }
        double total = 0;
        for (SrmStudent student : students) {
            total += student.attendance;
        }
        return total / students.length;
    }
}

public class AttendanceSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SrmStudent[] students = new SrmStudent[5];

        if (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.replace("5 students:", "").trim();
            
            String[] pairs = line.split(",");
            for (int i = 0; i < pairs.length && i < 5; i++) {
                String[] parts = pairs[i].trim().split("\\s+");
                String name = parts[0];
                int attendance = Integer.parseInt(parts[1]);
                students[i] = new SrmStudent(name, "REG" + (101 + i), attendance);
            }
        }

        for (SrmStudent s : students) {
            if (s != null) {
                String status = s.isEligible() ? "Eligible" : "Detained";
                System.out.println(s.name + " - " + s.attendance + "% - " + status);
            }
        }

        double avg = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%\n", avg);

        sc.close();
    }
}
