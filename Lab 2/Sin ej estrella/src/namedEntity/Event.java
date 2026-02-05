package namedEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Event extends NamedEntity {
    private String canonicForm;
    private LocalDate eventDate;
    private boolean isRecurrent;

    public Event(String name, String category, int frequency) {
        super(name, category, frequency);
        this.eventDate = generateRandomDate(2000, 2025);
        this.canonicForm = "Event on " + eventDate.format(DateTimeFormatter.ISO_DATE);
        this.isRecurrent = new Random().nextBoolean();
        this.setTopic(this.getRandomTopic());
    }

    public LocalDate getEventDate() {
        return this.eventDate;
    }

    public String getCanonicForm() {
        return this.canonicForm;
    }

    public boolean isRecurrent() {
        return this.isRecurrent;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Event: " + this.getName() +
                ", frequency: " + this.getFrequency() +
                ", date: " + this.getEventDate().format(DateTimeFormatter.ISO_DATE) +
                ", canonic form: " + this.getCanonicForm() +
                ", recurrent: " + (this.isRecurrent() ? "Yes" : "No") + ", topic: " + this.getTopic());
    }

}
