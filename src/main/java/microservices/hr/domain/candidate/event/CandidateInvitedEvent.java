package microservices.hr.domain.candidate.event;

import microservices.hr.core.event.Event;
import microservices.hr.domain.candidate.Candidate;

final public class CandidateInvitedEvent implements Event {
    private final Candidate candidate;

    public CandidateInvitedEvent(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate candidate() {
        return this.candidate;
    }
}
