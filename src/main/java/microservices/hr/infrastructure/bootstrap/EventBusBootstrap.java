package microservices.hr.infrastructure.bootstrap;

import com.google.inject.Injector;

import microservices.hr.core.Bootstrap;
import microservices.hr.core.event.EventBus;
import microservices.hr.domain.candidate.event.CandidateHiredEvent;
import microservices.hr.domain.candidate.listener.CandidateHiredEventHandler;

final public class EventBusBootstrap implements Bootstrap {
    private final Injector injector;

    public EventBusBootstrap(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void bootstrap() {
        EventBus eventBus;

        eventBus = this.injector.getInstance(EventBus.class);
        eventBus.subscribe(CandidateHiredEvent.class, this.injector.getInstance(CandidateHiredEventHandler.class));
    }
}
