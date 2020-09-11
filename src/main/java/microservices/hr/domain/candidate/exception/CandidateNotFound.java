package microservices.hr.domain.candidate.exception;

import microservices.hr.core.DomainException;

final public class CandidateNotFound extends DomainException {
    public CandidateNotFound() {
        super("Candidate not found.");
    }
}
