package microservices.hr.domain.candidate.event;

import microservices.hr.core.event.Deferred;
import microservices.hr.core.event.Event;
import microservices.hr.domain.candidate.Candidate;

final public class CandidateHiredEvent implements Event, Deferred {
    private final Candidate candidate;

    public CandidateHiredEvent(Candidate candidate) {
        this.candidate = candidate;
    }

    public Candidate candidate() {
        return candidate;
    }
}
