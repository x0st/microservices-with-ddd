package microservices.hr.domain.candidate.usecases;

import java.util.UUID;

import microservices.hr.core.command.Command;
import microservices.hr.core.command.UseCase;
import microservices.hr.domain.candidate.Candidate;
import microservices.hr.domain.candidate.CandidateRepository;

final public class HireUseCase implements UseCase<HireUseCase.HireCommand, Void> {
    private final CandidateRepository candidateRepository;

    public HireUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Void exec(HireCommand command) throws Exception {
        Candidate candidate;
        candidate = this.candidateRepository.get(command.candidateId);
        candidate.hire();

        this.candidateRepository.persist(candidate);

        return null;
    }

    final public static class HireCommand implements Command<Void> {
        private final UUID candidateId;

        public HireCommand(UUID candidateId) {
            this.candidateId = candidateId;
        }
    }
}
