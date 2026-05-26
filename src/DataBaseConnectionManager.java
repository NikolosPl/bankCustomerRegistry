import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnectionManager {
    private Connection conn;
    public Connection connect() throws Exception {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            this.conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );
            return this.conn;
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw e;
        }
    }
    public void close() throws SQLException {
        this.conn.close();
    }
}
