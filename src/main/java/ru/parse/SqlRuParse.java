package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Parse;
import ru.Post;
import ru.PropertiesCreator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class SqlRuParse implements Parse {
    private ParseDate parseDate;
    private Set<Post> postSet;
    private Properties properties;

    public SqlRuParse() {
        properties = PropertiesCreator.getProperties("parse.properties");
        parseDate = new ParseDate();
        postSet = new HashSet<>();
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
        Post rsl = new Post(document.title(),
                url,
                replace(els1.get(1).text()),
                parseDate.parse(els2.get(0).text().split(" \\[")[0]));
        postSet.add(rsl);
        return rsl;
    }

    /*public Set<Post> getPages(Set<String> urlsSet) {
        for (String url : urlsSet) {
            detail(url);
        }
        return postSet;
    }*/

    public Set<Post> list(String url) {
        Document document = getDocument(url);
        Elements els = document.select(properties.getProperty("oldHref"));
        for (Element el : els) {
            String s = el.child(0).attr("href");
            detail(s);
        }
        return postSet;
    }
    public Set<Post> getHrefs(String url, int pages) {
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
