
>
```java
// Install via maven dependency
// add the following section to your pom.xml file and update your dependencies
    <dependency>
      <groupId>com.breinify</groupId>
      <artifactId>brein-api-library-java</artifactId>
      <version>1.2.0</version>
    </dependency>
```


> ```java
> // This is the valid api-key
> final String apiKey = "time-is-ticking";
>
> // This is the URL of the Breinify service
> final String serviceEndpoint = "https://api.breinify.com";
>
> // This is one rest engine that can be used internally
> final BreinEngineType engineType = BreinEngineType.JERSEY_ENGINE;
>
> // Create the configuration object
> final BreinConfig breinConfig = new BreinConfig(apiKey,
>           serviceEndpoint,
>           engineType);
>
> // Set the configuration for later usage
> Breinify.setConfig(breinConfig);
> ```
