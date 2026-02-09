package namedEntity.heuristics;

public class ArticleHeuristic extends Heuristic {

    @Override
    protected boolean isEntity(String word) {
        boolean isEntity;

        if(word.equals("The")){
            isEntity = true;
        }
        else if (word.equals("the")){
            isEntity = true;
        }
        else if (word.equals("A")){
            isEntity = true;
        }
        else if (word.equals("a")){
            isEntity = true;
        }
        else if (word.equals("An")){
            isEntity = true;
        }
        else if (word.equals("an")){
            isEntity = true;
        }
        else{
            isEntity = false;
        }

        return isEntity;
    }
    
}
