package microservices.reception.api.http;

import java.util.Map;

public interface Response {
    public Integer statusCode();
    public String body();
    public Map<String, String> headers();
}
