package namedEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Company extends NamedEntity {
    private List<String> typeList = Arrays.asList(
            "Tech", "Retail", "Manufacturing", "Financial",
            "Healthcare", "Education", "Consulting", "Nonprofit");

    private String canonicForm;
    private Integer membersQuantity;
    private String type;

    public Company(String name, String category, int frequency) {
        super(name, category, frequency);
        this.canonicForm = canonicalizeString(name);
        Random rand = new Random();
        this.membersQuantity = rand.nextInt(5000) + 1;
        this.type = chooseRandomElem(typeList);
        this.setTopic(this.getRandomTopic());
    }

    public String getCanonicForm() {
        return this.canonicForm;
    }

    public Integer getMembersQuantity() {
        return this.membersQuantity;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Company: " + this.getName() + ", frequency: " + this.getFrequency() + ", canonic form: "
                + this.getCanonicForm() + ", type: "
                + this.getType() + ", members quantity: " + String.format("%,d", this.getMembersQuantity()) + ", topic: " + this.getTopic());
    }

}
