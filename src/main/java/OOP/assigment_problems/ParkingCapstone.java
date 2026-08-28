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

class ParkingSlot {
    String slotNo;
    int capacity;
    int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }
}

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    private double getEffectivePay() {
        if (employee instanceof ManagerEmployee) {
            return ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            return ((InternEmployee) employee).effectiveSalary();
        }
        return employee.getSalary();
    }

    public String fullProfile() {
        String slotInfo = (slot != null) ? slot.slotNo : "no parking assigned";
        return name + " | Pay: Rs " + getEffectivePay() + " | Slot: " + slotInfo;
    }
}

public class ParkingCapstone {
    public static void main(String[] args) {
        Employee e1 = new ManagerEmployee("E101", "Divya", 70000, 8000);
        Employee e2 = new Employee("E102", "Karan", 40000);
        Employee e3 = new InternEmployee("E103", "Meera", 12000, 10000);

        ParkingSlot slotA1 = new ParkingSlot("A1", 4, 3);
        ParkingSlot slotA2 = new ParkingSlot("A2", 5, 4);

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "E101", e1, slotA1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", e2, slotA2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "E103", e3, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
