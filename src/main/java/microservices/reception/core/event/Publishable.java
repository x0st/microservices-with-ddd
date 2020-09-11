package microservices.reception.core.event;

public interface Publishable extends Event {
    public String name();

    public String serialize();
}
