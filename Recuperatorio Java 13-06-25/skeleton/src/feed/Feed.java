package feed;

import java.util.ArrayList;
import java.util.List;

/*Esta clase modela el concepto de Feed*/

public class Feed {
	String url;
	String type;
	private List<Article> articleList;
	
	public Feed(String url, String type) {
		this.url = url;
		this.type = type;
		this.articleList = new ArrayList<Article>();
	}
	
	public String getUrl(){
		return this.url;
	}
	public String getType(){
		return this.url;
	}
	public List<Article> getArticles(){
		return this.articleList;
	}

	public void addArticle(Article a){
		this.articleList.add(a);
	}
	
	public String toString(boolean complete) {
		String feedString = "\n---------------------" + this.getUrl() + "---------------------\n";
		for (Article a: getArticles()){
			feedString+=a.toString(complete);
		}
		return feedString;
	}

	
	public void prettyPrint(boolean complete){
        System.out.println(this.toString(complete));
	}


}
