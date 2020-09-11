package microservices.hr.core.event;

import java.util.HashMap;
import java.util.LinkedList;

import microservices.hr.core.Logger;

final public class EventBus {
    private final HashMap<Class, LinkedList<EventListener>> map = new HashMap<>(1024);
    private final Logger logger;

    public EventBus(Logger logger) {
        this.logger = logger;
    }

    public final <T extends Event> void publish(T event) {
        if (!map.containsKey(event.getClass())) return;

        map.get(event.getClass()).forEach(eventEventListener -> {
            try {
                eventEventListener.handle(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public final void subscribe(Class event, EventListener listener) {
        if (!map.containsKey(event)) {
            map.put(event, new LinkedList<>());
        }

        map.get(event).add(listener);
    }
}
