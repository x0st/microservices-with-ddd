package microservices.reception.infrastructure.database.mysql;

interface Where {
    String left();
    String sign();
    Placeholder right();
}
