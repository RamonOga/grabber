package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Post;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SqlRuParse {
    private ParseDate parseDate;

    public SqlRuParse() {
        parseDate = new ParseDate();
    }

    public Set<Post> urlParse(String url, String hrefLink, String dateLink) {
        Set<Post> rsl = null;
        try {
            Document document = Jsoup.connect(url).get();
            rsl = new HashSet<>();
            Elements rowsHref = document.select(hrefLink);
            Elements rowsDates = document.select(dateLink);
            int i = 1;
            for (Element href : rowsHref) {
                Element date = rowsDates.get(i);
                i += 2;
                rsl.add(new Post(href.child(0).attr("href"), href.text(), date.text()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
