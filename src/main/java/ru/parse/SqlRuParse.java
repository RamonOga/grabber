package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Post;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SqlRuParse {
    private ParseDate parseDate;
    private Set<Post> postSet;

    public SqlRuParse() {
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
        try {
            Document document = Jsoup.connect(url).get();
            Elements rowsHref = document.select(hrefLink);
            Elements rowsDates = document.select(dateLink);
            String title = document.select("title").text();
            int i = 1;
            for (Element href : rowsHref) {
                Element date = rowsDates.get(i);
                i += 2;
                postSet.add(new Post(title,
                        href.child(0).attr("href"),
                        href.text(),
                        parseDate.parse(date.text())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postSet.size() > setSize;
    }

    public Set<Post> getPages(String url, String hrefLink, String dateLink, int pages) {
        for (int i = 1; i <= pages; i++) {
            urlParse(url + i, hrefLink, dateLink);
            System.out.println(postSet.size());
        }
        return postSet;
    }
}
