package namedEntity.place;

public class Direction extends Place {
    private String city;

    public Direction(String name, String category, int frequency) {
        super(name, category, frequency);

        this.city = getRandomFormListString(cityList);
        this.setTopic(this.getRandomTopic());
    }

    public String getCity() {
        return this.city;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Direction: " + this.getName() + ", frequency: " + this.getFrequency() + ", city: "
                + this.getCity() + ", topic: " + this.getTopic());
    }

}
