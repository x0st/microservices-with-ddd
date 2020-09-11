package microservices.hr.core;

import java.util.UUID;

import microservices.hr.core.AggregateRoot;

public interface Repository<AR extends AggregateRoot, EX extends Throwable> {
    void persist(AR entity);

    AR find(UUID identifier);

    AR get(UUID identifier) throws EX;
}
