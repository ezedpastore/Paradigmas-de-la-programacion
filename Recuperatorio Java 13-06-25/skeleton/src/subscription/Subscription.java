package subscription;

/*Esta clase abstrae el contenido de una sola suscripcion que ocurre en 
lista de suscripciones que figuran en el archivo de suscrpcion (json) */

public class Subscription{
	
	private String url;
	private String urlType;	
    private String content;
	
	public Subscription(String url, String urltype, String content){
		this.url = url;
		this.urlType = urltype;
        this.content = content;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	} 
	
    public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "{url=" + getUrl() + ", urlType=" + getUrlType() + "}";
	}
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}

	
	

	
	
	
}
