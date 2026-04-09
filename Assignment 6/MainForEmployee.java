public class MainForEmployee {
    public static void main(String[] args) {
        try {
            Employee[] employees = new Employee[3];
            double[] salary = new double[3];

            System.out.println("Enter Details for Full Time Employee (e.g. SWE or HR)");
            employees[0] = new FullTimeEmployee();
            salary[0] = employees[0].calcCTC();

            System.out.println("\nEnter Details for Contract Employee");
            employees[1] = new ContractEmployee();
            salary[1] = employees[1].calcCTC();

            System.out.println("\nEnter Details for Manager");
            employees[2] = new Manager();
            salary[2] = employees[2].calcCTC();

            // Header
            System.out.println("\n" + "=".repeat(70));
            System.out.printf("%-10s %-15s %-15s %-15s\n", "ID", "Name", "Designation", "Total CTC");
            System.out.println("=".repeat(70));

            // Displaying details and salaries
            for (int i = 0; i < employees.length; i++) {
                System.out.printf("%-10d %-15s %-15s %-15.2f\n", 
                    employees[i].getEmpId(), 
                    employees[i].getName(), 
                    employees[i].getDesignation(), 
                    salary[i]);
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
