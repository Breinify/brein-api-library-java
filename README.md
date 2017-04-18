<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify: Leading Temporal AI Engine" width="250">
</p>

# API-Library (JAVA) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.breinify/brein-api-library-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.breinify/brein-api-library-java) 
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
<sup>Features: **Temporal Data**, **(Reverse) Geocoding**, **Events**, **Weather**, **Holidays**, **Analytics**</sup>

This library utilizes [Breinify's API](https://www.breinify.com) to provide tasks like `geocoding`, `reverse geocoding`, `weather and events look up`, `holidays determination` through the API's endpoints, i.e., `/activity` and `/temporaldata`. Each endpoint provides different features, which are explained in the following paragraphs. In addition, this documentation gives detailed examples for each of the features available for the different endpoints.

**Activity Endpoint**: The endpoint is used to understand the usage-patterns and the behavior of a user using, e.g., an application, a mobile app, or a web-browser. The endpoint offers analytics and insights through Breinify's dashboard.

**TemporalData Endpoint**: The endpoint offers features to resolve temporal information like a timestamp, a location (latitude and longitude or free-text), or an IP-address, to temporal information (e.g., timezone, epoch, formatted dates, day-name),  holidays at the specified time and location, city, zip-code, neighborhood, country, or county of the location, events at the specified time and location (e.g., description, size, type), weather at the specified time and location (e.g., description, temperature).

## Getting Started

### Retrieving an API-Key

First of all, you need a valid API-key, which you can get for free at [https://www.breinify.com](https://www.breinify.com). In the examples, we assume you have the following API key:

**938D-3120-64DD-413F-BB55-6573-90CE-473A**

It is recommended to use signed messages when utilizing the Java library. A signed messages ensures, that the request is authorized. To activate signed message ensure that `Verification Signature` is enabled for your key (see [Breinify's API Docs](https://www.breinify.com/documentation) for further information).
In this documentation we assume that the following secret is attached to the API key and used to sign a message.

**utakxp7sm6weo5gvk7cytw==**


### Including the Library

The library is available through [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cbreinify) and can be easily added using:

```pom
<dependency>
  <groupId>com.breinify</groupId>
  <artifactId>brein-api-library-java</artifactId>
  <version>${CURRENT_VERSION}</version>
</dependency>
```

### Configuring the Library

Whenever the library is used, it needs to be configured, i.e., the configuration defines which API key and which secret 
(if signed messages are enabled, i.e., `Verification Signature` is checked) to use.

```java
Breinify.setConfig("938D-3120-64DD-413F-BB55-6573-90CE-473A", 
                   "utakxp7sm6weo5gvk7cytw==");
```

In addition to the API key and the secret, it is also possible to configure the engine used to request the API. By default,
the library tries to auto-detect an available engine on the class-path. If no implementation is found, the library throws
an exception. To manually specify a library, simple use the `setRestEngineType(BreinEngineType)` method. 

```java
final BreinConfig config = new BreinConfig("938D-3120-64DD-413F-BB55-6573-90CE-473A", "utakxp7sm6weo5gvk7cytw==")
                 .setRestEngineType(BreinEngineType.UNIREST_ENGINE);
Breinify.setConfig(config);
```

The library does not add any of the supported engines (i.e., `UniRest` or `Jersey`) to the class-path by default. 
To add the preferred requesting-library one of the following dependencies has to be added.

```pom
<dependency>
    <groupId>com.mashape.unirest</groupId>
    <artifactId>unirest-java</artifactId>
    <version>1.4.9</version>
</dependency>
```

```pom
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-client</artifactId>
    <version>1.19.1</version>
</dependency>
```

### Clean-Up after Usage

Whenever the library is not used anymore, it is recommended to clean-up and release the resources held (e.g., `UniRest` 
utilizes a connection manager, which holds several connections to increase performance). To do so, the `Breinify.shutdown()`
method is used. A typical framework may look like that:

```java
// whenever the application utilizing the library is initialized
Breinify.setConfig("938D-3120-64DD-413F-BB55-6573-90CE-473A", "utakxp7sm6weo5gvk7cytw==");

// whenever the application utilizing the library is destroyed/released
Breinify.shutdown();
```

## Activity: Selected Usage Examples

As mentioned earlier, the library provides method to simple add analytics for the usage of, e.g., an application, an app, or a web-site. There are several libraries available to be used for different system (e.g., [iOS](https://github.com/Breinify/brein-api-library-ios), [Android](https://github.com/Breinify/brein-api-library-android), [Node.js](https://github.com/Breinify/brein-api-library-node), [JavaScript](https://github.com/Breinify/brein-api-library-javascript-browser), [ruby](https://github.com/Breinify/brein-api-library-ruby), [php](https://github.com/Breinify/brein-api-library-php), [python](https://github.com/Breinify/brein-api-library-python)).
Activities are sent to the API whenever something happens within the system, which should be tracked. There are some common activities, which are *login*, *logout*, and *pageVisit*. Depending on the system other events may be tracked as well, e.g., *addToCart*, *checkOut*, or *readArticle*.

The engine is informed of an activity by executing `Breinify.activity(...)`. There are several overloaded versions of the `activity` method, making the usage as easy as possible. The following code-snippets illustrate the simplicity and show how the library can be utilized.

### Sending Login

```Java
Breinify.activity(new M<String>()
        .set("email", "user.login@gmail.com")
        .set("sessionId", "966542c6-2399-11e7-93ae-92361f002671"), "login");
```

### Sending readArticle

Instead of sending an activity utilizing the `Breinify.activity(...)` method, it is also possible to create an instance of a `BreinActivity` and use the `execute(...)` method to send the activity asynchronous. This implementation is typically favored when multiple information are sent with the activity (e.g., tags or descriptions).

```Java
new BreinActivity()
        .setUser("sessionId", "966542c6-2399-11e7-93ae-92361f002671")
        .setActivityType("readArticle")
        .setDescription("A Homebody President Sits Out His Honeymoon Period")
        .setTag("group", "politics")
        .setTag("date", "04/16/2017")
        .setTag("subscription", true)
        .execute();
```

### Send Activities for Funnel Analysis

For some use-cases, e.g., understanding your customers *favorite products*, *average time to checkout*, or *abandon rate*, it is necessary to keep track of your check-out process. The system supports different activities to provide funnel-analytics, i.e.,  *viewedProduct*, *addToCart*, *removeFromCart*, and *checkOut*. Each activity uses the same information to provide a funnel analysis (see the following example). 

```Java
new BreinActivity()
        .setUser("sessionId", "966542c6-2399-11e7-93ae-92361f002671")
        .setActivityType("addToCart")
        .setTag("productPrices", new ArrayList<Double>(3.50, 3.70))                // can be multiple prices
        .setTag("productIds", new ArrayList<String>("sock-AU-90", "sock-GR-92"))   // can be multiple ids
        .setTag("productCategories", new ArrayList<String>("apparel", "apparel"))  // optional
        .execute();
```

## TemporalData: Selected Usage Examples

The `/temporalData` endpoint is used to transform your temporal data into temporal information, i.e., enrich your temporal data with information like 
*current weather*, *upcoming holidays*, *regional and global events*, and *time-zones*, as well as geocoding and reverse geocoding.

### Getting User Information

Sometimes it is necessary to get some more information about the user of an application, e.g., to increase usability and enhance the user experience, 
to handle time-dependent data correctly, to add geo-based services, or increase quality of service. The client's information can be retrieved easily 
by calling the `/temporaldata` endpoint utilizing the `Breinify.temporalData(...)` method or by executing a `BreinTemporalData` instance, i.e.,:

```java
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLocalDateTime()
    .execute();
```

The returned result contains detailed information about the time, the location, the weather, holidays, and events at the time and the location. A detailed
example of the returned values can be found <a target="_blank" href="https://www.breinify.com/documentation">here</a>.

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/sample-user-information.png" alt="Sample output of the user information." width="500"><br/>
  <sup>Output of the Sample Application utilizing some commanly used features.</sup>
</p>

### Geocoding (resolve Free-Text to Locations)

Sometimes it is necessary to resolve a textual representation to a specific geo-location. The textual representation can be
structured and even partly unstructured, e.g., the textual representation `the Big Apple` is considered to be unstructured,
whereby a structured location would be, e.g., `{ city: 'Seattle', state: 'Washington', country: 'USA' }`. It is also possible
to pass in partial information and let the system try to resolve/complete the location, e.g., `{ city: 'New York', country: 'USA' }`.

```
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLocation("The Big Apple")
    .execute();
```

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/sample-geocoding.png" alt="Sample results of textual location requests." width="400"><br/>
  <sup>Formatted output of textual (unstructured) location requests.</sup>
</p>

To receive detailed temporal information from a structured location (e.g., current time, timezone, holidays, weather, or events), have a look at the following snippet: 

```
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLocation("Seattle", "Washington", "USA")
    .execute();
```

### Reverse Geocoding (retrieve GeoJsons for, e.g., Cities, Neighborhoods, or Zip-Codes)

The library also offers the feature of reverse geocoding. Having a specific geo-location and resolving the coordinates
to a specific city or neighborhood (i.e., names of neighborhood, city, state, country, and optionally GeoJson shapes). 

<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/sample-reverse-geocoding.png" alt="Sample results of reverse geocoding requests." width="400"><br/>
  <sup>Formatted output utilizing the result of reverse geocoding requests.</sup>
</p>

### Further links
To understand all the capabilities of Breinify's APIs, have a look at:

* [Sample Application](documentation/sample-app.md),
* [Change Log](documentation/changelog.md), or
* [Breinify's Website](https://www.breinify.com).