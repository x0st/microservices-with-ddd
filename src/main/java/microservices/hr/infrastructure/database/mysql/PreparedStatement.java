package microservices.hr.infrastructure.database.mysql;

import java.sql.Types;
import java.util.UUID;

import microservices.hr.infrastructure.Safe;

final public class PreparedStatement {
    private final java.sql.PreparedStatement nativeSt;

    PreparedStatement(java.sql.PreparedStatement nativeSt) {
        this.nativeSt = nativeSt;
    }

    public PreparedStatement setUUID(int index, UUID value) {
        Safe.safeVoid(() -> nativeSt.setString(index, value.toString()));
        return this;
    }

    public PreparedStatement setString(int index, String value) {
        if (null == value) Safe.safeVoid(() -> this.nativeSt.setNull(index, Types.VARCHAR));
        else Safe.safeVoid(() -> nativeSt.setString(index, value));
        return this;
    }

    public PreparedStatement setBoolean(int index, Boolean value) {
        if (null == value) Safe.safeVoid(() -> this.nativeSt.setNull(index, Types.BOOLEAN));
        else Safe.safeVoid(() -> this.nativeSt.setBoolean(index, value));
        return this;
    }

    public PreparedStatement setInt(int index, Integer value) {
        if (null == value) Safe.safeVoid(() -> this.nativeSt.setNull(index, Types.INTEGER));
        else Safe.safeVoid(() -> this.nativeSt.setInt(index, value));
        return this;
    }

    public ResultSet withResult() {
        return new ResultSet(Safe.safe(this.nativeSt::executeQuery));
    }

    public void withoutResult() {
        Safe.safe(this.nativeSt::execute);
    }
}
