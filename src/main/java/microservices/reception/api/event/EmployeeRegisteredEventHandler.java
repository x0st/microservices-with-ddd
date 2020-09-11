package microservices.reception.api.event;

import com.alibaba.fastjson.JSONObject;

import java.util.UUID;

import microservices.reception.core.GlobalEventHandler;
import microservices.reception.domain.employee.Employee;
import microservices.reception.domain.employee.EmployeeRepository;

final public class EmployeeRegisteredEventHandler implements GlobalEventHandler {
    private final EmployeeRepository employeeRepository;

    public EmployeeRegisteredEventHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void process(JSONObject object) {
        Employee employee = Employee.add(
                UUID.fromString(object.getString("id")),
                object.getString("firstName"),
                object.getString("lastName")
        );

        this.employeeRepository.persist(employee);
    }
}
