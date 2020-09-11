package microservices.hr.domain.candidate.exception;

import microservices.hr.core.DomainException;

final public class AlreadyInvited extends DomainException {
    public AlreadyInvited() {
        super("The candidate has already been invited.");
    }
}
