package httpRequest;

import java.net.HttpURLConnection;

public class RedditRequester extends Requester {
    
    public RedditRequester (String url) {
        super(url);
    }

    @Override
    protected void setupConnection(HttpURLConnection con) {
        con.setRequestProperty("User-Agent", "AppFeedReader");
        con.setRequestProperty("Accept", "application/json");
    }
}
