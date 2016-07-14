<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify API JavaScript Library" width="250">
</p>

<p align="center">
Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips.
</p>

### Additional Code Snippet

The following code snippets provides addtional information how to use the *breinify-api-java* library.

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
                .setBaseUrl("http://dev.breinify.com/api")
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
       "digitalfootpring",
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
final Object dataDigitalFootprinting = result.get("digitalfootpring");
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
The method **setDateOfBirth** is provided twice. You could either use one of these:

````
1. setDateOfBirth(final int month, final int day, final int year) 

or 

2. setDateOfBirth(final String dateOfBirth)


````
**Note:** only the first option ensures correct storage of the date of birth value. The second one might be ignored from the Breinify backend.


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
      "http://dev.breinify.com/api",
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
      "http://dev.breinify.com/api",
      BreinEngineType.JERSEY_ENGINE);

// set the configuration for later usage
Breinify.setConfig(breinConfig);

```

