package namedEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date extends NamedEntity {
    private LocalDate preciseDate;
    private String canonicForm;

    public Date(String name, String category, int frequency) {
        super(name, category, frequency);
        this.preciseDate = generateRandomDate(2000, 2025);
        this.canonicForm = preciseDate.format(DateTimeFormatter.ISO_DATE);
        this.setTopic(this.getRandomTopic());
    }

    public LocalDate getPreciseDate() {
        return this.preciseDate;
    }

    public String getCanonicForm() {
        return this.canonicForm;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Date: " + this.getName() +
                ", frequency: " + this.getFrequency() +
                ", precise date: " + this.getPreciseDate().format(DateTimeFormatter.ISO_DATE) +
                ", canonic form: " + this.getCanonicForm() + ", topic: " + this.getTopic());
    }

}
