package db;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static  {
        ds.setUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setUsername("user");
        ds.setPassword("passw0rd");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public DBCPDataSource() {
    }
}
