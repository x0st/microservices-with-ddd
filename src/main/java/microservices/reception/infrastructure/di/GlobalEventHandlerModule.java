package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.reception.api.event.EmployeeRegisteredEventHandler;
import microservices.reception.core.GlobalEventHandlerMap;
import microservices.reception.domain.employee.EmployeeRepository;

final public class GlobalEventHandlerModule extends AbstractModule {
    @Provides
    @Singleton
    GlobalEventHandlerMap provideGlobalEventHandlerMap(EmployeeRegisteredEventHandler employeeRegistered) {
        GlobalEventHandlerMap workerRegistry;

        workerRegistry = new GlobalEventHandlerMap();
        workerRegistry.map("EmployeeRegistered", employeeRegistered);

        return workerRegistry;
    }

    @Provides
    static EmployeeRegisteredEventHandler provideEmployeeRegisteredEventHandler(EmployeeRepository repository) {
        return new EmployeeRegisteredEventHandler(repository);
    }
}
