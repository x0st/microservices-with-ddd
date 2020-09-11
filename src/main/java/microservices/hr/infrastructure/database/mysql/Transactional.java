package microservices.hr.infrastructure.database.mysql;

public interface Transactional {
    void exec(Connection connection);
}
