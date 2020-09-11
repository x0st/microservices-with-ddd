package microservices.reception.core;

import java.util.UUID;

public interface Repository<AR extends AggregateRoot, EX extends Throwable> {
    void persist(AR entity);

    AR find(UUID identifier);

    AR get(UUID identifier) throws EX;
}
