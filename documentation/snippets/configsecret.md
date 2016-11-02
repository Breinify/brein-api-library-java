> ```java
> // Create the configuration object
> final String secret = "time-rift";
> final BreinConfig breinConfig = new BreinConfig(apiKey,
>           serviceEndpoint,
>           engineType).setSecret(secret);
>
> // Set the configuration for later usage
> Breinify.setConfig(breinConfig);
> ```
