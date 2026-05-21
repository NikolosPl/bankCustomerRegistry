import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DataBaseConnectionManager {
    private Connection conn;
    public void connect() throws Exception {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            this.conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );
            ResultSet s = this.conn.prepareStatement("SELECT * FROM customers;").executeQuery(); // Test the connection
            while(s.next()){
                System.out.println(s.getString("first_name") + " " + s.getString("last_name"));
            }
        } catch (Exception e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw e;
        }
    }
    public void close() throws SQLException {
        this.conn.close();
    }
}
