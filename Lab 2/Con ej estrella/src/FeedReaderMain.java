import parser.GeneralParser;
import parser.RedditParser;
import parser.RssParser;
import parser.SubscriptionParser;

import subscription.SingleSubscription;
import subscription.Subscription;

import topic.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import feed.Article;
import feed.Feed;
import httpRequest.RedditRequester;
import httpRequest.Requester;
import httpRequest.RssRequester;
import namedEntity.NamedEntity;
import namedEntity.NamedEntityFactory;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;

public class FeedReaderMain {

	private enum HeuristicType {
		Quick,
		Random;

		public static Heuristic getHeuristic(HeuristicType htype) {
			if (htype.equals(HeuristicType.Quick)) {
				return new QuickHeuristic();
			} else {
				return new RandomHeuristic();
			}
		}
	}

	private static void printHelp() {
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}

	public static void main(String[] args) throws Exception {
		System.out.println("************* FeedReader version 1.0 *************");
		if (args.length == 0) {
			/*
			 * Se obtiene las suscripciones del usuario, los artículos de las mismas, y por
			 * ultimo, se muestran por terminal todos los artículos de forma agradable para
			 * el usuario
			 */

			// Leer el archivo de subscription por defecto;
			SubscriptionParser subParser = new SubscriptionParser();
            Subscription sub = subParser.parseSubscriptionJson();

			for (SingleSubscription singleSubscription : sub.getSubscriptionsList()) {

				for (int i = 0; i < singleSubscription.getUlrParamsSize(); i++) {
					// Se obtiene la urlFeed dado el indice de un urlParam
					String urlFeed = singleSubscription.getFeedToRequest(i);

                    String res;
                    GeneralParser parser = null;

					if ("reddit".equals(singleSubscription.getUrlType())){
                        // Llamar al httpRequester para obtener los datos del servidor
                        Requester req = new RedditRequester(urlFeed);
                        res = req.getData();

                        // Llamar al Parser especifico 
                        parser = new RedditParser();

                        // Parseamos el feed
                        parser.parse(res);
                    }
                    else{
                        // Llamar al httpRequester para obtener los datos del servidor
                        Requester req = new RssRequester(urlFeed);
                        res = req.getData();

                        // Llamar al Parser especifico
                        parser = new RssParser();

                        // Parseamos el feed
                        parser.parse(res);
                    }

					// Obtenemos el Feed ya armado
					Feed feed = parser.getFeed();

					// LLamar al prettyPrint del Feed para ver los artículos del feed en forma legible y amigable para el usuario
					feed.prettyPrint();
				}

			}

		} else if (args.length == 1) {
			/*
			 * Se obtiene las suscripciones del usuario, los artículos de las mismas,
			 * las entidades nombradas de los artículos y por ultimo, se muestran en la
			 * terminal en formato de tabla con sus ocurrencias.
			 */

			// Se revisa que el argumento sea el correcto
			if (!"-ne".equals(args[0])) {
				System.out.println("Argumento invalido.");
				printHelp();
				System.exit(1);
			}

			// Se selecciona una de las heruristicas para el programa
			Heuristic heuristic = HeuristicType.getHeuristic(HeuristicType.Quick);

			// Leer el archivo de subscription por defecto;
			SubscriptionParser subParser = new SubscriptionParser();
            Subscription sub = subParser.parseSubscriptionJson();

			for (SingleSubscription singleSubscription : sub.getSubscriptionsList()) {

				for (int i = 0; i < singleSubscription.getUlrParamsSize(); i++) {
					// Se obtiene la urlFeed dado el indice de un urlParam
					String urlFeed = singleSubscription.getFeedToRequest(i);

                    String res;
                    GeneralParser parser = null;

					if ("reddit".equals(singleSubscription.getUrlType())){
                        // Llamar al httpRequester para obtener los datos del servidor
                        Requester req = new RedditRequester(urlFeed);
                        res = req.getData();

                        // Llamar al Parser especifico 
                        parser = new RedditParser();

                        // Parseamos el feed
                        parser.parse(res);
                    }
                    else{
                        // Llamar al httpRequester para obtener los datos del servidor
                        Requester req = new RssRequester(urlFeed);
                        res = req.getData();

                        // Llamar al Parser especifico
                        parser = new RssParser();

                        // Parseamos el feed
                        parser.parse(res);
                    }

					// Obtenemos el Feed ya armado
					Feed feed = parser.getFeed();

					// Mapa para guardar de entidades del Feed
					Map<String, List<NamedEntity>> entityMap = new HashMap<>();

					for (Article article : feed.getArticleList()) {
						// Llamar a la heuristica para que compute las entidades nombradas de cada
						// artículos del feed
						article.computeNamedEntities(heuristic);

						for (NamedEntity namedEntity : article.getNamedEntityList()) {
							String category = namedEntity.getCategory();
							String name = namedEntity.getName();
							int frequency = namedEntity.getFrequency();

							// Se crea un NamedEntityFactory para crear subclases según la categoría
							NamedEntityFactory neFact = new NamedEntityFactory();

							boolean isSaved = false;

							if (entityMap.get(category) == null) {
								entityMap.put(category, new ArrayList<NamedEntity>());
								entityMap.get(category).add(neFact.create(namedEntity));
								continue;
							}

							for (NamedEntity saveNamedEntity : entityMap.get(category)) {
								if (saveNamedEntity.getName().equals(name)) {
									saveNamedEntity.setFrequency(saveNamedEntity.getFrequency() + frequency);
									isSaved = true;
									break;
								}
							}

							if (!isSaved) {
								entityMap.get(category).add(neFact.create(namedEntity));
							}
						}

					}

					// LLamar al prettyPrint de la tabla de entidades nombradas del feed.
					for (Map.Entry<String, List<NamedEntity>> entry : entityMap.entrySet()) {
						for (NamedEntity ne : entry.getValue()) {
							ne.prettyPrint();
						}
					}

					System.out.println("#########################################");
					System.out.println("Para el feed anterior con url '" +     urlFeed	+ "' se obtuvieron los siguientes datos:");
					System.out.println("");

					for (Map.Entry<String, List<NamedEntity>> entry : entityMap.entrySet()) {
						String category = entry.getKey();
						int count = entry.getValue().size();

						System.out.println(
								"Para la categoría '" + category + "' se encontraron " + count + " entidades.");
					}

					System.out.println("#########################################");
				}

				Topic.prettyPrintTopicCounts();
			}

		} else {
			printHelp();
		}
	}

}
