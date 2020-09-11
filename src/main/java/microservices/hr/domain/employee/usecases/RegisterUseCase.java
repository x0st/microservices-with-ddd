package microservices.hr.domain.employee.usecases;

import microservices.hr.core.command.Command;
import microservices.hr.core.command.UseCase;
import microservices.hr.domain.employee.Employee;
import microservices.hr.domain.employee.EmployeeRepository;

final public class RegisterUseCase implements UseCase<RegisterUseCase.RegisterCommand, Void> {
    private final EmployeeRepository employeeRepository;

    public RegisterUseCase(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Void exec(RegisterCommand command) throws Exception {
        Employee employee;
        employee = Employee.add(command.firstName, command.lastName);

        this.employeeRepository.persist(employee);

        return null;
    }

    public static class RegisterCommand implements Command<Void> {
        private final String firstName;
        private final String lastName;

        public RegisterCommand(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
