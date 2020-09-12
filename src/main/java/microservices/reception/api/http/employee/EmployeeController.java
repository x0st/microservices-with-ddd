package microservices.reception.api.http.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import microservices.reception.api.http.JsonResponse;
import microservices.reception.api.http.Request;
import microservices.reception.api.http.Response;
import microservices.reception.domain.employee.query.Employee;
import microservices.reception.domain.employee.query.EmployeesQuery;

import static microservices.reception.core.json.Factory.obj;
import static microservices.reception.core.json.Factory.arr;

final public class EmployeeController {
    private final EmployeesQuery employeesQuery;

    public EmployeeController(EmployeesQuery employeesQuery) {
        this.employeesQuery = employeesQuery;
    }

    public Response list(Request baseRequest) {
        EmployeeListRequest request = EmployeeListRequest.fromBase(baseRequest);

        List<Employee> records = this.employeesQuery
                .withFilters(
                        new EmployeesQuery.Filters()
                            .withFirstName(request.firstName())
                            .withLastName(request.lastName())
                )
                .records();

        JSONArray responseArray = arr();
        JSONObject responseObject = obj().fluentPut("data", responseArray);

        for (Employee record : records) {
            responseArray.add(
                obj()
                        .fluentPut("id", record.id)
                        .fluentPut("fistName", record.firstName)
                        .fluentPut("lastName", record.lastName)
            );
        }

        return JsonResponse.ok(responseObject);
    }
}
