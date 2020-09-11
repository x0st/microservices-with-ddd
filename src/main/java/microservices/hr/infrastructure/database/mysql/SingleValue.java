package microservices.hr.infrastructure.database.mysql;

final class SingleValue implements Placeholder<String> {
    private final String value;

    SingleValue(String value) {
        this.value = value;
    }

    SingleValue(Integer value) {
        this.value = String.valueOf(value);
    }

    @Override
    public String expand() {
        return value;
    }

    @Override
    public String placeholder() {
        return "?";
    }
}
