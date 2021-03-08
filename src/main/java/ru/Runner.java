package ru;

import ru.db.PsqlStore;
import ru.parse.SqlRuParse;

import java.util.Properties;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        PsqlStore sqlt = new PsqlStore(PropertiesCreator.getProperties("app.properties"));
        SqlRuParse sqlRuParse = new SqlRuParse();
        Properties properties = PropertiesCreator.getProperties("parse.properties");
        Set<Post> hrefSet = sqlRuParse.getHrefs(properties.getProperty("url"), 5);
        sqlt.addAll(hrefSet);
        System.out.println(hrefSet.size());
        System.out.println(sqlt.findById("1"));
    }
}