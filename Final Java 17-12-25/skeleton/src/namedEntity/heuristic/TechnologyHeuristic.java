package namedEntity.heuristic;

import java.util.Map;

public class TechnologyHeuristic extends Heuristic {

    private static Map<String, String> categoryMap = Map.ofEntries(
        Map.entry("java", "LANGUAGES"),
        Map.entry("c++", "LANGUAGES"),
        Map.entry("python", "LANGUAGES"),
        Map.entry("rust", "LANGUAGES"),
        Map.entry("javascript", "LANGUAGES"),
        Map.entry("kotlin", "LANGUAGES"),
        Map.entry("docker", "INFRA"),
        Map.entry("kubernetes", "INFRA"),
        Map.entry("aws", "INFRA"),
        Map.entry("linux", "INFRA"),
        Map.entry("jenkins", "INFRA"),
        Map.entry("mysql", "DATABASE"),
        Map.entry("postgres", "DATABASE"),
        Map.entry("mongodb", "DATABASE"),
        Map.entry("redis", "DATABASE"),
        Map.entry("sql", "DATABASE"),
        Map.entry("algorithm", "CONCEPTS"),
        Map.entry("recursion", "CONCEPTS"),
        Map.entry("api", "CONCEPTS"),
        Map.entry("framework", "CONCEPTS"),
        Map.entry("compiler", "CONCEPTS")
    );

    @Override
    public String getCategory(String word) {
        String result = null;

        if(!stopWords.contains(word.toLowerCase())){
            result = categoryMap.get(word.toLowerCase());
        }        

        return result;
    }

}