package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.core.event.EventBus;
import microservices.hr.domain.candidate.CandidateRepository;
import microservices.hr.domain.employee.EmployeeRepository;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.outbox.Outbox;
import microservices.hr.infrastructure.storage.CandidateMySQLStorage;
import microservices.hr.infrastructure.storage.EmployeeMySQLStorage;

final public class RepositoryModule extends AbstractModule {
    @Provides
    @Singleton
    static EmployeeRepository provideEmployeeRepository(EventBus eventBus, MySQLClient mySQLClient, Outbox outbox) {
        return new EmployeeMySQLStorage(outbox, mySQLClient, eventBus);
    }

    @Provides
    @Singleton
    static CandidateRepository provideCandidateRepository(EventBus eventBus, MySQLClient mySQLClient, Outbox outbox) {
        return new CandidateMySQLStorage(eventBus, mySQLClient, outbox);
    }
}
