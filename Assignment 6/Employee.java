import java.util.Scanner;

public abstract class Employee {
    protected String name;
    protected String panNo;
    protected String joiningDate;
    protected String designation;
    protected int empId;
    protected PayRoll payroll;
    protected static Scanner sc = new Scanner(System.in);

    // Helper method to safely read integers
    protected static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Helper method to safely read doubles
    protected static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public Employee() {
        this.payroll = new PayRoll();
        this.empId = readInt("Enter ID: ");
        System.out.print("Enter Name: ");
        this.name = sc.nextLine();
        System.out.print("Enter PAN No.: ");
        this.panNo = sc.nextLine();
        System.out.print("Enter Joining Date: ");
        this.joiningDate = sc.nextLine();
        System.out.print("Enter Designation (Role): ");
        this.designation = sc.nextLine();
    }

    public abstract double calcCTC();

    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }
}
