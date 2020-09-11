package microservices.reception.infrastructure.database.mysql;

public interface Transactional {
    void exec(Connection connection);
}
