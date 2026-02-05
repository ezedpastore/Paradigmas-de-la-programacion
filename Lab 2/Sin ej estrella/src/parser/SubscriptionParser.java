package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;

import subscription.SingleSubscription;
import subscription.Subscription;
/*
* Esta clase implementa el parser del  archivo de suscripción (json)
* Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
* */

public class SubscriptionParser {
    static public enum Parser {
        RSS,
        REDDIT
    }

    static public Subscription getSubscription() throws Exception {
        FileReader reader;
        try {
            reader = new FileReader("config/subscriptions.json");
        } catch (FileNotFoundException e) {
            // ! Ver que onda con este caso
            throw e;
        }
        JSONArray subsArr = new JSONArray(new JSONTokener(reader));

        Subscription subscription = new Subscription(null);
        for (int i = 0; i < subsArr.length(); i++) {
            JSONObject sub = subsArr.getJSONObject(i);

            String url = sub.getString("url");
            String urlType = sub.getString("urlType");
            String download = sub.getString("download");
            String[] urlParams = getUrlParams(sub.getJSONArray("urlParams"));

            SingleSubscription singleSubscription = createSingleSubscription(url, urlType, download, urlParams);

            subscription.addSingleSubscription(singleSubscription);
        }

        return subscription;
    }

    static private String[] getUrlParams(JSONArray jsonUrlParams) {
        String[] urlParams = new String[jsonUrlParams.length()];
        for (int j = 0; j < jsonUrlParams.length(); j++) {
            urlParams[j] = jsonUrlParams.getString(j);
        }

        return urlParams;
    }

    static private SingleSubscription createSingleSubscription(
            String url,
            String urlType,
            String download,
            String[] urlParams)
            throws Exception {

        // Si no es del tipo RSS o REDIT tira el error IllegalArgumentException
        Parser.valueOf(urlType.toUpperCase());

        // ! Se tiene que validar el resto de campos

        SingleSubscription singleSubscription = new SingleSubscription(url, null, urlType);

        for (String urlParam : urlParams) {
            singleSubscription.setUlrParams(urlParam);
        }

        return singleSubscription;
    }

    static public GeneralParser getParser(String urlType) throws Exception {
        GeneralParser parser;

        // Si no es del tipo RSS o REDIT tira el error IllegalArgumentException
        Parser parserType = Parser.valueOf(urlType.toUpperCase());

        if (parserType.equals(Parser.RSS)) {
            parser = new RssParser();
        } else {
            parser = new RedditParser();
        }

        return parser;
    }

}
