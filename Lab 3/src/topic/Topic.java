package topic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import subscription.Counter;

public class Topic implements Serializable {
	protected static Map<String, Counter> counters = new HashMap<>();

	public Topic() {
		super();

		String className = this.getClass().getSimpleName();
		if (counters.get(className) == null) {
			counters.put(className, new Counter());
		}
		counters.get(className).increment();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public void prettyPrint() {
		System.out.println(this.getClass().getSimpleName());
	}

	public static void prettyPrintTopicCounts() {
		System.out.println();
		System.out.println("Se encontraron todos los siguientes temas en todos los feeds:");

		for (Map.Entry<String, Counter> entry : counters.entrySet()) {
			String topicClass = entry.getKey();
			int count = entry.getValue().getValue();

			System.out.println("* " + topicClass + " " + count);
		}
	}
}
