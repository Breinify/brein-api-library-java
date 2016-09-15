# Change log Breinify Java Api
This document contains a chronologically ordered list of changes for the Java Api library.


# Version 1.2.0


## Modifications

###BreinActivity.java & BreinUser.java

Some fields and their appropiate methods have been moved from BreinActivity.java to BreinUser.java. Those are:

- sessionId
- userAgent
- referrer
- url (has been renamed from additionUrl from BreinActivity.java)

Furthermore the following field is not available anymore:
- ipAddress


## Enhancements

###BreinConfig.java
It is now possible to set a default category. This will be used whenever a category is not provided by the BreinActivity instance.

```java
// create configuration
final BreinConfig breinConfig = new BreinConfig(VALID_API_KEY,
          BASE_URL,
          BreinEngineType.UNIREST_ENGINE)
          .setDefaultCategory("DEF-CAT-TYPE");

```

###BreinUser.java
Provides new methods to set fields that have been moved from BreinActivity.java. Those are:

```java
// user data
final BreinUser breinUser = new BreinUser()
         .setSessionId("r3V2kDAvFFL_-RBhuc_-Dg")
         .setUrl("https://sample.com.au/home")
         .setReferrer("https://sample.com.au/track")
         .setIpAddress("10.11.12.130)
         .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");

```



# Version 1.1.0


## Enhancements

###BreinUser.java

Method ***setDataOfBirth(final String)*** has been removed. This has lead to wrong values when sending the date of birth. From now on only the following method is supported:

````
setDateOfBirth(final int month, 
               final int day, 
               final int year)
````


###Breinify.java

The following new method:

```
public static void activity(final BreinActivity breinActivity)
```

can now be used to invoke an activity request. This implies that the necessary properties must be set before the call itself is invoked. 

This means that the following code is now possible:

```
...
// activity
final BreinActivity breinActivity = Breinify.getBreinActivity();

// set the appropriate properties
breinActivity.setBreinUser(breinUser);
breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);
breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
...
// invoke the activity request
Breinify.activity();
```

###BreinifyExecutor.java


BreinifyExecutor provides now a similar approach and this looks as follows:

```
 public void activity() 
```

This enables a similar mechanismn that is provied for Breinify.java and would look like this:

````
...
final BreinifyExecutor breinifyExecutor = new BreinConfig()
                .setApiKey(VALID_API_KEY)
                .setBaseUrl(BASE_URL)
                .setRestEngineType(BreinEngineType.UNIREST_ENGINE)
                .build();

final BreinActivity breinActivity = breinifyExecutor.getBreinActivity();

breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
breinActivity.setBreinUser(breinUser);     
breinActivity.setBreinCategoryType(BreinCategoryType.APPAREL);       breinActivity.setBreinActivityType(BreinActivityType.PAGEVISIT);
breinActivity.setDescription("your description");

// invoke activity call
breinifyExecutor.activity();
````
One difference of class BreinifyExecutor is that it has an instane of BreinActivity.

###BreinUsery.java



###BreinActivity.java


The activity request contains now the following fields:

- ipAddress
- sessionId
- userAgent
- referrer
- additionalUrl
- tags

The result of an activity request looks like this:


````
{
  "user": {
    "email": "User.Name@email.com",
    "firstName": "User",
    "lastName": "Name",
    "sessionId": "r3V2kDAvFFL_-RBhuc_-Dg",
    "additional": {
      "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
      "referrer": "https://teespring.com/track",
      "url": "https://verygoodurl.com/track"
    }
  },
  "activity": {
    "type": "pageVisit",
    "description": "your description",
    "category": "apparel"
  },
  "apiKey": "A187-B1DF-XXXX-YYYY-ZZZZ-4729-7B54-E5B1",
  "unixTimestamp": 1472765431,
  "ipAddress": "11.222.333.444"
}
````

###BreinBase.java

The method:

```
public BreinBase setUnixTimestamp(final long unixTimestamp)
```
can now be set with a value before a activity or lookup request is triggered. This is an optional configuration. If this property is not set then the current timestamp will be computed and used within the request.

##Modifications
* Method **activity** in class Breinify.java does not support the parameter BreinActivity anymore. From now on the member within the class will be used.

##Fixes

* BreinLookup.java contained the lookup request twice
* BreinActivityType.java: PAGEVISIT type was missing
