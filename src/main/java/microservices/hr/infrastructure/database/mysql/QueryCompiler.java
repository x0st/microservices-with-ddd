package microservices.hr.infrastructure.database.mysql;

final class QueryCompiler {
    String compile(QueryBuilder queryBuilder) {
        StringBuilder SQL = new StringBuilder();

        addSelect(SQL, queryBuilder);
        addFrom(SQL, queryBuilder);
        addWheres(SQL, queryBuilder);

        return SQL.toString();
    }

    private void addWheres(StringBuilder SQL, QueryBuilder queryBuilder) {
        for (int i = 0; i < queryBuilder.wheres().size(); i++) {
            if (0 == i) {
                SQL.append("WHERE ");
            } else {
                if (queryBuilder.wheres().get(i) instanceof AndWhere) {
                    SQL.append("AND ");
                } else if (queryBuilder.wheres().get(i) instanceof OrWhere) {
                    SQL.append("OR ");
                }
            }

            if (queryBuilder.wheres().get(i).right() instanceof Value) {
                SQL.append(queryBuilder.wheres().get(i).left());
                SQL.append(" ");
                SQL.append(queryBuilder.wheres().get(i).sign());
                SQL.append(" ");
                SQL.append("?");
            } else {
                SQL.append(queryBuilder.wheres().get(i).left());
                SQL.append(" ");
                SQL.append(queryBuilder.wheres().get(i).sign());
                SQL.append(" ");
                SQL.append(queryBuilder.wheres().get(i).right().expand());
            }

            SQL.append(" ");
        }
    }

    private void addSelect(StringBuilder SQL, QueryBuilder queryBuilder) {
        String[] expressions;

        expressions = new String[queryBuilder.select().size()];
        for (int i = 0; i < queryBuilder.select().size(); i++) {
            expressions[i] = queryBuilder.select().get(i);
        }

        SQL.append("SELECT ");
        SQL.append(String.join(", ", expressions));
    }

    private void addFrom(StringBuilder SQL, QueryBuilder queryBuilder) {
        SQL.append("FROM ");
        SQL.append(queryBuilder.from());
        SQL.append(" ");
    }
}
