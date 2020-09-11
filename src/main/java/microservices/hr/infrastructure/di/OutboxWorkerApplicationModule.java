package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.infrastructure.database.mysql.MySQLClient;

final public class OutboxWorkerApplicationModule extends AbstractModule {
    @Provides
    @Singleton
    static MySQLClient provideMySQLClient() {
        return new MySQLClient("127.0.0.1", "hr", "user", "password");
    }
}
