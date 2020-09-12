package microservices.reception.infrastructure.query.employee;

import java.util.LinkedList;
import java.util.List;

import microservices.reception.domain.employee.query.Employee;
import microservices.reception.domain.employee.query.EmployeesQuery;
import microservices.reception.infrastructure.database.mysql.MySQLClient;
import microservices.reception.infrastructure.database.mysql.ResultSet;
import microservices.reception.infrastructure.database.mysql.SelectQueryBuilder;

public class EmployeesMySQLQuery implements EmployeesQuery {
    private final MySQLClient mySQLClient;
    private final SelectQueryBuilder queryBuilder;
    private final EmployeeMapper mapper;

    public EmployeesMySQLQuery(MySQLClient mySQLClient, EmployeeMapper mapper) {
        this.mySQLClient = mySQLClient;
        this.mapper = mapper;

        this.queryBuilder = this.mySQLClient.connection()
                .selectQuery()
                .from("employees AS employee")
                .select(
                        "employee.id AS employee.id",
                        "employee.firstName AS employee.firstName",
                        "employee.lastName AS employee.lastName"
                );
    }

    @Override
    public List<Employee> records() {
        List<Employee> employees = new LinkedList<>();

        ResultSet resultSet = this.queryBuilder
                .toPreparedStatement()
                .withResult();

        while (resultSet.next()) {
            employees.add(this.mapper.employee(resultSet));
        }

        return employees;
    }

    @Override
    public EmployeesQuery withFilters(Filters filters) {
        if (filters.hasFirstName()) {
            this.queryBuilder.where("employee.firstName", "=", filters.getFirstName());
        }

        if (filters.hasLastName()) {
            this.queryBuilder.where("employee.lastName", "=", filters.getLastName());
        }

        return this;
    }
}
