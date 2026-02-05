package parser;

import feed.Article;
import feed.Feed;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * */

public class RssParser extends GeneralParser {
    private Feed feed;

    public RssParser() {
        this.feed = new Feed("nytimes");
    }

    @Override
    public void parse(String data) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes("UTF-8"));
            Document xmldoc = docBuilder.parse(input);

            NodeList items = xmldoc.getElementsByTagName("item");
            int item_length = items.getLength();

            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",
                    java.util.Locale.ENGLISH);

            for (int i = 0; i < item_length; i++) {
                Node node = items.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element item = (Element) node;
                    String title = getTagValue("title", item);
                    String pubDateStr = getTagValue("pubDate", item);
                    String description = getTagValue("description", item);
                    String link = getTagValue("link", item);
                    Date pubDate = null;
                    try {
                        if (!pubDateStr.equals("Not available")) {
                            pubDate = formatter.parse(pubDateStr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Article article = new Article(title, description, pubDate, link);
                    feed.addArticle(article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTagValue(String tag, Element element) {
        String value = "Not available";

        NodeList list = element.getElementsByTagName(tag);
        if (list.getLength() > 0) {
            Node node = list.item(0).getFirstChild();
            if (node != null) {
                value = node.getNodeValue().trim();
            }
        }
        return value;
    }

    public Feed getFeed() {
        return feed;
    }

}
