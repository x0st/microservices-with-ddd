package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.io.IOException;

import microservices.hr.core.event.EventBus;
import microservices.hr.core.Logger;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.logging.FileLogger;
import microservices.hr.infrastructure.outbox.Outbox;
import microservices.hr.core.command.CommandBus;
import microservices.hr.infrastructure.outbox.OutboxMySQLStorage;

final public class SharedModule extends AbstractModule {
    @Provides
    @Singleton
    static Logger provideLogger() throws IOException {
        return new FileLogger("/Users/pavel/IdeaProjects/microservices-example-1/hr.log");
    }

    @Provides
    @Singleton
    static MySQLClient provideMySQLClient() {
        return new MySQLClient("127.0.0.1", "hr", "user", "password");
    }

    @Provides
    @Singleton
    static Outbox provideOutbox() {
        return new OutboxMySQLStorage();
    }

    @Provides
    @Singleton
    static EventBus provideEventBus(Logger logger) {
        return new EventBus(logger);
    }

    @Provides
    @Singleton
    static CommandBus provideCommandBus() {
        return new CommandBus();
    }
}
