package namedEntity.heuristic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertiesParser {
    
    public static Map<String, String> readFileContents() {
        Map<String, String> categoryMapFromFile = new HashMap<>();

        String key = null;
        String value = null;
        String [] splitLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("config/tech_stack.properties"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                splitLine = line.split("=");

                key = splitLine[0];
                value = splitLine[1];

                categoryMapFromFile.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categoryMapFromFile;
    }
}
