 package parser;

import feed.Article;
import feed.Feed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Jsoup;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * */

public class RssParser extends GeneralParser {
    private Feed feed;

    public RssParser() {
        this.feed = new Feed("nytimes");
    }

    @Override
    public void parse(String data){
        try{
            String title, description, link, dateString;
            Date pubDate = null;

            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes("UTF-8"));
            
            Document xmlDoc = docBuilder.parse(input);

            NodeList articles = xmlDoc.getElementsByTagName("item");
            int amountArticles = articles.getLength();

            for(int i = 0; i < amountArticles; i++){
                Element article = (Element) articles.item(i);

                title = getContentFromTag(article, "title");
                description = getContentFromTag(article, "description");
                link = getContentFromTag(article, "link");
                dateString = getContentFromTag(article, "pubDate");

                if(!dateString.equals("Not available")){
                    pubDate = formatter.parse(dateString);
                }

                if (description != null && !description.isEmpty()) {
                    description = Jsoup.parse(description).text();
                }
                if (title != null && !title.isEmpty()) {
                    title = Jsoup.parse(title).text();
                }

                this.feed.addArticle(new Article(title, description, pubDate, link));
            }
        }
        catch(ParserConfigurationException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(SAXException e){
            e.printStackTrace();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }

    private String getContentFromTag(Element e, String tag){
        String content = null;

        try{
            NodeList contents = e.getElementsByTagName(tag);
            if(contents.getLength() > 0){
                content = contents.item(0).getFirstChild().getNodeValue();
            }
        }
        catch(DOMException exc){
            exc.printStackTrace();
        }

        return content;
    }

    public Feed getFeed() {
        return feed;
    }
}
