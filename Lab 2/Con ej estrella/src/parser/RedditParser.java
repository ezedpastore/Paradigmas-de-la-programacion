package parser;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import feed.Article;
import feed.Feed;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implementación
 * */

public class RedditParser extends GeneralParser {

    private Feed feed;

    public RedditParser() {
        // Podrías inicializar con un nombre de sitio más genérico o basado en la URL
        this.feed = new Feed("Reddit"); 
    }

    @Override
    public void parse(String data) {
        try{
            // 1. Cargar el JSON completo
            JSONObject fullResponse = new JSONObject(data);
                
            // 2. Navegar a la sección de artículos: data -> children
            JSONObject outerData = fullResponse.getJSONObject("data");
            JSONArray posts = outerData.getJSONArray("children");

            for(int i = 0; i < posts.length(); i++){
                JSONObject post = posts.getJSONObject(i);
                JSONObject innerData = post.getJSONObject("data");

                String title = innerData.getString("title");

                String description = innerData.getString("selftext");

                String link = innerData.getString("url");

                double createdUtc = innerData.getDouble("created_utc");
                Date pubDate = convertUnixTimestampToDate(createdUtc);

                Article article = new Article(title, description, pubDate, link);

                this.feed.addArticle(article);
            }
        }
        catch (Exception e) {
            System.err.println("Error parsing Reddit JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convierte el timestamp Unix (segundos) a un objeto java.util.Date.
     */
    private Date convertUnixTimestampToDate(double timestampSeconds) {
        // Multiplicar por 1000 para convertir segundos a milisegundos (que Date requiere)
        long timestampMillis = (long) (timestampSeconds * 1000);
        return new Date(timestampMillis);
    }

    @Override
    public Feed getFeed() {
        return this.feed;
    }

}
