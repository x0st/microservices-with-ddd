package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.core.command.CommandBus;
import microservices.hr.domain.candidate.listener.CandidateHiredEventHandler;

final public class EventModule extends AbstractModule {
    @Provides
    @Singleton
    static CandidateHiredEventHandler provideCandidateHiredEventHandler(CommandBus commandBus) {
        return new CandidateHiredEventHandler(commandBus);
    }
}
