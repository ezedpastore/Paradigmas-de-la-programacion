package httpRequest;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import parser.SubscriptionParser.Parser;

/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

public class httpRequester {
    private String url;

    public httpRequester(String url) {
        this.url = url;
    }
	
    public String getFeedRss() throws Exception {
        String feedRssXml = null;
        
        feedRssXml = get();
        
        return feedRssXml;
	}

	public String getFeedReedit() {
		String feedReeditJson = null;
		return feedReeditJson;
	}

    public String getData(String urlType) throws Exception {
		String data;

		Parser parserType = Parser.valueOf(urlType.toUpperCase());

		if (parserType.equals(Parser.RSS)) {
			data = this.getFeedRss();
		} else {
			data = this.getFeedReedit();
		}

		return data;
	}

	private String get() throws Exception {
		URL url = new URI(this.url).toURL();

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int status = con.getResponseCode();

		if (status != HttpURLConnection.HTTP_OK) {
			throw new Exception();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();

		con.disconnect();

		String res = content.toString();

		// System.out.println("Status: " + status);
		// System.out.println(content);

		return res;
	}

	public static void main(String[] args) throws Exception {
		String url = "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
		String res = new httpRequester(url).getFeedRss();

		System.out.println(res);
	}
}