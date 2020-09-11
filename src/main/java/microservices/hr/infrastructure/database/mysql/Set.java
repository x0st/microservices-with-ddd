package microservices.hr.infrastructure.database.mysql;

final public class Set {
    private final String column;
    private final Placeholder<? extends String> placeholder;

    Set(String column, Placeholder<? extends String> placeholder) {
        this.column = column;
        this.placeholder = placeholder;
    }

    String column() {
        return this.column;
    }

    Placeholder<? extends String> placeholder() {
        return this.placeholder;
    }
}
