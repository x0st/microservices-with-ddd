package microservices.reception.core;

abstract public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }

    public DomainException() {
        super();
    }
}
