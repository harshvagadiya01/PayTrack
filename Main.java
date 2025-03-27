import java.util.*;

abstract class Employee {

    final private String name;
    final private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    abstract double calculateSalary();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [ Name: " + name + ", ID: " + id + ", Salary: " + calculateSalary() + " ]";
    }
}

class FullTimeEmployee extends Employee {

    @SuppressWarnings("FieldMayBeFinal")
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {

    int hoursWorked;
    double hourlyRate;

    PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

class PayTrackSystem {
    private final HashSet<Integer> set;
    private final ArrayList<Employee> employees;

    public PayTrackSystem() {
        employees = new ArrayList<>();
        set = new HashSet<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        set.add(employee.getId());
    }

    public void removeEmployee(int id) {
        boolean isRemoved = employees.removeIf(e -> e.getId() == id);
        if(isRemoved) set.remove(id);
    }

    public Employee searchEmployee(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public double totalPayroll() {
        double sum = 0;
        for (Employee e : employees) {
            sum += e.calculateSalary();
        }
        return sum;
    }

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public void displayFullTimeEmployees() {
      for (Employee e : employees) {
          if (e instanceof FullTimeEmployee) {
              System.out.println(e);
          }
      }
    }
  
    public void displayPartTimeEmployees() {
        for (Employee e : employees) {
            if (e instanceof PartTimeEmployee) {
                System.out.println(e);
            }
        }
    }

    public boolean check(int i){
      return set.contains(i);
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PayTrackSystem system = new PayTrackSystem();

        while (true) {
            System.out.println("\n1.Add Emp  2.Remove Emp  3.Search Emp  4.Display All  5.Display Full-Time  6.Display Part-Time  7.Total Payroll  8.Exit");
            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    while(system.check(id)){
                      System.out.println("This id is already assigned to someone else. Enter other one:");
                      id = sc.nextInt();
                    }
                    System.out.print("Type (1. Full-Time, 2. Part-Time): ");
                    if (sc.nextInt() == 1) {
                        System.out.print("Salary: ");
                        system.addEmployee(new FullTimeEmployee(name, id, sc.nextDouble()));
                    } else {
                        System.out.print("Hours Worked: ");
                        int hours = sc.nextInt();
                        System.out.print("Hourly Rate: ");
                        system.addEmployee(new PartTimeEmployee(name, id, hours, sc.nextDouble()));
                    }
                }
                case 2 -> {
                    System.out.print("ID: ");
                    system.removeEmployee(sc.nextInt());
                }
                case 3 -> {
                    System.out.print("ID: ");
                    Employee emp = system.searchEmployee(sc.nextInt());
                    System.out.println(emp == null ? "Not Found!" : emp);
                }
                case 4 ->
                    system.displayEmployees();
                case 5 ->
                    system.displayFullTimeEmployees();
                case 6 ->
                    system.displayPartTimeEmployees();
                case 7 ->
                    System.out.println("Total Payroll: " + system.totalPayroll());
                case 8 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default ->
                    System.out.println("Invalid Option!");
            }
        }
    }
}