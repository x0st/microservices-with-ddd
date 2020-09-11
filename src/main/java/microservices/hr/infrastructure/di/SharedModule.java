package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.core.event.EventBus;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.outbox.Outbox;
import microservices.hr.core.command.CommandBus;
import microservices.hr.infrastructure.outbox.OutboxMySQLStorage;

final public class SharedModule extends AbstractModule {
    @Provides
    @Singleton
    static MySQLClient provideMySQLClient() {
        return new MySQLClient("127.0.0.1", 3306, "hr", "user", "password");
    }

    @Provides
    @Singleton
    static Outbox provideOutbox() {
        return new OutboxMySQLStorage();
    }

    @Provides
    @Singleton
    static EventBus provideEventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    static CommandBus provideCommandBus() {
        return new CommandBus();
    }
}
