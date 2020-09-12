package microservices.reception.infrastructure;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.javalin.Javalin;
import microservices.reception.api.http.employee.EmployeeController;
import microservices.reception.core.DomainException;
import microservices.reception.infrastructure.di.ControllerModule;
import microservices.reception.infrastructure.di.QueryModule;
import microservices.reception.infrastructure.di.RepositoryModule;
import microservices.reception.infrastructure.di.SharedModule;
import microservices.reception.infrastructure.javalin.HandlerWrapper;

final public class WebApplication {
    private final Javalin app;
    private final Injector injector;

    public static void main(String[] args) {
        new WebApplication().start();
    }

    WebApplication() {
        app = Javalin.create();
        injector = Guice.createInjector(
                new SharedModule(),
                new RepositoryModule(),
                new QueryModule(),
                new ControllerModule()
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

    }

    private void defineRoutes() {
        this.app.get("/api/v1/employees", new HandlerWrapper(this.injector.getInstance(EmployeeController.class)::list));
    }

    private void start() {
        app.start(80);
    }
}
