package namedEntity.person;

import java.util.Arrays;
import java.util.List;

public class LastName extends Person {
    private List<String> listOrigin = Arrays.asList("Italian", "German", "French", "Spanish", "English","Portuguese", "Russian", "Chinese","Japanese", "Korean","Turkish", "Arabic", "Dutch", "Swedish", "Norwegian",
    "Polish", "Greek", "Hungarian", "Czech", "Indian","Iranian", "Brazilian", "Mexican", "Canadian", "Australian"); 

    private String origin;
    private String canonicalLastName;

    public LastName (String name, String category, int frequency) {
        super(name, category, frequency);
        this.origin = chooseRandomElem(listOrigin);
        this.canonicalLastName = canonicalizeString(name);
        this.setTopic(this.getRandomTopic());
    }

    public String getOrigin () {
        return this.origin;
    }
    
    public String getCanonicalLastName () {
        return this.canonicalLastName;
    }

    @Override
    public void prettyPrint() {
        System.out.println("LastName: " + this.getName() + ", frequency: " + this.getFrequency() + ", origin: "+ this.getOrigin() + ", canonicalLastName: "+ this.getCanonicalLastName() + ", topic: " + this.getTopic());
    }
}