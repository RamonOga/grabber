package ru;

import ru.db.DataBase;
import ru.db.SQLTracker;

import java.time.LocalDateTime;

public class Runner {
    public static void main(String[] args) throws ClassNotFoundException {
        DataBase dataBase = DataBase.getDataBase("app.properties");
        Post post = new Post("www", "blablabla", LocalDateTime.now().toString());
        SQLTracker sqlt = new SQLTracker(dataBase.getConnection());
        //sqlt.addPost(post);
        sqlt.findAll().forEach(System.out::println);
        sqlt.close();
    }


}