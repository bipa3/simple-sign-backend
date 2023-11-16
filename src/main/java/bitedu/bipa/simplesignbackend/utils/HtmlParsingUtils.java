package bitedu.bipa.simplesignbackend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParsingUtils {
    public static String parseHtml(String html) {
        Document document = Jsoup.parse(html);
        return document.text();
    }
}
