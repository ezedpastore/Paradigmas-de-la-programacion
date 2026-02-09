
import feed.Article;
import feed.Feed;
import feedParser.FeedParser;
import feedParser.RSSParser;
import httpRequest.HttpRequester;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import namedEntity.heuristic.TechnologyHeuristic;
import namedEntity.heuristic.TechnologyHeuristicEj3;
import subscription.Subscription;
import subscription.SubscriptionParser;

public class FeedReaderMain {

    public static FeedParser getParser(String feedType) {
        switch (feedType) {
            case "rss" -> {
                return new RSSParser();
            }
            default ->
                throw new IllegalArgumentException("Unknown feed feedType: " + feedType);
        }
    }

    /**
     * Selects and returns a Heuristic based on the command-line arguments.
     *
     * @param args The command-line arguments.
     * @return The selected Heuristic implementation.
     */
    public static Heuristic getHeuristic(String[] args) {
        Heuristic heuristic = new RandomHeuristic(); // default

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-h") && i + 1 < args.length) {
                String heuristicArg = args[i + 1].toLowerCase();

                if (heuristicArg.equals("random")) {
                    heuristic = new RandomHeuristic();
                } else if (heuristicArg.equals("quick")) {
                    heuristic = new QuickHeuristic();
                } else if (heuristicArg.equals("technology")) {
                    heuristic = new TechnologyHeuristic();
                } else if (heuristicArg.equals("technology3")) {
                    heuristic = new TechnologyHeuristicEj3();
                } else {
                    System.out.println("Unknown heuristic: " + heuristicArg + ". Using default (RandomHeuristic).");
                }
            }
        }

        return heuristic;
    }


    /**
     * Prints the top 20 named entities (or fewer if there are fewer in the
     * list) sorted by frequency in descending order.
     *
     * @param namedEntities The list of named entities from which to print the
     * top entities and their frequencies.
     */
    public static void printEntityCounts(List<NamedEntity> namedEntities) {
        // Sort the list by frequency descending
        namedEntities.sort((e1, e2) -> Integer.compare(e2.getFrequency(), e1.getFrequency()));

        // Determine how many entities to print (max 20 or fewer if list is smaller)
        int entitiesToPrint = Math.min(20, namedEntities.size());

        // Print the top entities
        System.out.println("Top " + entitiesToPrint + " Named Entities by Frequency:");
        for (int i = 0; i < entitiesToPrint; i++) {
            NamedEntity entity = namedEntities.get(i);
            System.out.println(entity.getName() + " (" + entity.getCategory() + "): " + entity.getFrequency());
        } 
    }

    public static void printCategoryFrequencies(List<NamedEntity> namedEntities) {
        List<String> tecnoCat = List.of("LANGUAGES", "INFRA", "DATABASE", "CONCEPTS");

        // Sort the list by frequency descending
        namedEntities.sort((e1, e2) -> Integer.compare(e2.getFrequency(), e1.getFrequency()));

        // Determine how many entities to print (max 20 or fewer if list is smaller)
        //int entitiesToPrint = Math.min(20, namedEntities.size());

        // Print the top entities
        //System.out.println("Top " + entitiesToPrint + " Named Entities by Frequency:");
        for(String category : tecnoCat){
            int count = 1;

            System.out.println("CATEGORY: " + category);
            for (int i = 0; i < namedEntities.size(); i++) {
                NamedEntity entity = namedEntities.get(i);

                if(entity.getCategory().equals(category) && count < 4){
                    System.out.println("(" + count + ") " + entity.getName() + ": " + entity.getFrequency());
                    
                    count++;
                }

            } 
            System.out.println("\n");
            
            count = 1;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("************* FeedReader version 2.0 *************");

        // Read file with subscriptions
        SubscriptionParser sp = new SubscriptionParser();
        List<Subscription> subscriptions = sp.parseFromFile("config/subscriptions.json");

        // For each subscription, fetch the feed
        HttpRequester requester = new HttpRequester();

        // Global map to accumulate named entities and frequencies for all
        // articles and all feeds. The Key is the name of the entity
        Map<String, NamedEntity> globalEntityMap = new HashMap<>();

        Heuristic heuristic = getHeuristic(args);

        for (Subscription subscription : subscriptions) {
            String feedContent = requester.getUrlContent(subscription.getUrl());
            FeedParser feedParser = getParser(subscription.getUrlType());
            Feed feed = feedParser.parseFeed(feedContent);

            // Extract named entities from all articles
            for (Article article : feed.getArticles()) {
                String text = article.getTitle() + "/n" + article.getText();
                List<NamedEntity> namedEntities = heuristic.getNamedEntities(text);

                // Merge extracted entities into global map
                // Group them by name and sum their frequencies
                for (NamedEntity entity : namedEntities) {
                    String name = entity.getName();
                    if (globalEntityMap.containsKey(name)) {
                        // If already present, increase the frequency
                        NamedEntity existingEntity = globalEntityMap.get(name);
                        existingEntity.increaseFrequency();
                    } else {
                        // If not present, add it to the map
                        globalEntityMap.put(name, entity);
                    }
                }
            }
        }

        // EJERCICIO 3

        /* Map<String, Integer> categoriesInmutable = Map.ofEntries(
            Map.entry("LANGUAGES", 0),
            Map.entry("INFRA", 0),
            Map.entry("DATABASE", 0),
            Map.entry("CONCEPTS", 0)
        );

        Map<String, Integer> categoriesMutable = new HashMap<>(categoriesInmutable);

        for(String category : categoriesMutable.keySet()){
            for(NamedEntity entity : globalEntityMap.values()){
                if(entity.getCategory().equals(category)){
                    categoriesMutable.put(category, (categoriesMutable.get(category) + 1));
                }
            }
        }

        System.out.println("Named Entities Found:\n");
        for(String category : categoriesMutable.keySet()){
            System.out.println(category + ": " + categoriesMutable.get(category) + "\n");
        } */

        // Print entities and their frequencies
        System.out.println("Named Entities Found:");
        printEntityCounts(new ArrayList<>(globalEntityMap.values()));
    }

}
