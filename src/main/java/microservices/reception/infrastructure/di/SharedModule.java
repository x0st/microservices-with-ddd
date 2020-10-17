package microservices.reception.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.reception.api.event.EmployeeRegisteredEventHandler;
import microservices.reception.core.GlobalEventHandlerMap;
import microservices.reception.core.event.EventBus;
import microservices.reception.infrastructure.database.mysql.MySQLClient;

final public class SharedModule extends AbstractModule {
    @Provides
    @Singleton
    MySQLClient provideMySQLClient() {
        return new MySQLClient("127.0.0.1", 3307, "reception", "user", "password");
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new EventBus();
    }
}
