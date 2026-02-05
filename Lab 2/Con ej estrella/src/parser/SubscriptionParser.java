package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import subscription.SingleSubscription;
import subscription.Subscription;
/*
* Esta clase implementa el parser del  archivo de suscripción (json)
* Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
* */

public class SubscriptionParser {

    public SubscriptionParser(){
    }

    public Subscription parseSubscriptionJson() throws Exception{
        Subscription s = new Subscription();

        try{
            FileReader f = new FileReader("config/subscriptions.json");

            JSONArray subsArray = new JSONArray(new JSONTokener(f));
            
            String url, urlType;
            String[] urlParams;

            for(int i = 0; i < subsArray.length(); i++){
                JSONObject sub = subsArray.getJSONObject(i);

                url = sub.getString("url");
                urlType = sub.getString("urlType");
                urlParams = this.getUrlParamsFromJson(sub.getJSONArray("urlParams"));

                s.addSingleSubscription(createSingleSubscription(url, urlType, urlParams));
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return s;
    }

    private String[] getUrlParamsFromJson(JSONArray urlParamsFromJson){
        int urlParamsSize = urlParamsFromJson.length();
        
        String[] urlParams = new String[urlParamsSize];

        try{
            for(int i = 0; i < urlParamsSize; i++){
                urlParams[i] = urlParamsFromJson.getString(i);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return urlParams;
    }

    private SingleSubscription createSingleSubscription(String url, String urlType, String[] urlParams){
        SingleSubscription s = new SingleSubscription(url, null, urlType);

        for(int i = 0; i < urlParams.length; i++){
            s.setUlrParams(urlParams[i]);
        }

        return s;
    }
}
