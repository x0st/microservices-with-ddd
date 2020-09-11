package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.reception.core.event.EventBus;
import microservices.reception.domain.employee.EmployeeRepository;
import microservices.reception.infrastructure.database.mysql.MySQLClient;
import microservices.reception.infrastructure.storage.EmployeeMySQLStorage;

final public class RepositoryModule extends AbstractModule {
    @Provides
    @Singleton
    static EmployeeRepository provideEmployeeRepository(MySQLClient mySQLClient, EventBus eventBus) {
        return new EmployeeMySQLStorage(mySQLClient, eventBus);
    }
}
