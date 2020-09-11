package microservices.reception.infrastructure.database.mysql;

final class SelectQueryCompiler {
    String compile(SelectQueryBuilder queryBuilder) {
        StringBuilder SQL = new StringBuilder();

        addSelect(SQL, queryBuilder);
        addFrom(SQL, queryBuilder);
        addWheres(SQL, queryBuilder);
        addForUpdate(SQL, queryBuilder);
        addLimit(SQL, queryBuilder);

        return SQL.toString();
    }

    private void addLimit(StringBuilder SQL, SelectQueryBuilder queryBuilder) {
        if (null == queryBuilder.getLimit()) return;

        SQL.append("LIMIT ");
        SQL.append(queryBuilder.getLimit());
    }

    private void addForUpdate(StringBuilder SQL, SelectQueryBuilder queryBuilder) {
        if (!queryBuilder.getForUpdate()) return;

        SQL.append("FOR UPDATE ");
    }

    private void addWheres(StringBuilder SQL, SelectQueryBuilder queryBuilder) {
        for (int i = 0; i < queryBuilder.getWheres().size(); i++) {
            if (0 == i) {
                SQL.append("WHERE ");
            } else {
                if (queryBuilder.getWheres().get(i) instanceof AndWhere) {
                    SQL.append("AND ");
                } else if (queryBuilder.getWheres().get(i) instanceof OrWhere) {
                    SQL.append("OR ");
                }
            }

            SQL.append(queryBuilder.getWheres().get(i).left());
            SQL.append(" ");
            SQL.append(queryBuilder.getWheres().get(i).sign());
            SQL.append(" ");
            SQL.append(queryBuilder.getWheres().get(i).right().placeholder());

            SQL.append(" ");
        }
    }

    private void addSelect(StringBuilder SQL, SelectQueryBuilder queryBuilder) {
        String[] expressions;

        expressions = new String[queryBuilder.getSelect().size()];
        for (int i = 0; i < queryBuilder.getSelect().size(); i++) {
            expressions[i] = queryBuilder.getSelect().get(i);
        }

        SQL.append("SELECT ");
        SQL.append(String.join(", ", expressions));
    }

    private void addFrom(StringBuilder SQL, SelectQueryBuilder queryBuilder) {
        SQL.append("FROM ");
        SQL.append(queryBuilder.getFrom());
        SQL.append(" ");
    }
}
