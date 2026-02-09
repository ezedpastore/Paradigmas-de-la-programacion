package namedEntity.heuristics;

public class ParityHeuristic extends Heuristic {

    @Override
    protected boolean isEntity(String word) {
        return (word.length() % 2 == 0);
    }
    
}
