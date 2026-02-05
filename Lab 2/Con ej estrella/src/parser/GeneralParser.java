package parser;

import feed.Feed;

/* Esta clase modela los atributos y métodos comunes a todos los distintos tipos de parser existentes en la aplicación */
public abstract class GeneralParser {

    public abstract void parse(String data);

    public abstract Feed getFeed();

}
