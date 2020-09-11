package microservices.hr.domain.employee.event;

import microservices.hr.core.event.Event;
import microservices.hr.core.event.Publishable;
import microservices.hr.domain.employee.Employee;

import static microservices.hr.core.json.Factory.obj;

final public class EmployeeRegisteredEvent implements Event, Publishable {
    private final Employee employee;

    public EmployeeRegisteredEvent(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String name() {
        return "employee.registered";
    }

    @Override
    public String serialize() {
        return obj()
                .fluentPut("id", employee.id())
                .fluentPut("firstName", employee.firstName())
                .fluentPut("lastName", employee.lastName())
                .fluentPut("age", employee.age())
                .toJSONString();
    }
}
