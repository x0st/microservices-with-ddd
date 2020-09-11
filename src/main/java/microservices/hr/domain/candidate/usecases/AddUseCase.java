package microservices.hr.domain.candidate.usecases;

import java.util.UUID;

import microservices.hr.core.command.Command;
import microservices.hr.core.command.UseCase;
import microservices.hr.domain.candidate.Candidate;
import microservices.hr.domain.candidate.CandidateRepository;

final public class AddUseCase implements UseCase<AddUseCase.AddCommand, UUID> {
    private final CandidateRepository candidateRepository;

    public AddUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public UUID exec(AddCommand command) throws Exception {
        Candidate candidate;
        candidate = Candidate.add(command.firstName, command.lastName);

        this.candidateRepository.persist(candidate);

        return candidate.id();
    }

    final public static class AddCommand implements Command<UUID> {
        private final String firstName;
        private final String lastName;

        public AddCommand(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
