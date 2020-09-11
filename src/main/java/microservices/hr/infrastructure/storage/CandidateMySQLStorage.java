package microservices.hr.infrastructure.storage;

import java.util.HashSet;
import java.util.UUID;

import microservices.hr.core.event.EventBus;
import microservices.hr.core.event.Publishable;
import microservices.hr.domain.candidate.Candidate;
import microservices.hr.domain.candidate.CandidateRepository;
import microservices.hr.domain.candidate.exception.CandidateNotFound;
import microservices.hr.infrastructure.database.mysql.Connection;
import microservices.hr.infrastructure.database.mysql.MySQLClient;
import microservices.hr.infrastructure.database.mysql.ResultSet;
import microservices.hr.infrastructure.outbox.Outbox;
import microservices.hr.infrastructure.outbox.OutboxEvent;

final public class CandidateMySQLStorage implements CandidateRepository {
    private final Outbox outbox;
    private final EventBus eventBus;
    private final MySQLClient mySQLClient;
    private final HashSet<UUID> records = new HashSet<>(1024);

    public CandidateMySQLStorage(EventBus eventBus, MySQLClient mySQL, Outbox outbox) {
        this.eventBus = eventBus;
        this.mySQLClient = mySQL;
        this.outbox = outbox;
    }

    @Override
    public void persist(Candidate entity) {
        this.mySQLClient.connection().transaction((connection) -> {
            if (this.records.contains(entity.id())) {
                this.update(entity, connection);
            } else {
                this.create(entity, connection);
            }

            entity.domainEvents().forEach(this.eventBus::publish);

            entity.domainEvents().forEach(event -> {
                if (event instanceof Publishable) {
                    this.outbox.save(OutboxEvent.create(
                            ((Publishable) event).name(),
                            ((Publishable) event).serialize()
                    ), connection);
                }
            });

            entity.clearDomainEvents();
        });
    }

    @Override
    public Candidate find(UUID identifier) {
        ResultSet res = this.mySQLClient
                .connection()
                .preparedStatement("SELECT id, firstName, lastName, hired, invited FROM `candidates` WHERE id = ?")
                .setString(1, identifier.toString())
                .withResult();

        if (!res.first()) return null;

        Candidate candidate = new Candidate(
                UUID.fromString(res.getString(1)),
                res.getString(2),
                res.getString(3),
                res.getBoolean(4),
                res.getBoolean(5)

        );

        this.records.add(candidate.id());

        return candidate;
    }

    @Override
    public Candidate get(UUID identifier) throws CandidateNotFound {
        Candidate candidate = this.find(identifier);
        if (null == candidate) throw new CandidateNotFound();
        return candidate;
    }

    private void update(Candidate entity, Connection connection) {
        connection
                .preparedStatement("UPDATE `candidates` SET firstName = ?, lastName = ?, hired = ?, invited = ? WHERE id = ?")
                .setString(1, entity.firstName())
                .setString(2, entity.lastName())
                .setBoolean(3, entity.hired())
                .setBoolean(4, entity.invited())
                .setString(5, entity.id().toString())
                .withoutResult();
    }

    private void create(Candidate entity, Connection connection) {
        connection
                .preparedStatement("INSERT INTO `candidates` (id, firstName, lastName, hired, invited) VALUES (?, ?, ?, ?, ?)")
                .setString(1, entity.id().toString())
                .setString(2, entity.firstName())
                .setString(3, entity.lastName())
                .setBoolean(4, entity.hired())
                .setBoolean(5, entity.invited())
                .withoutResult();
    }
}
