package namedEntity;

import java.util.Arrays;
import java.util.List;

public class Other extends NamedEntity {
    private String comment;

    private List<String> commentsList = Arrays.asList(
            "This object needs review",
            "More information required",
            "Related objects pending update",
            "Unable to verify validity",
            "Important comment about this object",
            "Check for possible inconsistencies");

    public Other(String name, String category, int frequency) {
        super(name, category, frequency);
        this.comment = chooseRandomElem(commentsList);
    }

    public String getComment() {
        return this.comment;
    }

    @Override
    public void prettyPrint() {
        System.out.println("Other: " + this.getName() + ", frequency: " + this.getFrequency() + ", comment: "
                + this.getComment() + ", topic: " + this.getTopic());
    }
}
