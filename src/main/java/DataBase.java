import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBase {
    Properties properties;
    Connection connection;

    private DataBase() {
    }

    public Connection getConnection() {
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            Class.forName(properties.getProperty("driver"));
            Connection connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
        return connection;
    }


}
