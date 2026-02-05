package namedEntity.person;

import java.util.Random;

import namedEntity.NamedEntity;

public class Person extends NamedEntity {

    private int id;

    public Person(String name, String category, int frequency) {
        super(name, category, frequency);
        this.id = getRandomInt();
    }

    public int getRandomInt() {
        Random rand = new Random();
        int randomNum = rand.nextInt(1000);

        return randomNum;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Person: " + this.getName() + ", frequency: " + this.getFrequency() + "Id: " + this.id
                + ", topic: " + this.getTopic());
    }
}
