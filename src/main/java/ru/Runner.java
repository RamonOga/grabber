package ru;

import ru.db.DataBase;
import ru.db.PropertiesCreator;
import ru.db.SQLTracker;
import ru.parse.SqlRuParse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        SQLTracker sqlt = new SQLTracker("app.properties");
        SqlRuParse sqlRuParse = new SqlRuParse();
        PropertiesCreator propertiesCreator = new PropertiesCreator();
        Properties properties = propertiesCreator
                .getProperties("parse.properties");
        Set<Post> postSet = sqlRuParse.urlParse(properties.getProperty("url"),
                properties.getProperty("href"),
                properties.getProperty("date"));
        sqlt.addAll(postSet);
       // sqlt.findAll().forEach(System.out::println);
    }


}