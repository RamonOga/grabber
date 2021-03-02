package ru;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.db.PropertiesCreator;
import ru.db.WorkerDB;
import ru.parse.SqlRuParse;

import java.util.Properties;
import java.util.Set;

public class Runner {
    public static void main(String[] args) {
        WorkerDB sqlt = new WorkerDB("app.properties");
        SqlRuParse sqlRuParse = new SqlRuParse();
        PropertiesCreator propertiesCreator = new PropertiesCreator();
        Properties properties = propertiesCreator
                .getProperties("parse.properties");
        Document doc = sqlRuParse.getDocument
                ("https://www.sql.ru/forum/1333854/ishhem-senior-java-developer-udalenno-ot-250k");
        System.out.println(doc.title());
        //System.out.println(doc.body().text());
        Elements els1 = doc.select(".msgBody");
        Elements els2 = doc.select(".msgFooter");
        System.out.println(els1.get(1).text());
        System.out.println(els2.get(0).text().split(" \\[")[0]);
        Set<String> setHrefs = sqlRuParse.getHrefs(properties.getProperty("url"), properties.getProperty("href"), 2);
        setHrefs.forEach(System.out::println);

       /* Set<Post> postSet = sqlRuParse.getPages(properties.getProperty("url"),
               properties.getProperty("href"),
                properties.getProperty("date"), 1);
        sqlt.addAll(postSet);
        sqlt.findAll().forEach(System.out::println);
        System.out.println(sqlt.findAll().size());*/
    }
}