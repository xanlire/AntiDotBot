package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import java.sql.*;

/**
 * Класс предоставляет интерфейс для получения соединения с базой данных на основе
 * файла свойств
 * </p>
 * <h2>Использование</h2>
 * Connection connection = DBConnector.getConnection();
 *
 * @author dubininay
 * @version 1.0
 */
public class DBConnector {

    private Connection connection = null;

    public static Connection getConnection(){
        DBConnector connector = new DBConnector();
        connector.connect();
        return connector.connection;
    }

    private void connect(){
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9003/botDb", "SA", "");

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}

