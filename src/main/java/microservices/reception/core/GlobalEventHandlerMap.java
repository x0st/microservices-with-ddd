package microservices.reception.core;

import java.util.HashMap;

final public class GlobalEventHandlerMap {
    private final HashMap<String, GlobalEventHandler> map = new HashMap<>();

    public void map(String name, GlobalEventHandler eventHandler) {
        map.put(name, eventHandler);
    }

    public GlobalEventHandler get(String name) {
        return map.get(name);
    }
}
