package CleanCode;

class Manager extends BaseEmployee {
    private final double bonus;

    public Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }

    // Overriding calculateSalary to include bonus
    @Override
    public double calculateSalary() {
        return super.calculateSalary() * bonus;
    }
}
