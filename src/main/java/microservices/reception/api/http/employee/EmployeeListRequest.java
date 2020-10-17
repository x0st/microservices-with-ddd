package microservices.reception.api.http.employee;

import microservices.reception.api.http.Request;

final public class EmployeeListRequest {
    private final String firstName;
    private final String lastName;

    EmployeeListRequest(Request request) {
        this.firstName = request.queryParam("firstName");
        this.lastName = request.queryParam("lastName");
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }
}
