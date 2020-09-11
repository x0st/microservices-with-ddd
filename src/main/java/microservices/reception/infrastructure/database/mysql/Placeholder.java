package microservices.reception.infrastructure.database.mysql;

interface Placeholder<T> {
    T expand();

    String placeholder();
}
