package microservices.reception.infrastructure.database.mysql;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

final public class SelectQueryBuilder {
    private final List<String> select = new LinkedList<>();
    private final List<Where> wheres = new LinkedList<>();
    private Integer limit;
    private Boolean forUpdate = false;
    private String from;

    private final Connection connection;
    private final SelectQueryCompiler queryCompiler;

    SelectQueryBuilder(Connection connection) {
        this.connection = connection;
        this.queryCompiler = new SelectQueryCompiler();
    }

    public SelectQueryBuilder from(String value) {
        this.from = value;
        return this;
    }

    public SelectQueryBuilder select(String ...expressions) {
        this.select.addAll(Arrays.asList(expressions));
        return this;
    }

    public SelectQueryBuilder whereIn(String left, String[] right) {
        this.wheres.add(new AndWhere(left, "in", new ValueArray(right)));
        return this;
    }

    public SelectQueryBuilder where(String left, String sign, String right) {
        this.wheres.add(new AndWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public SelectQueryBuilder where(String left, String sign, Integer right) {
        this.wheres.add(new AndWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public SelectQueryBuilder orWhere(String left, String sign, String right) {
        if (this.wheres.isEmpty()) throw new RuntimeException();

        this.wheres.add(new OrWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public SelectQueryBuilder limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public SelectQueryBuilder forUpdate() {
        this.forUpdate = true;
        return this;
    }

    public PreparedStatement toPreparedStatement() {
        Where whereClause;
        String SQL = this.toSQL();
        PreparedStatement preparedStatement = this.connection.preparedStatement(SQL);

        for (int i = 0; i < this.wheres.size(); i++) {
            whereClause = this.wheres.get(i);

            if (whereClause.right() instanceof SingleValue) {
                preparedStatement.setString(i+1, (String)(whereClause.right()).expand());
            } else if (whereClause.right() instanceof ValueArray) {
                String[] values = ((ValueArray) whereClause.right()).expand();
                for (String value : values) preparedStatement.setString(i+1, value);
            }
        }

        return preparedStatement;
    }

    public String toSQL() {
        if (null == this.from) throw new RuntimeException();
        if (this.select.isEmpty()) throw new RuntimeException();

        return this.queryCompiler.compile(this);
    }

    Integer getLimit() { return limit; }

    Boolean getForUpdate() { return forUpdate; }

    String getFrom() {
        return from;
    }

    List<String> getSelect() {
        return select;
    }

    List<Where> getWheres() {
        return wheres;
    }
}
