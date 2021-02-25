package ru.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DataBase {
    private static DataBase dataBase;
    private Properties properties;
    private Connection connection;

    private DataBase(String propertiesPath) {
        initProperties(propertiesPath);
        initConnection();
    }

    public static DataBase getDataBase(String propertiesPath) {
        if (dataBase == null) {
            dataBase = new DataBase(propertiesPath);
        }
        return dataBase;
    }

    private void initConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("login"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
    private void initProperties(String prop) {
        try (InputStream in = DataBase.class.getClassLoader().getResourceAsStream(prop)) {
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
