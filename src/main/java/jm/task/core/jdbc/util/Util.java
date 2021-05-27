package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/dbjm113";
    private static final String USERNAME = "roots";
    private static final String PASSWORD = "root";
    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("useUnicode", "true");
        properties.setProperty("characterEncoding", "utf8");
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, properties);
        }
        if (!connection.isClosed()) {
        }
        return connection;
    }
}
