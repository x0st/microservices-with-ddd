package microservices.hr.infrastructure.outbox;

import java.util.UUID;

final public class OutboxEvent {
    private UUID id;
    private String name;
    private String data;
    private Boolean processed = false;

    private OutboxEvent(String name, String data) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.data = data;
    }

    public static OutboxEvent create(String name, String data) {
        return new OutboxEvent(name, data);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String data() {
        return data;
    }
}
