package ru.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Post;

import javax.print.Doc;
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
            int i = 1;
            for (Element href : rowsHref) {
                Element date = rowsDates.get(i);
                i += 2;
                postSet.add(new Post(href.text(),
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

    public Set<String> getHref(String url, String href) {
        Set<String> rslList = new HashSet<>();
        Document document = getDocument(url);
        Elements els = document.select(href);
        for (Element el : els) {
            rslList.add(el.child(0).attr("href"));
        }
        return rslList;
    }
    public Set<String> getHrefs(String url, String href, int pages) {
        return null;
    }
}
