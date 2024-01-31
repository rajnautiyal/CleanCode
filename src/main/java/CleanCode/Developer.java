package CleanCode;

class Developer extends BaseEmployee {
    private final double overtimePay;

    public Developer(String name, double baseSalary, double overtimePay) {
        super(name, baseSalary);
        this.overtimePay = overtimePay;
    }

    // Overriding calculateSalary to include overtime pay
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + overtimePay;
    }
}