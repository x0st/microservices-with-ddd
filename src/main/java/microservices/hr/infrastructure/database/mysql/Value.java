package microservices.hr.infrastructure.database.mysql;

final class Value implements Placeholder {
    private final String value;

    Value(String value) {
        this.value = value;
    }

    @Override
    public String expand() {
        return value;
    }
}
