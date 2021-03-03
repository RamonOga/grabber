package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Post;
import ru.PropertiesCreator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class SqlRuParse {
    private ParseDate parseDate;
    private Set<Post> postSet;
    PropertiesCreator propertiesCreator;

    public SqlRuParse() {
        propertiesCreator = new PropertiesCreator();
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


    private boolean urlParse(String url, String hrefLink, String dateLink) {
        int setSize = postSet.size();
        Document document = getDocument(url);
        Elements els1 = document.select(hrefLink);
        Elements els2 = document.select(dateLink);
        postSet.add(new Post(document.title(),
                url,
                els1.get(1).text(),
                parseDate.parse(els2.get(0).text().split(" \\[")[0])));
        return postSet.size() > setSize;
    }

    public Set<Post> getPages(Set<String> urlsSet) {
        Properties properties = propertiesCreator.getProperties("parse.properties");
        for (String url : urlsSet) {
            urlParse(url, properties.getProperty("href"), properties.getProperty("date"));
        }
        return postSet;
    }

    private Set<String> getHref(String url, String href) {
        Set<String> rslList = new HashSet<>();
        Document document = getDocument(url);
        Elements els = document.select(href);
        for (Element el : els) {
            rslList.add(el.child(0).attr("href"));
        }
        return rslList;
    }
    public Set<String> getHrefs(String url, String href, int pages) {
        Set<String> hrefsSet = new HashSet<>();
        for (int i = 1; i <= pages; i++) {
            hrefsSet.addAll(getHref(url + i, href));
        }
        return hrefsSet;
    }
}
