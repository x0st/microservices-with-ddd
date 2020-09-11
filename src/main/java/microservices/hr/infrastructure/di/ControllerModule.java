package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.api.http.candidate.CandidateController;
import microservices.hr.core.command.CommandBus;
import microservices.hr.domain.candidate.CandidateRepository;
import microservices.hr.domain.candidate.query.CandidatesQuery;

final public class ControllerModule extends AbstractModule {
    @Provides
    @Singleton
    static CandidateController provideCandidateController(
            CommandBus commandBus,
            CandidateRepository candidateRepository,
            CandidatesQuery candidatesQuery
    ) {
        return new CandidateController(commandBus, candidateRepository, candidatesQuery);
    }


}
