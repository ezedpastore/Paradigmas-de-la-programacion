# 📰 Feed Reader (with big data) - Lector de Feeds con procesamiento de grandes
volúmenes de datos en Java

## 🧑‍💻 Grupo 12 - NaN: Not a Name - UNC - Paradigmas - 2025

*Integrantes:*
- Canovas, José Lorenzo
- Garione, Facundo Gabriel
- Pastore, Ezequiel David

---

## Conceptos importantes
1. **Describa el flujo de la aplicación** ¿Qué pasos sigue la aplicación desde
la lectura del archivo subscriptions.json hasta la obtención de las entidades
nombradas? ¿Cómo se reparten las tareas entre los distintos componentes del
programa?

    1. Se crea la **session de Spark**.
    2. Se crea un **contexto de Spark**.
    3. Se utiliza la clase `SubscriptionParser` para leer el archivo
    `subscriptions.json` y crear objeto `Subscription`, donde este ultimo
    almacena todas las suscripciones (`SingleSubscription`).
    4. Se recorre los *singleSubscriptions* y se almacena las URLs de sus feeds
    en conjunto con su tipo de formato a utilizar en el parser.
    5. Se utiliza el contexto de Spark para paralelizar y repartir porciones de
    URLs a los **Workers** para lo siguientes pasos.
    6. Se mapea cada URL a su correspondiente `Feed`, utilizando la clase
    `httpRequester` para obtenerlo y `SubscriptionParser` para parsearlo.
    7. Se agrupan todos los feeds de las suscripciones mediante collect en
    **Master**.
    8. Si el usuario no pasa como argumento `-ne` se muestran los Feeds y
    termina el programa, caso contrario se continua.
    9. Se selecciona una heuristica para luego obtener las entidades nombradas.
    10. Se obtienen todos los artículos de todos los feeds y utilizando el
    contexto de Spark se paraleliza los siguientes pasos para los **Workers**.
    11. Se procesan los artículos.
    12. Se crean las subclases respectivas para cada entidades nombrada.
    13. Se realiza la reducción de las entidades nombradas para tener el conteo
    de la frecuencia de estas a lo largo de todos los artículos.
    14. Se agrupan todos las entidades nombradas mediante collect en **Master**.
    15. Se imprime por pantalla de forma amigable para el usuario los datos de
    todos las entidades nombradas.
    16. Se cierra el contexto y la session con Spark.


2. **¿Por qué se decide usar Apache Spark para este proyecto?** ¿Qué necesidad
concreta del problema resuelve?

    > Se decide usar Apache Spark para poder trabajar con grandes volúmenes de
    feeds en el menor tiempo posible. Gracias a que varias tareas pesadas son
    independientes y se pueden realizar de forma paralela, como por ejemplo:
    **la obtención de los feeds mediante peticiones HTTP y su respectivo parseo**;
    **el procesamiento de los artículos y el conteo de las entidades nombradas**.

3. **Liste las principales ventajas y desventajas que encontró al utilizar
Spark.**

    Ventajas:
    - Abstracción.
    - Velocidad de procesamiento.
    - Rendimiento (mejor de lo que posiblemente pudiéramos lograr solos).
    - Escalabilidad (local/cluster).
    - APIs sencillas.
    - Procesamiento paralelo.

    Desventajas
    - Uso de memoria elevado.
    - Curva de aprendizaje (ya que es un framework).
    - Poca eficacia para pequeños volúmenes de datos.

4. **¿Cómo se aplica el concepto de inversión de control en este laboratorio?**
Explique cómo y dónde se delega el control del flujo de ejecución. ¿Qué
componentes deja de controlar el desarrollador directamente?

    Al utilizar Spark estamos cediendo el control de la ejecución de ciertas
    tareas, las cuales serian difíciles de implementar (y muy laborioso).

    Esta inversion de control se realiza en:
    1. La obtención de los feeds mediante peticiones HTTP y su respectivo parseo
    2. El procesamiento de los artículos y el conteo de las entidades nombradas

    Los componentes que se dejan de controlar directamente son:
    1. Los datos.
    2. La partición de los datos.
    3. Planificación de tareas.
    4. Gestión del paralelismo.
    5. Asignación de recursos.
    6. Manejo de errores.

5. **¿Considera que Spark requiere que el código original tenga una integración
tight vs loose coupling?**

    > Consideramos que Spark favorece una integración `loose coupling`, ya que
    uno se beneficia mucho de que los componentes de la aplicación estén
    modularizados, para que puedan evolucionar o reutilizarse de forma
    independiente.

6. **¿El uso de Spark afectó la estructura de su código original?** ¿Tuvieron
que modificar significativamente clases, métodos o lógica de ejecución del
laboratorio 2?

    > No, se modifico casi nada del código original. Quitando la modificación
    del main, se extendieron varias subclases con la clase **Serializable** y
    corregimos un warning en cierta parte del código por el cambio de version
    de JAVA 24 a JAVA 11.


## 📌 Descripción

Este proyecto es una extensión del lector de feeds RSS desarrollado en el
laboratorio 2, adaptado para trabajar con grandes volúmenes de datos utilizando
el framework Apache Spark. La aplicación permite leer múltiples fuentes RSS
configuradas en un archivo subscriptions.json, descargar sus artículos y
realizar un análisis de texto para extraer entidades nombradas como personas,
lugares, organizaciones, productos, fechas, entre otras.

El objetivo principal es escalar el procesamiento de estos datos para que sea
eficiente incluso cuando se incrementa significativamente la cantidad de feeds y
artículos procesados. Spark nos permite ejecutar las tareas en paralelo, tanto
en la etapa de descarga como en la de análisis de entidades.

Este enfoque no solo mejora el rendimiento, sino que también sienta las bases
para aplicar técnicas más complejas.

El diseño modular y extensible de la aplicación permite incorporar fácilmente
nuevos parsers de feeds, heurísticas de detección de entidades o funcionalidades
adicionales, manteniendo una estructura clara y desacoplada.

---

## 🧠 Decisiones de diseño

Además de las decisiones tomadas en el laboratorio 2, se tomaron algunas
adicionales en este nuevo laboratorio:

- Casi todas las clases involucradas en la aplicación, fueron modificadas para
que extiendan de la clase Serializable (io.Serializable).
- Se elimina código repetido para la lectura, obtención y parseo de feeds.
- No se consideró necesaria la creación de nuevas clases o métodos.
---

## ⚙️ Instalación y ejecución

1.  Asegúrate de tener instalado el JDK (Java Development Kit) y el framework
Apache Spark.Puedes instalar jdk con:

    ```bash
    sudo apt install openjdk-11-jdk
    ```

    Y Apache Spark siguiendo los pasos de [este video](https://youtu.be/2iU71eYkFkU?si=9-zY_ejUMN3qdtf9)

2.  Clona este repositorio:

    ```bash
    git clone https://github.com/paradigmas25-g12/paradigmas25-g12-lab3.git
    cd paradigmas25-g12-lab3
    ```
3.  Compila el código Java mediante Makefile.
    -  Cambiar las variables `JAVA_PATH` y `SPARK_FOLDER` según su configuración.

4.  Ejecuta la aplicación.
    - Hay dos funciones de la aplicación distintas, con `make` se obtienen los
    feeds y se muestran por pantalla, mientras que con `make runHeuristic` se
    computan las entidades nombradas para todos los artículos de todos los feeds
    y se muestran por pantalla.

---

## ✨ Funcionalidades
- 🔄 Lectura de Feeds RSS: Permite procesar múltiples feeds desde distintas
URLs especificadas en un archivo de suscripción.
- 🧠 Reconocimiento de Entidades Nombradas: Extrae entidades nombradas (como
personas, lugares, organizaciones, etc.) de los artículos mediante heurísticas
que son configurables.
- 📄Impresión legible de resultados: Tanto los artículos como las entidades
nombradas se muestran en consola con formato amigable.
- ⚡ Procesamiento distribuido con el framework Apache Spark: Utiliza el Spark
para realizar tareas de manera paralela, acelerando la obtención y análisis de
datos y permitiendo trabajar con grandes cantidades de datos.
- 📦 Extensible: Diseño modular que facilita agregar nuevas heurísticas,
parsers, y tipos de feeds.
-----

## 🗂️ Estructura del Proyecto
```
.
├── README.md
├── Makefile
├── config
│   └── subscriptions.json
├── lib
│   └── json-20250107.jar
└── src
    ├── FeedReaderMain.java
    ├── feed
    │   ├── Article.java
    │   └── Feed.java
    ├── httpRequest
    │   └── httpRequester.java
    ├── namedEntity
    │   ├── Company.java
    │   ├── Date.java
    │   ├── Event.java
    │   ├── NamedEntity.java
    │   ├── NamedEntityFactory.java
    │   ├── Other.java
    │   ├── Product.java
    │   ├── heuristic
    │   │   ├── Heuristic.java
    │   │   ├── QuickHeuristic.java
    │   │   └── RandomHeuristic.java
    │   ├── person
    │   │   ├── LastName.java
    │   │   ├── Name.java
    │   │   ├── Person.java
    │   │   └── Title.java
    │   └── place
    │       ├── City.java
    │       ├── Country.java
    │       ├── Direction.java
    │       ├── OtherPlace.java
    │       └── Place.java
    ├── parser
    │   ├── GeneralParser.java
    │   ├── RedditParser.java
    │   ├── RssParser.java
    │   └── SubscriptionParser.java
    ├── subscription
    │   ├── Counter.java
    │   ├── SingleSubscription.java
    │   └── Subscription.java
    └── topic
        ├── Other.java
        ├── Topic.java
        ├── TopicFactory.java
        ├── culture
        │   ├── Cinema.java
        │   ├── Culture.java
        │   ├── Music.java
        │   └── OtherCulture.java
        ├── politics
        │   ├── International.java
        │   ├── National.java
        │   ├── OtherPolitics.java
        │   └── Politics.java
        └── sports
            ├── Basket.java
            ├── F1.java
            ├── Football.java
            ├── Sports.java
            └── Tennis.java
```
-----

## 👥 Créditos

Trabajo realizado para la materia **"Paradigmas de la Programación" - FAMAF -
UNC** 🏛️
