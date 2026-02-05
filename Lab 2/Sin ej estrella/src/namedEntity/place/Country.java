package namedEntity.place;

public class Country extends Place {
    // población, lengua oficial
    private String officialLanguage;
    private int population;

    public Country(String name, String category, int frequency) {
        super(name, category, frequency);

        this.officialLanguage = getRandomFormListString(officialLanguageList);
        this.population = getRandomInt(10000, 10000000);
        this.setTopic(this.getRandomTopic());
    }

    public String getOfficialLanguage() {
        return this.officialLanguage;
    }

    public int getPopulation() {
        return this.population;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Country: " + this.getName() + ", frequency: " + this.getFrequency() + ", official language: " + this.getOfficialLanguage() + ", population: " + String.format("%,d", this.getPopulation()) + ", topic: " + this.getTopic());
    }

}
