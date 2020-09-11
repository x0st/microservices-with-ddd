package microservices.reception.domain.employee;

import microservices.reception.core.Repository;
import microservices.reception.domain.employee.exception.EmployeeNotFound;

public interface EmployeeRepository extends Repository<Employee, EmployeeNotFound> {
}
