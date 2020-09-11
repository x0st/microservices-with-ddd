package microservices.hr.domain.employee;

import microservices.hr.core.Repository;
import microservices.hr.domain.employee.exception.EmployeeNotFound;

public interface EmployeeRepository extends Repository<Employee, EmployeeNotFound> {
}
