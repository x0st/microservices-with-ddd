package microservices.hr.domain.candidate.usecases;

import java.util.UUID;

import microservices.hr.core.command.Command;
import microservices.hr.core.command.UseCase;
import microservices.hr.domain.candidate.Candidate;
import microservices.hr.domain.candidate.CandidateRepository;

final public class InviteUseCase implements UseCase<InviteUseCase.InviteCommand, Void> {
    private final CandidateRepository candidateRepository;

    public InviteUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Void exec(InviteCommand command) throws Exception {
        Candidate candidate;
        candidate = this.candidateRepository.find(command.uuid);
        candidate.invite();

        this.candidateRepository.persist(candidate);

        return null;
    }

    public static class InviteCommand implements Command<Void> {
        private final UUID uuid;

        public InviteCommand(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
