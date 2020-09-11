package microservices.hr.infrastructure.database.mysql;

final public class UpdateQueryCompiler {
    String compile(UpdateQueryBuilder queryBuilder) {
        StringBuilder SQL = new StringBuilder();

        addUpdate(SQL, queryBuilder);
        addSets(SQL, queryBuilder);
        addWheres(SQL, queryBuilder);

        return SQL.toString();
    }

    private void addWheres(StringBuilder SQL, UpdateQueryBuilder queryBuilder) {
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

    private void addSets(StringBuilder SQL, UpdateQueryBuilder queryBuilder) {
        SQL.append("SET ");

        for (int i = 0; i < queryBuilder.getSets().size(); i++) {
            SQL.append(queryBuilder.getSets().get(i).column());
            SQL.append(" ");
            SQL.append("=");
            SQL.append(" ");
            SQL.append(queryBuilder.getSets().get(i).placeholder().placeholder());

            if (i+1 < queryBuilder.getSets().size()) SQL.append(",");
        }

        SQL.append(" ");
    }

    private void addUpdate(StringBuilder SQL, UpdateQueryBuilder queryBuilder) {
        SQL.append("UPDATE ");
        SQL.append(queryBuilder.getTable());
        SQL.append(" ");
    }
}
