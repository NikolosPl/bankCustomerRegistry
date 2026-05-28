import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DataBaseConnectionManager {
    private static final HikariDataSource dataSource = initDataSource();
    private static HikariDataSource initDataSource(){
        try(FileInputStream fis = new FileInputStream("config.properties")){
            Properties props = new Properties();
            props.load(fis);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30_000);
            config.setIdleTimeout(600_000);
            config.setPoolName("bankCustomerRegistry");

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            return new HikariDataSource(config);
        } catch (Exception e){
            throw new RuntimeException("Nie udało się zainicjalizować puli połączeń HikariCP: " + e.getMessage(), e);
        }

    }
    public static Connection connect() throws Exception {
        return dataSource.getConnection();
    }
    public static void shutdown(){
        if(!dataSource.isClosed()){
            dataSource.close();
        }
    }
}
