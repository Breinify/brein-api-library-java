```java
// this is the valid api-key
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// this is the URL of the Breinify service
final String serviceEndpoint = "http://dev.breinify.com/api";

// this is one rest engine that can be used internally
final BreinEngineType engineType = BreinEngineType.UNIREST_ENGINE;

// create the configuration object
final BreinConfig breinConfig = new BreinConfig(apiKey,
          serviceEndpoint,
          engineType);

// set the configuration for later usage
Breinify.setConfig(breinConfig);
```