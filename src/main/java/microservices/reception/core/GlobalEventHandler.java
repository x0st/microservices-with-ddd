package microservices.reception.core;

import com.alibaba.fastjson.JSONObject;

public interface GlobalEventHandler {
    public void process(JSONObject object) throws Exception;
}
