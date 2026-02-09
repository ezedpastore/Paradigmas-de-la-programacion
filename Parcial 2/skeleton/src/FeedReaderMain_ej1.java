import subscription.Subscription;
import subscription.Subscriptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Request.Requester;
import parser.Parser;
import feed.Feed;


public class FeedReaderMain_ej1 {
	public static void main(String[] args) {
	
		Subscriptions sub = new Subscriptions("config/subscriptions_ej1.json");
		for (Subscription s: sub.getSubscriptions()){
			Requester request = new Requester(s.getUrl(),s.getUrlType());
			Feed feed = Parser.parse(request.getResponse(), request.getUrl() ,request.getType()); 
			
			if("".equals(s.getDownload())){
                feed.prettyPrint();
            }
            else{
                BufferedWriter bw = null;
                try{
                    FileWriter fw = new FileWriter(s.getDownload());

                    bw = new BufferedWriter(fw);
                    bw.write(feed.toString());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                finally{
                    if(bw != null){
                        try{
                            bw.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
		}
	}
}
