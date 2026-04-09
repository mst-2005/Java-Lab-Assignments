public class ContractEmployee extends Employee {

    public ContractEmployee() {
        super();
        this.payroll.noOfHrs = readDouble("Enter Total Hours Worked: ");
        this.payroll.hourlyRate = readDouble("Enter Hourly Rate: ");
    }

    @Override
    public double calcCTC() {
        return payroll.noOfHrs * payroll.hourlyRate;
    }
}
