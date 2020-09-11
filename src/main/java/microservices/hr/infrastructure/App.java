package microservices.hr.infrastructure;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.javalin.Javalin;
import microservices.hr.api.http.candidate.CandidateController;
import microservices.hr.core.DomainException;
import microservices.hr.infrastructure.bootstrap.CommandBusBootstrap;
import microservices.hr.infrastructure.bootstrap.EventBusBootstrap;
import microservices.hr.infrastructure.di.ControllerModule;
import microservices.hr.infrastructure.di.EventModule;
import microservices.hr.infrastructure.di.QueryModule;
import microservices.hr.infrastructure.di.RepositoryModule;
import microservices.hr.infrastructure.di.SharedModule;
import microservices.hr.infrastructure.di.UseCaseModule;
import microservices.hr.infrastructure.javalin.HandlerWrapper;

final public class App {
    private final Javalin app;
    private final Injector injector;

    public static void main(String[] args) {
        new App().start();
    }

    App() {
        app = Javalin.create();
        injector = Guice.createInjector(
                new SharedModule(),
                new RepositoryModule(),
                new UseCaseModule(),
                new EventModule(),
                new ControllerModule(),
                new QueryModule()
        );

        bootstrap();
        exceptions();
        defineRoutes();
    }

    private void exceptions() {
        app.exception(DomainException.class, (e, context) -> {
            context.status(412);
            context.header("Content-Type", "application/json");
            context.result(new JSONObject().fluentPut("message", e.getMessage()).toJSONString());
        });

        app.exception(Exception.class, (e, context) -> {
            context.status(500);
            context.header("Content-Type", "application/json");
            context.result(new JSONObject().fluentPut("message", "Internal Server Error").toJSONString());
        });
    }

    private void bootstrap() {
        new CommandBusBootstrap(injector).bootstrap();
        new EventBusBootstrap(injector).bootstrap();
    }

    private void defineRoutes() {
        this.app.get("/api/v1/candidates", new HandlerWrapper(injector.getInstance(CandidateController.class)::all));
        this.app.post("/api/v1/candidate", new HandlerWrapper(injector.getInstance(CandidateController.class)::add));
        this.app.post("/api/v1/candidate/:uuid/hire", new HandlerWrapper(injector.getInstance(CandidateController.class)::hire));
        this.app.post("/api/v1/candidate/:uuid/invite", new HandlerWrapper(injector.getInstance(CandidateController.class)::invite));
    }

    private void start() {
        app.start(80);
    }
}
