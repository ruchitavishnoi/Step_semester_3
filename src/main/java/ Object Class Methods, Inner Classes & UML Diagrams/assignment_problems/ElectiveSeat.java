import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

interface CreditPolicy {
    int getCreditLimit();
    String getTypeName();
}

class RegularCreditPolicy implements CreditPolicy {
    @Override
    public int getCreditLimit() {
        return 24;
    }

    @Override
    public String getTypeName() {
        return "Regular";
    }
}

class HonorsCreditPolicy implements CreditPolicy {
    @Override
    public int getCreditLimit() {
        return 28;
    }

    @Override
    public String getTypeName() {
        return "Honors";
    }
}

class ExchangeCreditPolicy implements CreditPolicy {
    @Override
    public int getCreditLimit() {
        return 20;
    }

    @Override
    public String getTypeName() {
        return "Exchange";
    }
}

class Student {
    private String name;
    private CreditPolicy creditPolicy;
    private int currentCredits;

    public Student(String name, CreditPolicy creditPolicy, int currentCredits) {
        this.name = name;
        this.creditPolicy = creditPolicy;
        this.currentCredits = currentCredits;
    }

    public String getName() {
        return name;
    }

    public CreditPolicy getCreditPolicy() {
        return creditPolicy;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public boolean canAddCredits(int credits) {
        return currentCredits + credits <= creditPolicy.getCreditLimit();
    }

    public void addCredits(int credits) {
        this.currentCredits += credits;
    }

    public void removeCredits(int credits) {
        this.currentCredits -= credits;
    }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;
    private List<Student> enrolledStudents;
    private Queue<Student> waitlist;

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
        this.waitlist = new ArrayDeque<>();
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    public boolean isWaitlisted(Student student) {
        return waitlist.contains(student);
    }

    public boolean enrollOrWaitlist(Student student) {
        if (isEnrolled(student) || isWaitlisted(student)) {
            System.out.println("Enrollment failed: " + student.getName() + " is already enrolled or waitlisted.");
            return false;
        }

        if (!student.canAddCredits(credits)) {
            int futureCredits = student.getCurrentCredits() + credits;
            System.out.println("Enrollment failed: " + student.getName() + " would exceed the " +
                    student.getCreditPolicy().getTypeName() + " credit limit (" + futureCredits + "/" +
                    student.getCreditPolicy().getCreditLimit() + ").");
            return false;
        }

        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.addCredits(credits);
            System.out.println(student.getName() + " enrolled in " + name + " (credits: " +
                    student.getCurrentCredits() + "/" + student.getCreditPolicy().getCreditLimit() + ").");
            return true;
        } else {
            waitlist.add(student);
            System.out.println(name + " is full. " + student.getName() + " added to waitlist (position " +
                    waitlist.size() + ").");
            return true;
        }
    }

    public boolean drop(Student student) {
        if (!enrolledStudents.contains(student)) {
            if (waitlist.contains(student)) {
                waitlist.remove(student);
                System.out.println(student.getName() + " removed from waitlist for " + name + ".");
                return true;
            }
            System.out.println(student.getName() + " is not enrolled in " + name + ".");
            return false;
        }

        enrolledStudents.remove(student);
        student.removeCredits(credits);
        System.out.println(student.getName() + " dropped " + name + " (credits: " +
                student.getCurrentCredits() + "/" + student.getCreditPolicy().getCreditLimit() + ").");

        promoteNextWaitlistedStudent();
        return true;
    }

    private void promoteNextWaitlistedStudent() {
        while (!waitlist.isEmpty() && enrolledStudents.size() < capacity) {
            Student nextStudent = waitlist.poll();
            if (nextStudent.canAddCredits(credits)) {
                enrolledStudents.add(nextStudent);
                nextStudent.addCredits(credits);
                System.out.println(nextStudent.getName() + " promoted from waitlist and enrolled in " +
                        name + " (credits: " + nextStudent.getCurrentCredits() + "/" +
                        nextStudent.getCreditPolicy().getCreditLimit() + ").");
                break;
            } else {
                System.out.println("Promotion failed for " + nextStudent.getName() +
                        ": credit limit exceeded upon promotion.");
            }
        }
    }
}

class EnrollmentService {
    public boolean enroll(Student student, Elective elective) {
        return elective.enrollOrWaitlist(student);
    }

    public boolean drop(Student student, Elective elective) {
        return elective.drop(student);
    }
}

public class ElectiveSeat {
    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", new RegularCreditPolicy(), 20);
        Student ravi = new Student("Ravi", new HonorsCreditPolicy(), 22);
        Student neha = new Student("Neha", new ExchangeCreditPolicy(), 12);
        Student kiran = new Student("Kiran", new RegularCreditPolicy(), 22);

        EnrollmentService service = new EnrollmentService();

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);

        service.drop(asha, cloud);
    }
}
