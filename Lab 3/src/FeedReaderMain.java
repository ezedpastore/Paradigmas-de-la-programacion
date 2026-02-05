import parser.GeneralParser;
import parser.SubscriptionParser;
import scala.Tuple2;
import subscription.SingleSubscription;
import subscription.Subscription;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import feed.Article;
import feed.Feed;

import httpRequest.httpRequester;

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
		// Crea una sesión de Spark en modo local (usa todos los núcleos de la máquina)
		SparkSession spark = SparkSession
				.builder()
				.appName("FeedReader")
				.master("local[*]") // Ejecuta en modo local usando todos los núcleos
				.config("spark.log.level", "FATAL")
				.getOrCreate();

		JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

		/*
		 * Se obtiene las suscripciones del usuario, se realiza las peticiones http
		 * en paralelo para obtener todos los feeds
		 */
		List<Tuple2<String, String>> urlFeedsInfo = new ArrayList<>();

		// Leer el archivo de subscription por defecto;
		Subscription sub = SubscriptionParser.getSubscription();
		for (SingleSubscription singleSubscription : sub.getSubscriptionsList()) {
			for (int i = 0; i < singleSubscription.getUlrParamsSize(); i++) {
				// Se obtiene la urlFeed dado el indice de un urlParam
				String urlFeed = singleSubscription.getFeedToRequest(i);
				urlFeedsInfo.add(new Tuple2<>(urlFeed, singleSubscription.getUrlType()));
			}
		}

		JavaRDD<Tuple2<String, String>> urlFeedInfoRDD = jsc.parallelize(urlFeedsInfo);

		JavaRDD<Feed> feedsRDD = urlFeedInfoRDD.map(
				(Tuple2<String, String> urlInfo) -> {
					httpRequester req = new httpRequester(urlInfo._1);
					String res = req.getData(urlInfo._2);

					GeneralParser parser = SubscriptionParser.getParser(urlInfo._2);
					parser.parse(res);

					return parser.getFeed();
				});

		List<Feed> allFeeds = feedsRDD.collect();

		System.out.println("************* FeedReader version 1.0 *************");
		if (args.length == 0) {
			// Mostrar los feeds recopilados
			for (Feed feed : allFeeds) {
				feed.prettyPrint();
			}

		} else if (args.length == 1) {
			/*
			 * Se juntan todos los artículos de todos los feeds y se los reparte a los
			 * workers para que estos últimos procesen los artículos y realicen el
			 * conteo de entidades nombradas.
			 */

			// Se revisa que el argumento sea el correcto.
			if (!"-ne".equals(args[0])) {
				System.out.println("Argumento invalido.");
				printHelp();
				System.exit(1);
			}

			// Se selecciona una de las heruristicas para el programa.
			Heuristic heuristic = HeuristicType.getHeuristic(HeuristicType.Quick);

			// Se crea una lista para almacenar todos los artículos.
			List<Article> allArticles = new ArrayList<>();

			// Itera sobre todas los feeds y combina todos los artículos en allArticles.
			for (Feed feed : allFeeds) {
				allArticles.addAll(feed.getArticleList());
			}

			// Se reparten porciones de artículos a cada Worker.
			JavaRDD<Article> allArticlesRDD = jsc.parallelize(allArticles);

			// Los Workers computan la porción de artículos obtenidos.
			JavaRDD<Article> processedArticleRDD = allArticlesRDD.map(
					(Article article) -> {
						article.computeNamedEntities(heuristic);

						return article;
					});

			// Extrae todas las entidades nombradas de los artículos procesados.
			/*
			 * flatMap se usa para aplanar las listas de entidades nombradas de cada
			 * artículo en un solo RDD de NamedEntity.
			 *
			 * Funciona conceptualmente similar a la combinación de los artículos
			 * en la variable allArticles, esto por cada Worker (no se junta en Master
			 * ya que se esta haciendo de forma distribuida), y a partir de ahora es
			 * como si estuviésemos trabajando cada namedEntity dentro de un bucle en
			 * todos los Workers.
			 */
			JavaRDD<NamedEntity> namedEntitiesRDD = processedArticleRDD.flatMap(
					article -> article.getNamedEntityList().iterator());

			NamedEntityFactory nef = new NamedEntityFactory();

			// Convierte el RDD de entidades nombradas en un PairRDD.
			// Cada elemento es una tupla donde la clave es el nombre de la entidad y el
			// valor es la entidad misma pasada por el NamedEntityFactory.
			JavaPairRDD<String, NamedEntity> namedEntityPairsRDD = namedEntitiesRDD.mapToPair(
					ne -> new Tuple2<>(ne.getName(), nef.create(ne)));

			// Combina entidades con el mismo nombre, sumando su frecuencia.
			JavaPairRDD<String, NamedEntity> reducedNamedEntitiesRDD = namedEntityPairsRDD.reduceByKey(
					(NamedEntity ne1, NamedEntity ne2) -> {
						NamedEntity ne = ne1;

						// Si el tema de la primera entidad es "Other", usa la segunda entidad.
						// Es para priorizar entidades con temas más específicos (tienen mas
						// datos para mostrar).
						if (ne1.getTopic() instanceof topic.Other) {
							ne = ne2;
						}

						// Suma las frecuencias de ambas entidades para obtener la frecuencia total
						// combinada.
						ne.setFrequency(ne1.getFrequency() + ne2.getFrequency());

						return ne;
					});

			// Recopila los resultados finales del RDD reducido en una lista en Master
			// Esta contiene las entidades nombradas únicas de todos los artículos de todos
			// los feeds
			List<Tuple2<String, NamedEntity>> finalCounts = reducedNamedEntitiesRDD.collect();

			// Itera sobre la lista de entidades nombradas y lo imprime de manera
			// legible
			for (Tuple2<String, NamedEntity> tp : finalCounts) {
				tp._2().prettyPrint();
			}

		} else {
			printHelp();
		}

		jsc.close();
		spark.stop();
	}

}
