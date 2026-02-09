package feedParser;

import feed.Article;
import feed.Feed;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.*;

import org.jsoup.Jsoup;
import org.w3c.dom.*;

/**
 * Parser for RSS feeds.
 * <p>
 * {@code RSSParser} is a concrete implementation of {@link FeedParser} that
 * parses RSS-formatted XML content and extracts articles into a {@link Feed}.
 * It respects a specified limit on the number of articles to extract.
 * </p>
 */
public class RSSParser extends FeedParser {

    /**
     * Parses the provided RSS XML content and extracts up to {@code limit}
     * articles into a {@link Feed}.
     *
     * @param xmlContent the raw RSS XML content as a {@code String}
     * @param limit the maximum number of articles to parse
     * @return a {@link Feed} containing the parsed articles from the RSS feed
     * @throws Exception if parsing the RSS feed fails
     */
    @Override
    public Feed parseFeed(String xmlContent, int limit) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));

        Element channel = (Element) doc.getElementsByTagName("channel").item(0);
        String siteName = getText(channel, "title");

        Feed feed = new Feed(siteName);

        NodeList items = doc.getElementsByTagName("item");
        SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

        for (int i = 0; i < items.getLength() && i < limit; i++) {
            Element item = (Element) items.item(i);

            String title = getText(item, "title");
            String description = getText(item, "description");

            String pubDateStr = getText(item, "pubDate");
            String link = getText(item, "link");

            Date pubDate = null;
            if (pubDateStr != null && !pubDateStr.isEmpty()) {
                pubDate = format.parse(pubDateStr);
            }
            
            /*
            EJERCICIO 4

            La modificación se hace en esta clase (RSSParser) con el objetivo de mantener el principio de resposabilidad única (una clase debe tener una única razón para cambiar, es decir, su responsabilidad debe ser única) y la alta cohesión (todos los elementos dentro de una clase deben estar relacionados).

            Esto se hace aquí porque el concepto de esta clase se basa en parsear y convertir el contenido xml en artículos que forman un feed. 

            Además, si esto lo realizase en otra clase o archivo, incrementaría el acoplamiento entre clases ya que debería interactuar con la clase Feed o Article porque tengo que utilizar los campos title y description(utilizados para computar las heurísticas) para borrar todos los tags.
            */
           
            if(title != null){
                title = Jsoup.parse(title).text();
            }
            if(description != null){
                description = Jsoup.parse(description).text();
            }

            Article article = new Article(title, description, pubDate, link);
            feed.getArticles().add(article);
        }

        return feed;
    }

    private String getText(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list != null && list.getLength() > 0) {
            Node node = list.item(0);
            if (node != null && node.getTextContent() != null) {
                return node.getTextContent().trim();
            }
        }
        return null;
    }

}
