package microservices.reception.infrastructure.database.mysql;

import java.util.UUID;

import microservices.reception.infrastructure.util.Safe;

final public class ResultSet {
    private final java.sql.ResultSet nativeRes;

    ResultSet(java.sql.ResultSet nativeRes) {
        this.nativeRes = nativeRes;
    }

    public UUID getUUID(int index) {
        return Safe.safe(() -> UUID.fromString(this.nativeRes.getString(index)));
    }

    public UUID getUUID(String column) {
        return Safe.safe(() -> UUID.fromString(this.nativeRes.getString(column)));
    }

    public String getString(int index) {
        return Safe.safe(() -> this.nativeRes.getString(index));
    }

    public String getString(String column) {
        return Safe.safe(() -> this.nativeRes.getString(column));
    }

    public Boolean getBoolean(int index) {
        return Safe.safe(() -> this.nativeRes.getBoolean(index));
    }

    public Boolean getBoolean(String column) {
        return Safe.safe(() -> this.nativeRes.getBoolean(column));
    }

    public Integer getInt(int index) {
        return Safe.safe(() -> this.nativeRes.getInt(index));
    }

    public Integer getInt(String column) {
        return Safe.safe(() -> this.nativeRes.getInt(column));
    }

    public boolean first() {
        return Safe.safe(this.nativeRes::first);
    }

    public boolean next() {
        return Safe.safe(this.nativeRes::next);
    }
}
