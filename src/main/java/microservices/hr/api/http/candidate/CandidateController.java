package microservices.hr.api.http.candidate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.UUID;

import microservices.hr.api.http.JsonResponse;
import microservices.hr.api.http.Request;
import microservices.hr.api.http.Response;
import microservices.hr.core.command.CommandBus;
import microservices.hr.domain.candidate.Candidate;
import microservices.hr.domain.candidate.CandidateRepository;
import microservices.hr.domain.candidate.query.CandidatesQuery;
import microservices.hr.domain.candidate.usecases.AddUseCase;
import microservices.hr.domain.candidate.usecases.HireUseCase;
import microservices.hr.domain.candidate.usecases.InviteUseCase;

import static microservices.hr.core.json.Factory.obj;
import static microservices.hr.core.json.Factory.arr;

final public class CandidateController {
    private final CommandBus commandBus;
    private final CandidatesQuery candidatesQuery;
    private final CandidateRepository candidateRepository;

    public CandidateController(CommandBus commandBus, CandidateRepository candidateRepository, CandidatesQuery candidatesQuery) {
        this.commandBus = commandBus;
        this.candidatesQuery = candidatesQuery;
        this.candidateRepository = candidateRepository;
    }

    public Response add(Request baseRequest) throws Exception {
        AddCandidateRequest request = AddCandidateRequest.fromBase(baseRequest);

        UUID uuid = commandBus.execute(new AddUseCase.AddCommand(
                request.firstName(),
                request.lastName()
        ));

        Candidate candidate = candidateRepository.get(uuid);

        JSONObject response = obj().fluentPut("data", obj()
                .fluentPut("id", candidate.id())
                .fluentPut("firstName", candidate.firstName())
                .fluentPut("lastName", candidate.lastName())
                .fluentPut("invited", candidate.invited())
                .fluentPut("hired", candidate.hired())
        );

        return JsonResponse.created(response);
    }

    public Response invite(Request request) throws Exception {
        commandBus.execute(new InviteUseCase.InviteCommand(
                UUID.fromString(request.pathParam("uuid"))
        ));

        return JsonResponse.ok();
    }

    public Response hire(Request request) throws Exception {
        commandBus.execute(new HireUseCase.HireCommand(
                UUID.fromString(request.pathParam("uuid"))
        ));

        return JsonResponse.ok();
    }

    public Response all(Request request) throws Exception {
        List<microservices.hr.domain.candidate.query.Candidate> records = candidatesQuery.records();

        JSONArray array = arr();
        JSONObject response = obj().fluentPut("data", array);

        records.forEach((candidate) -> array.add(obj()
                .fluentPut("id", candidate.id)
                .fluentPut("firstName", candidate.firstName)
                .fluentPut("lastName", candidate.lastName)
                .fluentPut("invited", candidate.invited)
                .fluentPut("hired", candidate.hired)));

        return JsonResponse.ok(response);
    }
}
