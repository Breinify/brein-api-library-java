>
```java
// Install via maven dependency
// add the following section to your pom.xml file and update your dependencies
    <dependency>
      <groupId>com.breinify</groupId>
      <artifactId>brein-api-library-java</artifactId>
      <version>2.1.0</version>
    </dependency>
```

> 
```java
// This is the valid api-key
final String apiKey = "time-is-ticking";
>

// Create the configuration object
final BreinConfig breinConfig = new BreinConfig(apiKey);
>
// Set the configuration for later usage
Breinify.setConfig(breinConfig);
```
