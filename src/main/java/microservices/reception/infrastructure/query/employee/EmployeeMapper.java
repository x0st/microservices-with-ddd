package microservices.reception.infrastructure.query.employee;

import microservices.reception.domain.employee.query.Employee;
import microservices.reception.infrastructure.database.mysql.ResultSet;

public class EmployeeMapper {
    public Employee employee(ResultSet resultSet) {
        Employee employee = new Employee();

        employee.id = resultSet.getUUID("employee.id");
        employee.lastName = resultSet.getString("employee.lastName");
        employee.firstName = resultSet.getString("employee.firstName");

        return employee;
    }
}
