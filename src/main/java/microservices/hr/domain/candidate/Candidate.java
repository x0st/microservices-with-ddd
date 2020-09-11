package microservices.hr.domain.candidate;

import java.util.UUID;

import microservices.hr.core.AggregateRoot;
import microservices.hr.domain.candidate.event.CandidateHiredEvent;
import microservices.hr.domain.candidate.exception.AlreadyHired;
import microservices.hr.domain.candidate.exception.AlreadyInvited;

final public class Candidate extends AggregateRoot {
    private UUID id;
    private String firstName;
    private String lastName;
    private Boolean hired;
    private Boolean invited;

    public Candidate(UUID id, String firstName, String lastName, Boolean hired, Boolean invited) {
        this.id = id;
        this.hired = hired;
        this.invited = invited;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public void hire() throws AlreadyHired {
        if (this.hired) throw new AlreadyHired();

        this.hired = true;

        this.registerEvent(new CandidateHiredEvent(this));
    }

    public void invite() throws AlreadyInvited {
        if (this.invited) throw new AlreadyInvited();

        this.invited = true;
    }

    @Override
    public UUID id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Boolean hired() {
        return hired;
    }

    public Boolean invited() { return invited; }

    public static Candidate add(String firstName, String lastName) {
        return new Candidate(
                UUID.randomUUID(),
                firstName,
                lastName,
                false,
                false
        );
    }
}
