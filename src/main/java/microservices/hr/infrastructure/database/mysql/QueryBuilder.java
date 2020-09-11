package microservices.hr.infrastructure.database.mysql;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

final public class QueryBuilder {
    private final List<String> select = new LinkedList<>();
    private final List<Where> wheres = new LinkedList<>();
    private String from;

    private final Connection connection;
    private final QueryCompiler queryCompiler;

    public QueryBuilder(Connection connection) {
        this.connection = connection;
        this.queryCompiler = new QueryCompiler();
    }

    public QueryBuilder from(String value) {
        this.from = value;
        return this;
    }

    public QueryBuilder select(String ...expressions) {
        this.select.addAll(Arrays.asList(expressions));
        return this;
    }

    public QueryBuilder where(String left, String sign, String right) {
        this.wheres.add(new AndWhere(left, sign, new Value(right)));
        return this;
    }

    public QueryBuilder orWhere(String left, String sign, String right) {
        if (this.wheres.isEmpty()) throw new RuntimeException();

        this.wheres.add(new OrWhere(left, sign, new Value(right)));
        return this;
    }

    String from() {
        return from;
    }

    List<String> select() {
        return select;
    }

    List<Where> wheres() {
        return wheres;
    }

    public PreparedStatement toPreparedStatement() {
        String SQL = this.toSQL();
        PreparedStatement preparedStatement = this.connection.preparedStatement(SQL);

        for (int i = 0; i < this.wheres.size(); i++) {
            if (this.wheres.get(i).right() instanceof Value) {
                preparedStatement.setString(i+1, this.wheres.get(i).right().expand());
            }
        }

        return preparedStatement;
    }

    public String toSQL() {
        if (null == this.from) throw new RuntimeException();
        if (this.select.isEmpty()) throw new RuntimeException();

        return this.queryCompiler.compile(this);
    }
}
