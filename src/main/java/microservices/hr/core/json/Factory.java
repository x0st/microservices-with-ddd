package microservices.hr.core.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

final public class Factory {
    public static JSONObject obj() {
        return new JSONObject();
    }

    public static JSONArray arr() {
        return new JSONArray();
    }
}
