package microservices.hr.domain.candidate.listener;

import microservices.hr.core.event.EventListener;
import microservices.hr.core.command.CommandBus;
import microservices.hr.domain.candidate.event.CandidateHiredEvent;
import microservices.hr.domain.employee.usecases.RegisterUseCase;

final public class CandidateHiredEventHandler implements EventListener<CandidateHiredEvent> {
    private final CommandBus commandBus;

    public CandidateHiredEventHandler(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public void handle(CandidateHiredEvent event) throws Exception {
        this.commandBus.execute(new RegisterUseCase.RegisterCommand(
                event.candidate().firstName(),
                event.candidate().lastName()
        ));
    }
}
