package CleanCode;

public class BaseEmployee implements Employee{
    private final String name;
    private final double baseSalary;

    public BaseEmployee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double calculateSalary() {
        return baseSalary;
    }
}
