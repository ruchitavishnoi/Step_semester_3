class Employee {
    private String empId;
    private String empName;
    private double salary;

    public Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class HREmployee {
    public static void main(String[] args) {
        Employee plain = new Employee("E101", "Alice", 40000);
        Employee manager = new ManagerEmployee("E102", "Bob", 70000, 8000);
        Employee intern = new InternEmployee("E103", "Charlie", 12000, 10000);

        Employee[] employees = { plain, manager, intern };

        for (Employee emp : employees) {
            if (emp instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) emp;
                System.out.println("Manager effective pay: Rs " + m.effectiveSalary());
            } else if (emp instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) emp;
                System.out.println("Intern effective pay: Rs " + i.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + emp.getSalary());
            }
        }
    }
}
