package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import microservices.reception.api.event.EmployeeRegisteredEventHandler;
import microservices.reception.domain.employee.EmployeeRepository;

final public class GlobalEventHandlerModule extends AbstractModule {
    @Provides
    static EmployeeRegisteredEventHandler provideEmployeeRegisteredEventHandler(EmployeeRepository repository) {
        return new EmployeeRegisteredEventHandler(repository);
    }
}
