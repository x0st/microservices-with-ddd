package microservices.hr.api.http;

import java.util.List;
import java.util.Map;

final public class Request {
    private final String body;
    private final Map<String, String> headers;
    private final Map<String, List<String>> queryParams;
    private final Map<String, String> pathParams;

    public Request(
            String body,
            Map<String, String> headers,
            Map<String, List<String>> queryParams,
            Map<String, String> pathParams
    ) {
        this.body = body;
        this.headers = headers;
        this.queryParams = queryParams;
        this.pathParams = pathParams;
    }

    public String body() {
        return body;
    }

    public String pathParam(String name) {
        return pathParams.get(name);
    }

    public String queryParam(String name) {
        return "";
    }
}
