package microservices.reception.infrastructure.database.mysql;

final class AndWhere implements Where {
    private final String left;
    private final String sign;
    private final Placeholder right;

    AndWhere(String left, String sign, Placeholder right) {
        this.left = left;
        this.sign = sign;
        this.right = right;
    }

    public String left() {
        return left;
    }

    public String sign() {
        return sign;
    }

    public Placeholder right() {
        return right;
    }
}
