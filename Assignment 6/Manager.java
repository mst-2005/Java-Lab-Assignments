public class Manager extends FullTimeEmployee {

    public Manager() {
        super();
        this.payroll.perfBonus = readDouble("Enter Performance Bonus: ");
        this.payroll.ta = readDouble("Enter Travel Allowance (TA): ");
        this.payroll.eduAllowance = readDouble("Enter Education Allowance: ");
    }

    @Override
    public double calcCTC() {
        return payroll.baseSalary + payroll.perfBonus + payroll.ta + payroll.eduAllowance + payroll.benefits + payroll.healthInsurance;
    }
}
