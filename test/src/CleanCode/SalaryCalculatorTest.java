package CleanCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class SalaryCalculatorTest {

    SalaryCalculable salaryCalculable;

    Employee developer;
    Employee manager;


    @BeforeEach
    void init() {
        developer=new Developer("dev1",1200,100);
        manager=new Manager("manager",300,100);
        //MockitoAnnotations.openMocks(this);
    }


    @DisplayName("Testing the developer salary and name ")
    @Test
    void test_developerSalary() {
        salaryCalculable = new SalaryCalculator(developer);
        // Arrange

        //assert
        assertEquals("dev",developer.getName());
        assertEquals(1300, salaryCalculable.calculateSalary(), "developer salary is corrcet");
        //assertEquals(lastName, user.get(), "User's last name is incorrect");
    }

    @DisplayName("Testing the manager salary and name ")
    @Test
    void test_ManagerSalary() {
        salaryCalculable = new SalaryCalculator(manager);
        // Arrange

        //assert
        assertEquals("manager",manager.getName());
        assertEquals(30000, salaryCalculable.calculateSalary(), "developer salary is corrcet");
        //assertEquals(lastName, user.get(), "User's last name is incorrect");
    }
}
