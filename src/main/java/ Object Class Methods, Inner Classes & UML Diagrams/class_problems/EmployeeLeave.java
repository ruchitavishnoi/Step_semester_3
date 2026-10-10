import java.time.LocalDate;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private String name;
    private String type;

    public Employee(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public abstract int getMaxLeaveDays();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) {
        super(name, "Full-time");
    }

    @Override
    public int getMaxLeaveDays() {
        return 20;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) {
        super(name, "Part-time");
    }

    @Override
    public int getMaxLeaveDays() {
        return 10;
    }
}

class ContractEmployee extends Employee {
    public ContractEmployee(String name) {
        super(name, "Contract");
    }

    @Override
    public int getMaxLeaveDays() {
        return 5;
    }
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void approve() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " + capitalize(status.name()) + " request cannot revert to Pending.");
            return;
        }
        this.status = LeaveStatus.APPROVED;
        System.out.println("Leave request for " + employee.getName() + " approved. Status: Approved.");
    }

    public void reject() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " + capitalize(status.name()) + " request cannot revert to Pending.");
            return;
        }
        this.status = LeaveStatus.REJECTED;
        System.out.println("Leave request for " + employee.getName() + " rejected. Status: Rejected.");
    }

    public void revertToPending() {
        if (status != LeaveStatus.PENDING) {
            System.out.println("Cannot change status: " + capitalize(status.name()) + " request cannot revert to Pending.");
            return;
        }
        this.status = LeaveStatus.PENDING;
    }

    private String capitalize(String name) {
        if (name == null || name.isEmpty()) return "";
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}

class LeaveManager {
    public LeaveRequest submitRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        LeaveRequest request = new LeaveRequest(employee, startDate, endDate);
        System.out.println("Leave request submitted by " + employee.getName() + " for " + startDate + " to " + endDate + ". Status: Pending.");
        return request;
    }

    public void approveRequest(LeaveRequest request) {
        request.approve();
    }

    public void rejectRequest(LeaveRequest request) {
        request.reject();
    }

    public void revertToPending(LeaveRequest request) {
        request.revertToPending();
    }
}

public class EmployeeLeave {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("John Doe");
        Employee jane = new PartTimeEmployee("Jane Smith");

        LeaveRequest req1 = manager.submitRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approveRequest(req1);

        LeaveRequest req2 = manager.submitRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        manager.revertToPending(req1);
    }
}
