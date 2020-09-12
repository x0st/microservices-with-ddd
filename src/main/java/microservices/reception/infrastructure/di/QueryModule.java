package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import microservices.reception.domain.employee.query.EmployeesQuery;
import microservices.reception.infrastructure.database.mysql.MySQLClient;
import microservices.reception.infrastructure.query.employee.EmployeeMapper;
import microservices.reception.infrastructure.query.employee.EmployeesMySQLQuery;

final public class QueryModule extends AbstractModule {
    @Provides
    static EmployeesQuery provideEmployeesQuery(MySQLClient mySQLClient) {
        return new EmployeesMySQLQuery(mySQLClient, new EmployeeMapper());
    }
}
