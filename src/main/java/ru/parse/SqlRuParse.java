package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Parse;
import ru.Post;
import ru.PropertiesCreator;

import java.io.IOException;
import java.util.*;

public class SqlRuParse implements Parse {
    private ParseDate parseDate;
    private List<Post> postSet;
    private int listSize;
    private Properties properties;

    public SqlRuParse() {
        listSize = 0;
        properties = PropertiesCreator.getProperties("parse.properties");
        parseDate = new ParseDate();
        postSet = new ArrayList<>();
    }

    public Document getDocument(String url) {
        try {
          return Jsoup.connect(url).get();
        } catch (IOException ioe) {
            ioe.fillInStackTrace();
        }
        return null;
    }

    @Override
    public Post detail(String url) {
        Document document = getDocument(url);
        Elements els1 = document.select(properties.getProperty("href"));
        Elements els2 = document.select(properties.getProperty("date"));
        listSize++;
        Post rsl = new Post(listSize,
                document.title(),
                url,
                replace(els1.get(1).text()),
                parseDate.parse(els2.get(0).text().split(" \\[")[0]));
        postSet.add(rsl);
        return rsl;
    }

    public List<Post> list(String url) {
        Document document = getDocument(url);
        Elements els = document.select(properties.getProperty("oldHref"));
        for (Element el : els) {
            String s = el.child(0).attr("href");
            detail(s);
        }
        return postSet;
    }

    public List<Post> getHrefs(String url, int pages) {
        for (int i = 1; i <= pages; i++) {
            String u = url + i;
            list(u);
        }
        System.out.println("getHrefs");
        return postSet;
    }

    private String replace(String text) {
        if (text.contains("'")) {
           return text.replace("'", ".");
        }
        return text;
    }
}
