package microservices.hr.domain.employee.exception;

import microservices.hr.core.DomainException;

final public class EmployeeNotFound extends DomainException {
    public EmployeeNotFound() {
        super("Employee not found.");
    }
}
