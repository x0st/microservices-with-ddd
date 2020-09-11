package microservices.hr.api.http.candidate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import microservices.hr.api.http.Request;

final public class AddCandidateRequest {
    private final String firstName;
    private final String lastName;

    private AddCandidateRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public static AddCandidateRequest fromBase(Request request) {
        JSONObject json;
        json = JSON.parseObject(request.body());

        return new AddCandidateRequest(
                json.getString("firstName"),
                json.getString("lastName")
        );
    }
}
