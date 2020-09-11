package microservices.hr.api.http;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

final public class JsonResponse implements Response {
    private final Integer statusCode;
    private final JSONObject body;
    private final Map<String, String> headers;

    public JsonResponse(Integer statusCode, JSONObject body) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
    }

    @Override
    public Integer statusCode() {
        return this.statusCode;
    }

    @Override
    public String body() {
        if (null == this.body) return "";
        return this.body.toJSONString();
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    public static JsonResponse ok() {
        return new JsonResponse(200, null);
    }

    public static JsonResponse ok(JSONObject body) {
        return new JsonResponse(200, body);
    }

    public static JsonResponse created(JSONObject body) {
        return new JsonResponse(201, body);
    }
}
