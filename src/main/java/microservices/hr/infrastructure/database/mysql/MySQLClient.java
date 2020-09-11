package microservices.hr.infrastructure.database.mysql;

import java.sql.DriverManager;

import microservices.hr.infrastructure.Safe;

final public class MySQLClient {
    private final String url;

    public MySQLClient(String host, String database, String username, String password) {
        this.url = String.format("jdbc:mysql://%s:3306/%s?user=%s&password=%s", host, database, username, password);
    }

    public MySQLClient(String host, Integer port, String database, String username, String password) {
        this.url = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", host, port, database, username, password);
    }

    public Connection connection() {
        return Safe.safe(() -> new Connection(DriverManager.getConnection(url)));
    }
}
