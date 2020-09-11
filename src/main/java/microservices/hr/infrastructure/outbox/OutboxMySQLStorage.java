package microservices.hr.infrastructure.outbox;

import microservices.hr.infrastructure.database.mysql.Connection;

final public class OutboxMySQLStorage implements Outbox {
    public void save(OutboxEvent event, Connection connection) {
        connection
                .preparedStatement("INSERT INTO `outbox` (id, name, data, processed) VALUES (?, ?, ?, ?)")
                .setString(1, event.id().toString())
                .setString(2, event.name())
                .setString(3, event.data())
                .setBoolean(4, false)
                .withoutResult();
    }
}
