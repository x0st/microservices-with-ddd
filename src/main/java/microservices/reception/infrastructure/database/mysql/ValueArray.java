package microservices.reception.infrastructure.database.mysql;

final class ValueArray implements Placeholder<String[]> {
    private final String[] values;

    ValueArray(String[] values) {
        this.values = values;
    }

    @Override
    public String[] expand() {
        return this.values;
    }

    @Override
    public String placeholder() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("(");

        for (int i = 0; i < this.values.length; i++) {
            sb.append("?");

            if (i+1 < this.values.length) sb.append(",");
        }

        sb.append(")");


        return sb.toString();
    }
}
