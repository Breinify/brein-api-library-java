<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify API Java Library" width="250">
</p>

<p align="center">
Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips.
</p>

### Additional Code Snippet

The following code snippets provides additional information how to use the *breinify-api-java* library.

#### BreinExecutor
Instead of using Breinify class methods you could also use the BreinExecutor class in order to invoke activity or lookup calls.

The following example will create a user and a configuration for Breinify and will invoke the activity and lookup calls.

```java
// valid api-key
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// create a user
final BreinUser breinUser = new BreinUser("user.anywhere@email.com");

// configure and build the Breinify Executor
final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(apiKey)
                .setBaseUrl("https://api.breinify.com")
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

// invoke an activity call
breinifyExecutor.activity(breinUser,
                BreinActivityType.LOGIN,
                BreinCategoryType.FOOD,
                "This is a description",
                false);
                
// prepare the lookup part

// define an array of subjects of interest
final String[] dimensions = {"firstname",
       "gender",
       "age",
       "agegroup",
       "digitalfootprint",
       "images"};

// wrap this into BreinDimension
final BreinDimension breinDimension = new BreinDimension(dimensions);

// invoke the lookup
final BreinResult result = breinifyExecutor.lookup(breinUser,
                breinDimension, false);

// retrieve possible values
final Object dataFirstname = result.get("firstname");
final Object dataGender = result.get("gender");
final Object dataAge = result.get("age");
final Object dataAgeGroup = result.get("agegroup");
final Object dataDigitalFootprinting = result.get("digitalfootprint");
final Object dataImages = result.get("images");
```

#### BreinUser
BreinUser provides some methods to add further data. This example shows all possible option. 


````
// create a user with mandatory email
BreinUser breinUser = new BreinUser("user.anywhere@email.com");

// set further data 
breinUser.setFirstName("User")
         .setLastName("Anyhere")
         .setImei("356938035643809")
         .setDateOfBirth(6, 20, 1985)
         .setDeviceId("AAAAAAAAA-BBBB-CCCC-1111-222222220000")
         .setSessionId("SID:ANON:w3.org:j6oAOxCWZ");

// some output
System.out.println(breinUser.toString());
````

#### Exception

The Breinify-Java API provides two exceptions. These are:

1. BreinException
2. BreinInvalidConfigurationException

 
BreiException will be thrown for instance when an activity call fails. BreinInvalidConfigurationException will only be thrown in case of an invalid BreinConfig. This is the case when a wrong URL is configured.

 
#### Rest-Engine
The Breinify Java API provides two possible rest engines. These are UNIREST and JERSEY. Within the configuration you can select the appropriate rest engine.



This is snippet shows how to use the UNIREST engine:

```java
// valid api-key
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// create the configuration object with UNIREST engine 
final BreinConfig breinConfig = new BreinConfig(apiKey,
      "https://api.breinify.com",
      BreinEngineType.UNIREST_ENGINE);

// set the configuration for later usage
Breinify.setConfig(breinConfig);

```

In case you would like to use the Jersey implementation simple change the engine type within the configuration:

```java
// valid api-key
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// create the configuration object with Jersey engine 
final BreinConfig breinConfig = new BreinConfig(apiKey,
      "https://api.breinify.com",
      BreinEngineType.JERSEY_ENGINE);

// set the configuration for later usage
Breinify.setConfig(breinConfig);

```

#### Activity Request with comprehensive data

The following sample provides an overview how to configure all fields for an activity request:

```
public void testPageVisit() {

final String BASE_URL = " https://api.breinify.com";
final String VALID_API_KEY = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// set configuration
final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
          BASE_URL,
          BreinEngineType.UNIREST_ENGINE);
Breinify.setConfig(breinConfig);

// user data
final BreinUser breinUser = new BreinUser("user.anywhere@email.com")
         .setFirstName("User")
         .setLastName("Anyhere")
         .setImei("356938035643809")
         .setDateOfBirth(6, 20, 1985)
         .setDeviceId("AAAAAAAAA-BBBB-CCCC-1111-222222220000");

final Map<String, String> tagMap = new HashMap<>();
tagMap.put("t1", "0.0");
tagMap.put("t2", "0.0");
tagMap.put("t3", "0.0");
tagMap.put("t4", "0.0");
tagMap.put("nr", "1.0");
tagMap.put("sortid", "1.0");

// class Breinify provides an instance of BreinActivity
final BreinActivity breinActivity = Breinify.getBreinActivity();

breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
breinActivity.setBreinUser(breinUser);
breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
breinActivity.setDescription("your description");
breinActivity.setSign(false);
breinActivity.setTagsMap(tagMap);
breinActivity.setIpAddress("11.222.333.444");
breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
breinActivity.setAdditionalUrl("https://sample.com.au/home");
breinActivity.setReferrer("https://sample.com.au");
breinActivity.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

Breinify.activity(breinActivity);
}
```

