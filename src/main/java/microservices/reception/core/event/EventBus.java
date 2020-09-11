package microservices.reception.core.event;

import java.util.HashMap;
import java.util.LinkedList;

final public class EventBus {
    private final HashMap<Class, LinkedList<EventListener>> map = new HashMap<>(1024);

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
