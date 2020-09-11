package microservices.hr.domain.candidate.exception;

import microservices.hr.core.DomainException;

final public class AlreadyHired extends DomainException {
    public AlreadyHired() {
        super("The candidate is already hired.");
    }
}
