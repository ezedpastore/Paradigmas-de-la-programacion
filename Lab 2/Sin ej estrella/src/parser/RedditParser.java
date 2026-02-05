package parser;

import feed.Feed;

/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implementación
 * */

public class RedditParser extends GeneralParser {

    @Override
    public void parse(String data) {

    }

    @Override
    public Feed getFeed() {
        return new Feed("");
    }

}
