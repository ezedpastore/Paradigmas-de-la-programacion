package namedEntity.person;

import java.util.Arrays;
import java.util.List;

public class Title extends Person {
    
    public List<String> listProfessions = Arrays.asList("Doctor", "Professor", "Engineer", "Architect", "Lawyer", "Judge", "Nurse", "Pharmacist", "Dentist", "Veterinarian", "Accountant", "Psychologist", "Therapist", "Scientist", "Pilot", "Captain", "Chief", "Detective", "Manager", "Director", "President", "Vice President", "Minister", "Ambassador", "Sir", "Madam", "Dr.", "PhD", "MD", "Esq.");
    private String profession;
    private String canonicalProfession;
    
    public Title (String name, String category, int frequency) {
        super(name, category, frequency);
        this.profession = chooseRandomElem(listProfessions);
        this.canonicalProfession = canonicalizeString(name);
        this.setTopic(this.getRandomTopic());
    }

    public String getProfession () {
        return this.profession;
    }

    public String getCanonicalProfession () {
        return this.canonicalProfession;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Title: " + this.getName() + ", frequency: " + this.getFrequency() + ", profession: "+ this.getProfession() + ", canonicalProfession: "+ this.getCanonicalProfession() + ", topic: " + this.getTopic());
    }
}
