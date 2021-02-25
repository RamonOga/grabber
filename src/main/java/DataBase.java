import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBase implements AutoCloseable {
    Properties properties;
    Connection connection;

    private DataBase() {
        initConnection();
    }

    public void initConnection() {
        try {
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
    }
    public void initProperties(String prop) {
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(prop)) {
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
