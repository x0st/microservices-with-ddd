package microservices.hr.domain.employee;

import java.util.UUID;

import microservices.hr.core.AggregateRoot;
import microservices.hr.domain.employee.event.EmployeeRegisteredEvent;

final public class Employee extends AggregateRoot {
    private UUID id;
    private Integer age;
    private String firstName;
    private String lastName;

    public Employee(UUID id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public static Employee add(String firstName, String lastName) {
        Employee employee;
        employee = new Employee(UUID.randomUUID(), firstName, lastName, null);
        employee.registerEvent(new EmployeeRegisteredEvent(employee));
        return employee;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Integer age() {
        return age;
    }

    @Override
    public UUID id() {
        return id;
    }
}
