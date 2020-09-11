package microservices.hr.infrastructure.database.mysql;

import java.sql.SQLException;

import microservices.hr.infrastructure.Safe;

final public class Connection {
    private final java.sql.Connection nativeConn;

    Connection(java.sql.Connection nativeConn) throws SQLException {
        this.nativeConn = nativeConn;
        this.nativeConn.setAutoCommit(false);
    }

    public java.sql.Connection nativeConn() {
        return this.nativeConn;
    }

    public PreparedStatement preparedStatement(String sql) {
        return Safe.safe(() -> new PreparedStatement(nativeConn().prepareStatement(sql)));
    }

    public void transaction(Transactional closure) {
        this.transaction();

        try {
            closure.exec(this);
            this.commit();
        } catch (Exception e) {
            this.rollback();

            throw new RuntimeException(e);
        }

        this.close();
    }

    public void close() {
        Safe.safeVoid(() -> nativeConn().createStatement().close());
    }

    public void transaction() {
        Safe.safeVoid(() -> nativeConn().createStatement().execute("START TRANSACTION"));
    }

    public void rollback() {
        Safe.safeVoid(() -> nativeConn().createStatement().execute("ROLLBACK"));
    }

    public void commit() {
        Safe.safeVoid(() -> nativeConn().createStatement().execute("COMMIT"));
    }
}
