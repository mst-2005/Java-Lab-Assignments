public class FullTimeEmployee extends Employee {

    public FullTimeEmployee() {
        super();
        this.payroll.baseSalary = readDouble("Enter Base Salary: ");
        this.payroll.benefits = readDouble("Enter Benefits: ");
        this.payroll.healthInsurance = readDouble("Enter Health Insurance: ");
        
        if (this.designation.equalsIgnoreCase("SWE")) {
            this.payroll.perfBonus = readDouble("Enter Performance Bonus: ");
        } else if (this.designation.equalsIgnoreCase("HR")) {
            this.payroll.hiringCommision = readDouble("Enter Hiring Commission: ");
        }
    }

    @Override
    public double calcCTC() {
        double ctc = payroll.baseSalary + payroll.benefits + payroll.healthInsurance;
        if (this.designation.equalsIgnoreCase("SWE")) {
            return ctc + payroll.perfBonus;
        } else if (this.designation.equalsIgnoreCase("HR")) {
            return ctc + payroll.hiringCommision;
        }
        return ctc;
    }
}
