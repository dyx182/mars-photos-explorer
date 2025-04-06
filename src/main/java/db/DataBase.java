package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private static final String DB_URL = "jdbc:h2:file:./db/mars_photos;DB_CLOSE_DELAY=-1";
    private static final String INIT_SCRIPT = """
            CREATE TABLE IF NOT EXISTS photos (
                id INT PRIMARY KEY,
                sol INT NOT NULL,
                camera VARCHAR(50) NOT NULL,
                img_src VARCHAR(255) NOT NULL,
                rover VARCHAR(20) NOT NULL
            );
            """;

    private DataBase() {
    } // Запрещаем создание экземпляров

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, "sa", "");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(INIT_SCRIPT);
            return connection;
        }
    }
}
