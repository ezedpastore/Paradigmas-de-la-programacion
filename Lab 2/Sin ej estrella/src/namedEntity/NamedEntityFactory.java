package namedEntity;

import namedEntity.person.Person;
import namedEntity.person.LastName;
import namedEntity.person.Name;
import namedEntity.person.Title;

import namedEntity.place.Place;
import namedEntity.place.City;
import namedEntity.place.Country;
import namedEntity.place.Direction;
import namedEntity.place.OtherPlace;

public class NamedEntityFactory {

    public NamedEntity create(NamedEntity ne) {
        NamedEntity subclass;

        switch (ne.getCategory().toLowerCase()) {
            case "person":
                subclass = new Person(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "name":
                subclass = new Name(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "lastname":
                subclass = new LastName(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "title":
                subclass = new Title(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "place":
                subclass = new Place(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "city":
                subclass = new City(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "country":
                subclass = new Country(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "direction":
                subclass = new Direction(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "otherplace":
                subclass = new OtherPlace(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "company":
                subclass = new Company(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "product":
                subclass = new Product(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "date":
                subclass = new Date(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            case "event":
                subclass = new Event(ne.getName(), ne.getCategory(), ne.getFrequency());
                break;

            default:
                subclass = new Other(ne.getName(), ne.getCategory(), ne.getFrequency());
        }

        return subclass;
    }

}
