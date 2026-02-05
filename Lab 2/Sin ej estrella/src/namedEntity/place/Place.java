package namedEntity.place;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import namedEntity.NamedEntity;

import topic.Topic;
import topic.TopicFactory;
import topic.TopicFactory.TopicType;

public class Place extends NamedEntity {
    protected static List<String> countryList = Arrays.asList("United States", "Canada", "Mexico", "Brazil",
            "Argentina", "United Kingdom", "France", "Germany", "Italy", "Spain",
            "Portugal", "Russia", "China", "Japan", "Australia",
            "New Zealand", "South Africa", "Egypt", "India", "Indonesia",
            "Nigeria", "Saudi Arabia", "Turkey", "Switzerland", "Sweden",
            "Norway", "Denmark", "Netherlands", "Belgium", "Austria",
            "Greece", "Poland", "Ukraine", "Ireland", "Singapore",
            "Malaysia", "Thailand", "Philippines", "Vietnam", "South Korea",
            "Colombia", "Peru", "Chile", "Venezuela", "Morocco",
            "Algeria", "Kenya", "Ethiopia", "Ghana", "Angola");

    protected static List<String> cityList = Arrays.asList("New York City", "Los Angeles", "Chicago", "Houston",
            "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose", "London", "Paris", "Berlin",
            "Rome", "Madrid", "Tokyo", "Beijing", "Shanghai", "Mumbai", "Delhi", "Cairo", "Johannesburg", "Sydney",
            "Melbourne", "Toronto", "Montreal", "Buenos Aires", "Córdoba", "Rio de Janeiro", "São Paulo", "Moscow",
            "Saint Petersburg", "Istanbul", "Ankara", "Baghdad", "Tehran", "Jakarta", "Bangkok", "Seoul", "Mexico City",
            "Lima", "Santiago", "Bogotá", "Caracas", "Kuala Lumpur", "Singapore", "Hong Kong", "Amsterdam", "Brussels",
            "Zurich");

    protected static List<String> capitalList = Arrays.asList("Washington D.C.", "Ottawa", "Mexico City", "Brasília",
            "Buenos Aires", "London", "Paris", "Berlin", "Rome", "Madrid",
            "Lisbon", "Moscow", "Beijing", "Tokyo", "Canberra",
            "Wellington", "Pretoria", "Cairo", "New Delhi", "Jakarta",
            "Abuja", "Riyadh", "Ankara", "Bern", "Stockholm",
            "Oslo", "Copenhagen", "Amsterdam", "Brussels", "Vienna",
            "Athens", "Warsaw", "Kyiv", "Dublin", "Singapore",
            "Kuala Lumpur", "Bangkok", "Manila", "Hanoi", "Seoul",
            "Bogotá", "Lima", "Santiago", "Caracas", "Rabat",
            "Algiers", "Nairobi", "Addis Ababa", "Accra", "Luanda");

    protected static List<String> officialLanguageList = Arrays.asList("English", "Spanish", "French", "German",
            "Italian", "Portuguese", "Russian", "Chinese", "Japanese", "Arabic", "Hindi",
            "Bengali", "Swahili", "Malay", "Thai", "Vietnamese", "Korean", "Dutch", "Swedish", "Norwegian", "Basque",
            "Catalan", "Estonian");

    public Place(String name, String category, int frequency) {
        super(name, category, frequency);
    }

    protected static int getRandomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    protected static String getRandomFormListString(List<String> list) {
        Random randomGenerator = new Random();

        int index = randomGenerator.nextInt(list.size());
        String item = list.get(index);
        return item;
    }

    @Override
    protected Topic getRandomTopic() {
        Random random = new Random();

        TopicType[] topics = { TopicType.International, TopicType.National, TopicType.OtherPolitics };
        return TopicFactory.create(topics[random.nextInt(topics.length)]);
    }

    @Override
    public void prettyPrint() {
        System.out.println("Place: " + this.getName() + ", frequency: " + this.getFrequency());
    }
}
