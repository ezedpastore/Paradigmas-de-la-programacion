import subscription.Subscription;
import subscription.Subscriptions;
import Request.Requester;
import parser.Parser;
import feed.Feed;
import namedEntity.heuristics.Heuristic;
import namedEntity.heuristics.QuickHeuristic;


public class FeedReaderMain_ej3 {
	public static void main(String[] args) {
	
		Subscriptions sub = new Subscriptions("config/subscriptions_ej3.json");
		for (Subscription s: sub.getSubscriptions()){
            //System.out.println(s.getUrl());
            //System.out.println(s.getUrlType());

			Requester request = new Requester(s.getUrl(), s.getUrlType());

            //System.out.println(request.getResponse());

			Feed feed = Parser.parse(request.getResponse(), request.getUrl() ,request.getType()); 
			
            Heuristic h = new QuickHeuristic();
			h.computeEntities(feed);

            String feedString = "\n---------------------" + s.getUrl() + "---------------------\n";
            System.out.println(feedString);
			h.printEntities();
		}		
	}

}
