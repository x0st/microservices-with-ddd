package microservices.hr.core;

import java.util.ArrayList;

import microservices.hr.core.event.Event;

abstract public class AggregateRoot implements Entity {
    private final ArrayList<Event> events = new ArrayList<Event>(5);

    protected void registerEvent(Event event) {
        events.add(event);
    }

    public ArrayList<Event> domainEvents() {
        return events;
    }

    public void clearDomainEvents() {
        events.clear();
    }
}
