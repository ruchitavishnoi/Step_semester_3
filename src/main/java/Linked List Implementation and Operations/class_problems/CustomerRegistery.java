import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

class CustomerRegistry {
    private Set<Customer> customers = new HashSet<>();

    public boolean addCustomer(Customer customer) {
        return customers.add(customer);
    }

    public boolean containsCustomer(Customer customer) {
        return customers.contains(customer);
    }

    public int getUniqueCount() {
        return customers.size();
    }
}

public class CustomerRegistery {
    public static void main(String[] args) {
        CustomerRegistry registry = new CustomerRegistry();

        Customer c1 = new Customer(101, "Asha");
        Customer c1Duplicate = new Customer(101, "Asha");
        Customer c2 = new Customer(102, "Ravi");

        boolean added1 = registry.addCustomer(c1);
        System.out.println(added1);

        boolean added2 = registry.addCustomer(c1Duplicate);
        if (added2) {
            System.out.println(added2);
        } else {
            System.out.println("false (duplicate rejected)");
        }

        boolean added3 = registry.addCustomer(c2);
        System.out.println(added3);

        System.out.println("unique count " + registry.getUniqueCount());

        System.out.println("contains: " + registry.containsCustomer(new Customer(101, "Asha")));
    }
}
