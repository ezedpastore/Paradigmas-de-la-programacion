package namedEntity.heuristic;

import java.util.Map;

public class TechnologyHeuristicEj3 extends Heuristic {
    
    @Override
    public String getCategory(String word) {
        String result = null;

        Map<String, String> categoryMap = PropertiesParser.readFileContents();

        if(!stopWords.contains(word.toLowerCase())){
            result = categoryMap.get(word.toLowerCase());
        }        

        return result;
    }
}
