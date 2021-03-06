package microservices.reception.core.command;

public interface UseCase<T extends Command<?>, R> {
    public R exec(T command) throws Exception;
}
