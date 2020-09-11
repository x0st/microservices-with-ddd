package microservices.hr.infrastructure.bootstrap;

import com.google.inject.Injector;

import microservices.hr.core.Bootstrap;
import microservices.hr.core.command.CommandBus;
import microservices.hr.domain.candidate.usecases.AddUseCase;
import microservices.hr.domain.candidate.usecases.HireUseCase;
import microservices.hr.domain.candidate.usecases.InviteUseCase;
import microservices.hr.domain.employee.usecases.RegisterUseCase;

final public class CommandBusBootstrap implements Bootstrap {
    private final Injector injector;

    public CommandBusBootstrap(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void bootstrap() {
        CommandBus commandBus;

        commandBus = this.injector.getInstance(CommandBus.class);
        commandBus.map(AddUseCase.AddCommand.class, this.injector.getInstance(AddUseCase.class));
        commandBus.map(HireUseCase.HireCommand.class, this.injector.getInstance(HireUseCase.class));
        commandBus.map(InviteUseCase.InviteCommand.class, this.injector.getInstance(InviteUseCase.class));
        commandBus.map(RegisterUseCase.RegisterCommand.class, this.injector.getInstance(RegisterUseCase.class));
    }
}
