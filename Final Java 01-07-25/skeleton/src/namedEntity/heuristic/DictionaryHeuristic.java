package namedEntity.heuristic;

import java.util.Map;

public class DictionaryHeuristic extends Heuristic {

     private static Map<String, String> categoryMap = Map.ofEntries(
        // Company
        Map.entry("Microsoft", "COMPANY"),
        Map.entry("Apple", "COMPANY"),
        Map.entry("Google", "COMPANY"),
        Map.entry("DoorDash", "COMPANY"),
        Map.entry("OpenAI", "COMPANY"),
        Map.entry("NVIDIA", "COMPANY"),
        Map.entry("GitLab", "COMPANY"),

        // Person
        Map.entry("Torvalds", "PERSON"),
        Map.entry("Marx", "PERSON"),
        Map.entry("Turing", "PERSON"),

        // Location
        Map.entry("USA", "LOCATION"),
        Map.entry("Germany", "LOCATION"),
        Map.entry("India", "LOCATION"),

        // Other
        Map.entry("Linux", "OTHER"),
        Map.entry("Python", "OTHER"),
        Map.entry("Rust", "OTHER"),
        Map.entry("Ethereum", "OTHER")
    );


    @Override
    public String getCategory(String word) {
        String category = null;

        char firstLetter = Character.toUpperCase(word.charAt(0));
        String restOfWord = word.substring(1);
        restOfWord = restOfWord.toLowerCase();

        word = Character.toString(firstLetter) + restOfWord;

        if(!stopWords.contains(word.toLowerCase())){
            category = categoryMap.get(word);
        }

        return category;
    }
}
