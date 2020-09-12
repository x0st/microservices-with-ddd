package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.reception.api.http.employee.EmployeeController;
import microservices.reception.domain.employee.query.EmployeesQuery;

final public class ControllerModule extends AbstractModule {
    @Provides
    @Singleton
    static EmployeeController provideEmployeeController(EmployeesQuery employeesQuery) {
        return new EmployeeController(employeesQuery);
    }
}
