package topic;

import java.util.Random;

import topic.culture.Cinema;
import topic.culture.Music;
import topic.culture.OtherCulture;

import topic.politics.International;
import topic.politics.National;
import topic.politics.OtherPolitics;

import topic.sports.Basket;
import topic.sports.F1;
import topic.sports.Football;
import topic.sports.Tennis;

public class TopicFactory {
    public enum TopicType {
        Other,
        Football,
        Basket,
        Tennis,
        F1,
        International,
        National,
        OtherPolitics,
        Cinema,
        Music,
        OtherCulture;
    }

    public static Topic create(TopicType topicType) {
        Topic subtopic;

        switch (topicType) {
            case TopicType.Cinema:
                subtopic = new Cinema();
                break;
            case TopicType.Music:
                subtopic = new Music();
                break;
            case TopicType.OtherCulture:
                subtopic = new OtherCulture();
                break;
            case TopicType.International:
                subtopic = new International();
                break;
            case TopicType.National:
                subtopic = new National();
                break;
            case TopicType.OtherPolitics:
                subtopic = new OtherPolitics();
                break;
            case TopicType.Basket:
                subtopic = new Basket();
                break;
            case TopicType.Tennis:
                subtopic = new Tennis();
                break;
            case TopicType.Football:
                subtopic = new Football();
                break;
            case TopicType.F1:
                subtopic = new F1();
                break;
            default:
                subtopic = new Other();
                break;
        }
        return subtopic;
    }

    public static Topic getRandomTopic() {
        Random random = new Random();

        TopicType[] topics = TopicType.values();
        return create(topics[random.nextInt(topics.length)]);
    }

}