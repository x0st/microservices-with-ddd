package microservices.hr.infrastructure.database.mysql;

final class OrWhere implements Where {
    private final String left;
    private final String sign;
    private final Placeholder right;

    OrWhere(String left, String sign, Placeholder right) {
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
