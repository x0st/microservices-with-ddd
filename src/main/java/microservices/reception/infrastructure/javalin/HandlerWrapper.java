package microservices.reception.infrastructure.javalin;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import microservices.reception.api.http.Request;
import microservices.reception.api.http.Response;

final public class HandlerWrapper implements Handler {
    private final H handler;

    public HandlerWrapper(H handler) {
        this.handler = handler;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        Request request;
        Response response;

        request = new Request(
                ctx.body(),
                ctx.headerMap(),
                ctx.queryParamMap(),
                ctx.pathParamMap()
        );

        response = this.handler.handle(request);

        ctx.result(response.body());
        ctx.status(response.statusCode());

        response.headers().forEach(ctx::header);
    }

    public interface H {
        Response handle(Request request) throws Exception;
    }
}
