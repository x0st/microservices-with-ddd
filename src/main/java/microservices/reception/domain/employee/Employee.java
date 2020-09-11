package microservices.reception.domain.employee;

import java.util.UUID;

import microservices.reception.core.AggregateRoot;

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

    public static Employee add(UUID id, String firstName, String lastName) {
        return new Employee(id, firstName, lastName, null);
    }
}
