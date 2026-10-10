import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private int[] marks;

    public Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public int[] getMarks() {
        return marks;
    }

    public int getTotalMarks() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }
}

class StudentMarksGrid {
    private String[] subjects;
    private List<Student> students;

    public StudentMarksGrid(String[] subjects) {
        this.subjects = subjects;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printTotals() {
        StringBuilder sb = new StringBuilder("Totals ");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            sb.append(s.getName()).append(" ").append(s.getTotalMarks());
            if (i < students.size() - 1) {
                sb.append(", ");
            }
        }
        System.out.println(sb.toString());
    }

    public void printToppers() {
        StringBuilder sb = new StringBuilder("toppers ");
        for (int subjIdx = 0; subjIdx < subjects.length; subjIdx++) {
            String topperName = "";
            int maxMark = -1;

            for (Student s : students) {
                int[] marks = s.getMarks();
                if (subjIdx < marks.length) {
                    if (marks[subjIdx] > maxMark) {
                        maxMark = marks[subjIdx];
                        topperName = s.getName();
                    }
                }
            }

            if (maxMark != -1) {
                sb.append(subjects[subjIdx]).append(" ").append(topperName).append(" ").append(maxMark);
                if (subjIdx < subjects.length - 1) {
                    sb.append(", ");
                }
            }
        }
        System.out.println(sb.toString());
    }
}

public class StudentMarks {
    public static void main(String[] args) {
        String[] subjects = {"Math", "Sci", "Eng"};
        StudentMarksGrid grid = new StudentMarksGrid(subjects);

        grid.addStudent(new Student("Anu", new int[]{80, 90, 70}));
        grid.addStudent(new Student("Ravi", new int[]{60, 85}));
        grid.addStudent(new Student("Meena", new int[]{95, 75, 88}));

        grid.printTotals();
        grid.printToppers();
    }
}
