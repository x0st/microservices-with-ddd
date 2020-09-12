package microservices.reception.api.http;

import java.util.HashMap;
import java.util.Map;

final public class EmptyResponse implements Response {
    private final Integer statusCode;
    private final Map<String, String> headers;

    public EmptyResponse(Integer statusCode) {
        this.statusCode = statusCode;
        this.headers = new HashMap<>();
    }

    @Override
    public Integer statusCode() {
        return this.statusCode;
    }

    @Override
    public String body() {
        return "";
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    public static EmptyResponse ok() {
        return new EmptyResponse(200);
    }
}
