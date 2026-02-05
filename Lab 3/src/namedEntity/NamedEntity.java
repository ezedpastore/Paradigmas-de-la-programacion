package namedEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import topic.Topic;
import topic.TopicFactory;

/*Esta clase modela la noción de entidad nombrada*/
public class NamedEntity implements Serializable {
	String name;
	String category;
	int frequency;
	Topic topic;

	public NamedEntity(String name, String category, int frequency) {
		super();
		this.name = name;
		this.category = category == null ? "Other" : category;
		this.frequency = frequency;
		this.topic = TopicFactory.create(TopicFactory.TopicType.Other);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	protected Topic getRandomTopic() {
		return TopicFactory.getRandomTopic();
	}

	public void incFrequency() {
		this.frequency++;
	}

	protected String chooseRandomElem(List<String> list) {
		int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
		String randomElement = list.get(randomIndex);

		return randomElement;
	}

	protected static String canonicalizeString(String s) {
		s = s.trim().toLowerCase();
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	protected LocalDate generateRandomDate(int startYear, int endYear) {
		Random random = new Random();
		int randomYear = random.nextInt(endYear - startYear + 1) + startYear;
		int randomMonth = random.nextInt(12) + 1;
		int randomDay = random.nextInt(28) + 1;

		return LocalDate.of(randomYear, randomMonth, randomDay);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [name=" + name + ", frequency=" + frequency
				+ ", category=" + category + "]";
	}

	public void prettyPrint() {
		System.out.println(this.getName() + " " + this.getFrequency());
	}

}
