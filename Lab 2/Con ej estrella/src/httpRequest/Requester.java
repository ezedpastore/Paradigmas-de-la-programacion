package httpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */

public abstract class Requester {
    private String url;

    public Requester(String url) {
        this.url = url;
    }

    protected abstract void setupConnection(HttpURLConnection con);

    public String getData(){
		String result = null;
        HttpURLConnection con = null;
        
        try{
            URL url = new URI(this.url).toURL();

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            if(status != HttpURLConnection.HTTP_OK){
                throw new IOException();
            }

            // Flujo de datos de entrada en bytes
            InputStream is = con.getInputStream();
            
            // Transforma el flujo de bytes en carácteres. Es el puente entre los bytes y el texto. Es ineficiente ya que lee carácter por carácter.
            InputStreamReader isr = new InputStreamReader(is);

            // Almacena muchos carácteres en memoria y nos permite leer de línea en línea, haciendo menos accesos y más rápido. 
            BufferedReader in = new BufferedReader(isr);

            String inputLine;
            StringBuffer content = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();

            result = content.toString();
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            con.disconnect();       
        }
        
        return result;
	}
}