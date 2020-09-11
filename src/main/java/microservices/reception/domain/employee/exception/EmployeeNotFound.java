package microservices.reception.domain.employee.exception;

import microservices.reception.core.DomainException;

final public class EmployeeNotFound extends DomainException {
    public EmployeeNotFound() {
        super("Employee not found.");
    }
}
