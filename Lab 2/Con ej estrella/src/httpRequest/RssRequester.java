package httpRequest;

import java.net.HttpURLConnection;

public class RssRequester extends Requester {

    public RssRequester(String url) {
        super(url);
    }
    
    @Override
    protected void setupConnection(HttpURLConnection con) {
        // RSS no necesita encabezados especiales, así que no se hace nada.
    }
}
