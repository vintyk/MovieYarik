package connectionManager;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/movie?autoReconnect=true&useSSL=false&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static DataSource dataSource;

    static {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName("com.mysql.jdbc.Driver");
        poolProperties.setUrl(DB_URL);
        poolProperties.setUsername(USERNAME);
        poolProperties.setPassword(PASSWORD);
        dataSource = new DataSource(poolProperties);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

//    public static final String URL = "jdbc:mysql:3306//localhost:3306/movie";
//    public static final String USERNAME = "root";
//    public static final String PASSWORD = "root";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//    }
}