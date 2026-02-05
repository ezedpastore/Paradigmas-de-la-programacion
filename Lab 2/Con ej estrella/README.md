# 📰 Feed Reader - Lector de Feeds en Java

## 🧑‍💻 Grupo 12 - NaN: Not a Name - UNC - Paradigmas - 2025

*Integrantes:*
- Canovas, José Lorenzo
- Garione, Facundo Gabriel
- Pastore, Ezequiel David

---

## 📌 Descripción

Este proyecto es un lector de feeds desarrollado como parte de un laboratorio de
la materia Paradigmas. La aplicación, construida en Java bajo el paradigma
orientado a objetos, permite a los usuarios consumir y visualizar feeds de
diversas fuentes.

El lector obtiene las URLs de los feeds desde un archivo de configuración en
formato JSON, y muestra el contenido de los artículos de manera legible.
Además, incluye una funcionalidad para computar las entidades nombradas más
frecuentes en los feeds, utilizando heurísticas.

---

## 🧠 Decisiones de diseño

A continuación se detallan las principales decisiones de diseño tomadas durante
el desarrollo del lector de feeds. Estas decisiones apuntan a una solución
modular, extensible y alineada con los principios de la programación orientada
a objetos.

### Manejo de dependencias externas

* Se optó por **gestionar manualmente la librería externa `json.org`**,
alejándola en el directorio `lib`. Esta librería fue utilizada para parsear el
archivo de configuración en formato JSON.

### Separación de responsabilidades en el parseo

* La clase **`SubscriptionParser`** ya no extiende de `GeneralParser` y se
responsabiliza exclusivamente de:

  * Leer y validar el archivo `subscriptions.json`.
  * Crear y validar las instancias de `SingleSubscription`.
  * Determinar qué parser concreto utilizar (por ejemplo, `RssParser`,
  `RedditParser`) en función de la URL.

### Utilización de enumerables

* En `SubscriptionParser` para facilitar la validación y la selección de
parsers.
* El cambio de heurística se realiza fácilmente gracias al uso de **tipos
enumerados (`enum`)** específicos para estas.
* Para la creación de subclases de `Topic` en `TopicFactory`, para evitar
errores de escritura (se evita escribir un string con el nombre de la subclase).
* Utilizar enumerables nos permite seleccionar comportamientos con bajo
acoplamiento.

### Modelado de entidades nombradas

* Se implementó una jerarquía de clases que sigue el diseño propuesto por la
cátedra. Cada tipo de entidad nombrada es representado como una **subclase
directa** de `NamedEntity`, con atributos específicos. Por ejemplo:

  * `Messi` es una instancia de `LastName`, no de `Person`.
  * `Argentina` sería una instancia de `Country`, no de `Place`.

* Lo mismo para `Topic`.

### Inicialización y aleatorización de datos

* Algunos atributos de las clases, como nombres, países o ciudades, se asignan
de forma **aleatoria desde listas predefinidas**, simulando una base de
conocimiento heurística para enriquecer los resultados sin requerir acceso a
datos externos.

### Temas como un atributo en NamedEntity

* Se incorporó un atributo `Topic` a las entidades nombradas. Este modelo
representa la jerarquía temática (deportes, cultura, política, etc.) subclases
de `Topic`.

### Impresión de Entidades Nombradas en la Terminal

* Se muestran solo aquellas entidades que están presentes en **todos los
artículos** de un feed específico.

* Para cada entidad nombrada, se utiliza el siguiente formato:

      [Subclase]: [Nombre], frecuencia: [Frecuencia], [...Atributos], tema: [Tema]

  Donde:
  * **[Subclase]** es la subclase de `NamedEntity` a la que pertenece la entidad
  (por ejemplo, `LastName`, `Country`).
  * **[Nombre]** es el nombre de la entidad (por ejemplo, `Messi`, `Córdoba`,
  `Friday`).
  * **[Frecuencia]** indica la cantidad de veces que se encontró el nombre en
  todos los artículos del feed.
  * **[...Atributos]** son las propiedades específicas de cada subclase (por
  ejemplo, `comment`, `origin`, `profession`).
  * **[Tema]** representa la relación temática de la entidad (por ejemplo,
  `Deportes`, `Cultura`).

* Luego de la información individual de cada entidad, se incluye un resumen al
finalizar la impresión, mostrando el **total de entidades por cada categoría de
tema**.

* Al finalizar la impresión de todos los feeds, se imprime la cantidad total de
Topics (global).

---

## ⚙️  Instalación

1.  Asegúrate de tener instalado el JDK (Java Development Kit). Puedes
instalarlo con:

    ```bash
    sudo apt install openjdk-24-jdk
    ```
2.  Clona este repositorio:

    ```bash
    git clone https://github.com/paradigmas25-g12/paradigmas25-g12-lab2.git
    cd paradigmas25-g12-lab2
    ```
3.  Compila el código Java.
4.  Ejecuta la aplicación.

---

## ▶️  Ejecución

1.  Navega al directorio del proyecto.
2.  Compila los archivos Java.
3.  Ejecuta el programa principal:

    ```bash
    java FeedReader
    ```

Para ejecutar el programa con la funcionalidad de análisis de entidades
nombradas, utiliza el parámetro `-ne`:

```bash
java FeedReader -ne
````

-----

## ✨ Funcionalidades

  * **Lectura de Feeds:** La aplicación consume feeds de diferentes sitios y
  tópicos, definidos en un archivo JSON.
  * **Parseo de JSON y XML:** Se utilizan librerías para parsear tanto el
  archivo de configuración JSON como los feeds XML.
  * **Visualización Legible:** Los artículos de los feeds se muestran de forma
  clara y amigable para el usuario.
  * **Análisis de Entidades Nombradas:** Opcionalmente, la aplicación puede
  computar y mostrar las entidades nombradas en los feeds, junto con su
  frecuencia y otra información propia de la subclase.
  * **Heurísticas:** Se pueden utilizar diferentes heurísticas para la detección
  de entidades nombradas (QuickHeuristic, RandomHeuristic).

-----

## 🗂️ Estructura del Proyecto

```
.
├── config
│   └── subscriptions.json
├── lib
│   └── json-20250107.jar
├── README.md
└── src
    ├── feed
    │   ├── Article.java
    │   └── Feed.java
    ├── FeedReaderMain.java
    ├── httpRequest
    │   └── httpRequester.java
    ├── namedEntity
    │   ├── Company.java
    │   ├── Date.java
    │   ├── Event.java
    │   ├── heuristic
    │   │   ├── Heuristic.java
    │   │   ├── QuickHeuristic.java
    │   │   └── RandomHeuristic.java
    │   ├── NamedEntityFactory.java
    │   ├── NamedEntity.java
    │   ├── Other.java
    │   ├── person
    │   │   ├── LastName.java
    │   │   ├── Name.java
    │   │   ├── Person.java
    │   │   └── Title.java
    │   ├── place
    │   │   ├── City.java
    │   │   ├── Country.java
    │   │   ├── Direction.java
    │   │   ├── OtherPlace.java
    │   │   └── Place.java
    │   └── Product.java
    ├── parser
    │   ├── GeneralParser.java
    │   ├── RedditParser.java
    │   ├── RssParser.java
    │   └── SubscriptionParser.java
    ├── subscription
    │   ├── Counter.java
    │   ├── SingleSubscription.java
    │   └── Subscription.java
    └── topic
        ├── culture
        │   ├── Cinema.java
        │   ├── Culture.java
        │   ├── Music.java
        │   └── OtherCulture.java
        ├── Other.java
        ├── politics
        │   ├── International.java
        │   ├── National.java
        │   ├── OtherPolitics.java
        │   └── Politics.java
        ├── sports
        │   ├── Basket.java
        │   ├── F1.java
        │   ├── Football.java
        │   ├── Sports.java
        │   └── Tennis.java
        ├── TopicFactory.java
        └── Topic.java
```

-----

## 👥 Créditos

Trabajo realizado para la materia **"Paradigmas de la Programación" - FAMAF -
UNC** 🏛️
