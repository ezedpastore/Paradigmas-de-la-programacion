package namedEntity.place;

public class City extends Place {
    private String country;
    private String capital;
    private int population;

    public City(String name, String category, int frequency) {
        super(name, category, frequency);

        this.country = getRandomFormListString(countryList);
        this.capital = getRandomFormListString(capitalList);
        this.population = getRandomInt(10000, 10000000);
        this.setTopic(this.getRandomTopic());
    }

    public int getPopulation() {
        return this.population;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCapital() {
        return this.capital;
    }

    @Override
    public void prettyPrint() {
        System.out.println("City: " + this.getName() + ", frequency: " + this.getFrequency() + ", country: "
                + this.getCountry() + ", capital: "
                + this.getCapital() + ", population: " + String.format("%,d", this.getPopulation()) + ", topic: " + this.getTopic());
    }
}
