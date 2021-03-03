package ru.db;

import ru.PropertiesCreator;

import java.sql.*;
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
            e.fillInStackTrace();
        }
    }
    private void initProperties(String prop) {
       properties = PropertiesCreator.getProperties(prop);
    }

    public Connection getConnection() {
        return connection;
    }

}
