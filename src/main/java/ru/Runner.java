package ru;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.db.WorkerDB;
import ru.parse.SqlRuParse;

import java.util.Properties;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        WorkerDB sqlt = new WorkerDB("app.properties");
        SqlRuParse sqlRuParse = new SqlRuParse();
        Properties properties = PropertiesCreator.getProperties("parse.properties");
        Set<String> hrefSet = sqlRuParse.getHrefs(properties.getProperty("url"), properties.getProperty("oldHref"), 5);
        Set<Post> postSet = sqlRuParse.getPages(hrefSet);
        postSet.stream().filter(a -> a.getDescription().contains("'")).forEach(System.out::println);
        System.out.println(postSet.size());
        /*sqlt.addAll(postSet);*/
    }
}