package microservices.hr.core.command;

import java.util.HashMap;

final public class CommandBus {
    private final HashMap<Class, UseCase> map = new HashMap<>(64);

    public <T> T execute(Command<T> command) throws Exception {
        UseCase<Command<?>, ?> executor;

        executor = this.get(command.getClass());
        return (T) executor.exec(command);
    }

    public void map(Class commandClass, UseCase useCase) {
        this.map.put(commandClass, useCase);
    }

    private UseCase<Command<?>, ?> get(Class commandClass) {
        if (this.map.containsKey(commandClass)) {
            return this.map.get(commandClass);
        }

        throw new RuntimeException();
    }
}
