package microservices.hr.infrastructure.database.mysql;

import java.util.LinkedList;
import java.util.List;

final public class UpdateQueryBuilder {
    private String table;
    private final List<Where> wheres = new LinkedList<>();
    private final List<Set> sets = new LinkedList<>();

    private final Connection connection;
    private final UpdateQueryCompiler queryCompiler;

    UpdateQueryBuilder(Connection connection) {
        this.connection = connection;
        this.queryCompiler = new UpdateQueryCompiler();
    }

    public UpdateQueryBuilder update(String table) {
        this.table = table;
        return this;
    }

    public UpdateQueryBuilder whereIn(String left, String[] right) {
        this.wheres.add(new AndWhere(left, "in", new ValueArray(right)));
        return this;
    }

    public UpdateQueryBuilder where(String left, String sign, String right) {
        this.wheres.add(new AndWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public UpdateQueryBuilder where(String left, String sign, Integer right) {
        this.wheres.add(new AndWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public UpdateQueryBuilder orWhere(String left, String sign, String right) {
        if (this.wheres.isEmpty()) throw new RuntimeException();

        this.wheres.add(new OrWhere(left, sign, new SingleValue(right)));
        return this;
    }

    public UpdateQueryBuilder set(String column, String value) {
        this.sets.add(new Set(column, new SingleValue(value)));
        return this;
    }

    public UpdateQueryBuilder set(String column, Integer value) {
        this.sets.add(new Set(column, new SingleValue(value)));
        return this;
    }

    public PreparedStatement toPreparedStatement() {
        int placeholderCounter = 1;
        String SQL = this.toSQL();
        PreparedStatement preparedStatement = this.connection.preparedStatement(SQL);

        for (Set set : this.sets) {
            preparedStatement.setString(placeholderCounter++, set.placeholder().expand());
        }

        for (Where where : this.wheres) {
            if (where.right() instanceof SingleValue) {
                preparedStatement.setString(placeholderCounter++, (String) (where.right()).expand());
            } else if (where.right() instanceof ValueArray) {
                String[] values = ((ValueArray) where.right()).expand();
                for (String value : values)
                    preparedStatement.setString(placeholderCounter++, value);
            }
        }

        return preparedStatement;
    }

    public String toSQL() {
        if (null == this.table) throw new RuntimeException();
        if (this.sets.isEmpty()) throw new RuntimeException();

        return this.queryCompiler.compile(this);
    }

    String getTable() {
        return table;
    }

    List<Set> getSets() {
        return sets;
    }

    List<Where> getWheres() {
        return wheres;
    }
}
