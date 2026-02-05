package namedEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import topic.Topic;
import topic.TopicFactory;
import topic.TopicFactory.TopicType;

public class Product extends NamedEntity {
    private String comercial;
    private String producer;

    private List<String> comercialList = Arrays.asList("Global Sales Inc.", "MarketPro", "SalesCo", "Retail Group",
            "E-Com Inc");
    private List<String> productorList = Arrays.asList("TechWorks", "AutoCorp", "FoodTech", "BioPharm", "GreenEnergy");

    public Product(String name, String category, int frequency) {
        super(name, category, frequency);
        this.comercial = chooseRandomElem(comercialList);
        this.producer = chooseRandomElem(productorList);
        this.setTopic(this.getRandomTopic());
    }

    public String getComercial() {
        return this.comercial;
    }

    public String getProducer() {
        return this.producer;
    }

    @Override
    protected Topic getRandomTopic() {
        Random random = new Random();

        TopicType[] topics = { TopicType.Other, TopicType.Football, TopicType.Basket, TopicType.Tennis, TopicType.F1,
                TopicType.Cinema, TopicType.Music, TopicType.OtherCulture };
        return TopicFactory.create(topics[random.nextInt(topics.length)]);
    }

    @Override
    public void prettyPrint() {
        System.out.println("Product: " + this.getName() +
                ", frequency: " + this.getFrequency() +
                ", comercial: " + this.getComercial() +
                ", producer: " + this.getProducer() + ", topic: " + this.getTopic());
    }

}
