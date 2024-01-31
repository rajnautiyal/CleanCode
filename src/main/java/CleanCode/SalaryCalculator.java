package CleanCode;

public class SalaryCalculator  implements  SalaryCalculable{

    private final Employee employee;

    public SalaryCalculator(Employee employee) {
        this.employee = employee;
    }

    public double calculateSalary() {
        return employee.calculateSalary();
    }
}
