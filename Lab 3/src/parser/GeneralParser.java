package parser;

import java.io.Serializable;

import feed.Feed;

/* Esta clase modela los atributos y métodos comunes a todos los distintos tipos de parser existentes en la aplicación */
public abstract class GeneralParser implements Serializable {

    public abstract void parse(String data);

    public abstract Feed getFeed();

}
