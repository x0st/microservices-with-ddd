package microservices.hr.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import microservices.hr.domain.candidate.CandidateRepository;
import microservices.hr.domain.candidate.usecases.AddUseCase;
import microservices.hr.domain.candidate.usecases.HireUseCase;
import microservices.hr.domain.candidate.usecases.InviteUseCase;
import microservices.hr.domain.employee.EmployeeRepository;
import microservices.hr.domain.employee.usecases.RegisterUseCase;

final public class UseCaseModule extends AbstractModule {
    @Provides
    @Singleton
    static InviteUseCase provideInviteUseCase(CandidateRepository candidateRepository) {
        return new InviteUseCase(candidateRepository);
    }

    @Provides
    @Singleton
    static HireUseCase provideHireUseCase(CandidateRepository candidateRepository) {
        return new HireUseCase(candidateRepository);
    }

    @Provides
    @Singleton
    static AddUseCase provideAddUseCase(CandidateRepository candidateRepository) {
        return new AddUseCase(candidateRepository);
    }

    @Provides
    @Singleton
    static RegisterUseCase provideRegisterUseCase(EmployeeRepository employeeRepository) {
        return new RegisterUseCase(employeeRepository);
    }
}
