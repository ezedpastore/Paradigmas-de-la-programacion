package namedEntity.person;

import java.util.Arrays;
import java.util.List;

public class Name extends Person {
    private List<String> listOrigin = Arrays.asList("Italian", "German", "French", "Spanish", "English","Portuguese", "Russian", "Chinese","Japanese", "Korean","Turkish", "Arabic", "Dutch", "Swedish", "Norwegian",
    "Polish", "Greek", "Hungarian", "Czech", "Indian","Iranian", "Brazilian", "Mexican", "Canadian", "Australian");
    private String origin;
    private String canonicalName;
    
    public Name (String name, String category, int frequency) {
        super(name, category, frequency);
        this.origin = chooseRandomElem(listOrigin);
        this.canonicalName = canonicalizeString(name);
        this.setTopic(this.getRandomTopic());
    }

    public String getOrigin() {
        return this.origin;
    }

    public String getCanonicalName () {
        return this.canonicalName;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Name: " + this.getName() + ", frequency: " + this.getFrequency() + ", origin: "+ this.getOrigin() + ", canonicalName: "
        + this.getCanonicalName() + ", topic: " + this.getTopic());
    }
}
