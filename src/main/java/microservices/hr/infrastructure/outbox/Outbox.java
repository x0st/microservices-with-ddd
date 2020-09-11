package microservices.hr.infrastructure.outbox;

import microservices.hr.infrastructure.database.mysql.Connection;

public interface Outbox {
    void save(OutboxEvent event, Connection connection);
}
