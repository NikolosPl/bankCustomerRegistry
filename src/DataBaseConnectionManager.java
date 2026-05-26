import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBaseConnectionManager {
    public Connection connect() throws Exception {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            Properties props = new Properties();
            props.load(fis);
            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw e;
        }
    }
}
