package microservices.reception.infrastructure.storage;

import java.util.HashSet;
import java.util.UUID;

import microservices.reception.core.event.EventBus;
import microservices.reception.domain.employee.Employee;
import microservices.reception.domain.employee.EmployeeRepository;
import microservices.reception.domain.employee.exception.EmployeeNotFound;
import microservices.reception.infrastructure.database.mysql.Connection;
import microservices.reception.infrastructure.database.mysql.MySQLClient;
import microservices.reception.infrastructure.database.mysql.ResultSet;

final public class EmployeeMySQLStorage implements EmployeeRepository {
    private final EventBus eventBus;
    private final MySQLClient mySQLClient;
    private final HashSet<UUID> records = new HashSet<>(1024);

    public EmployeeMySQLStorage(MySQLClient mySQLClient, EventBus eventBus) {
        this.eventBus = eventBus;
        this.mySQLClient = mySQLClient;
    }

    @Override
    public void persist(Employee entity) {
        this.mySQLClient.connection().transaction((connection) -> {
            if (this.records.contains(entity.id())) {
                this.update(entity, connection);
            } else {
                this.create(entity, connection);
            }

            entity.domainEvents().forEach(this.eventBus::publish);
            entity.clearDomainEvents();
        });
    }

    @Override
    public Employee find(UUID identifier) {
        ResultSet resultSet = this.mySQLClient
                .connection()
                .preparedStatement("SELECT id, firstName, lastName, age FROM `employees` WHERE id = ?")
                .setUUID(1, identifier)
                .withResult();

        if (!resultSet.first()) return null;

        Employee employee = new Employee(
                resultSet.getUUID(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4)
        );

        this.records.add(employee.id());

        return employee;
    }

    @Override
    public Employee get(UUID identifier) throws EmployeeNotFound {
        Employee employee = this.find(identifier);
        if (null == employee) throw new EmployeeNotFound();
        return employee;
    }

    private void create(Employee employee, Connection connection) {
        connection.preparedStatement("INSERT INTO `employees` (id, firstName, lastName, age) VALUES (?, ?, ?, ?)")
                .setUUID(1, employee.id())
                .setString(2, employee.firstName())
                .setString(3, employee.lastName())
                .setInt(4, employee.age())
                .withoutResult();
    }

    private void update(Employee employee, Connection connection) {
        connection.preparedStatement("UPDATE `employees` SET firstName = ?, lastName = ?, age = ? WHERE id = ?")
                .setString(1, employee.firstName())
                .setString(2, employee.lastName())
                .setInt(3, employee.age())
                .setUUID(4, employee.id())
                .withoutResult();
    }
}
