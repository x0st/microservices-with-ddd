package microservices.reception.api.http.employee;

import microservices.reception.api.http.Request;

final public class EmployeeListRequest {
    private final String firstName;
    private final String lastName;

    private EmployeeListRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public static EmployeeListRequest fromBase(Request request) {
        return new EmployeeListRequest(
                request.queryParam("firstName"),
                request.queryParam("lastName")
        );
    }
}
