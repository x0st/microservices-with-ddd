package microservices.reception.core;

import java.util.HashMap;
import java.util.Map;

final public class QueryFactory {
    private final Map<Class, Object> map = new HashMap<>();

    public void populate(Object query) {
        this.map.put(query.getClass(), query);
    }

    public <T> T make(Class<T> query) {
        return (T) this.map.get(query);
    }
}
