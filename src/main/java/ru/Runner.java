package ru;

import ru.db.DataBase;
import ru.db.SQLTracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.ResourceBundle;

public class Runner {
    public static void main(String[] args) {
        SQLTracker sqlt = new SQLTracker("app.properties");
        Post post = new Post("www", "blablabla", LocalDateTime.now().toString());
        sqlt.addPost(post);
        sqlt.findAll().forEach(System.out::println);


    }


}