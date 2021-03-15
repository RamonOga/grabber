package ru;

import ru.db.PsqlStore;
import ru.parse.SqlRuParse;

import java.util.List;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) {
        Properties properties = PropertiesCreator.getProperties("app.properties");
        PsqlStore sqlt = new PsqlStore(properties);
        SqlRuParse sqlRuParse = new SqlRuParse(properties);
        List<Post> hrefSet = sqlRuParse.getHrefs(properties.getProperty("parseUrl"), 1);
        sqlt.addAll(hrefSet);
        System.out.println(hrefSet.size());
        System.out.println(sqlt.findById("1"));
    }
}