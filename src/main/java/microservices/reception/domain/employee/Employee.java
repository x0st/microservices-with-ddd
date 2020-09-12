package microservices.reception.domain.employee;

import java.util.UUID;

import microservices.reception.core.AggregateRoot;

final public class Employee extends AggregateRoot {
    private UUID id;
    private String firstName;
    private String lastName;

    public Employee(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    @Override
    public UUID id() {
        return id;
    }

    public static Employee add(UUID id, String firstName, String lastName) {
        return new Employee(id, firstName, lastName);
    }
}
