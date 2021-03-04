package ru;

import ru.db.PsqlStore;
import ru.parse.SqlRuParse;

import java.util.Properties;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        PsqlStore sqlt = new PsqlStore("app.properties");
        SqlRuParse sqlRuParse = new SqlRuParse();
        System.out.println(sqlt.findById("1"));
        Properties properties = PropertiesCreator.getProperties("parse.properties");
        Set<String> hrefSet = sqlRuParse.getHrefs(properties.getProperty("url"), properties.getProperty("oldHref"), 5);
        Set<Post> postSet = sqlRuParse.getPages(hrefSet);
        System.out.println(postSet.size());
        sqlt.addAll(postSet);
    }
}