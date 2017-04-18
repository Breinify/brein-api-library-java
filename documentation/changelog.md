<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify: Leading Temporal AI Engine" width="250">
</p>

# Change log Breinify Java Api
This document contains a chronologically ordered list of changes for the Java Api library.

# Version 2.0.0

The architecture of the library was re-factored. With these changes, the general usage of the library is the same as before.
Nevertheless, slight changes may be necessary to adapt to the new structure.

* `BreinTemporalDataResult` provides detailed access to the different returned data-points of the `\temporaldata` endpoint.
* multiple `setters` where added to increase usability of the library
* it is now possible to `shutdown` and `reuse` the library

# Version 1.3.2

## Modifications

### Concurrency Support

With each activity or temporalData request the associated request classes are cloned before the request call will be invoked. This will prevent concurrency issues in multithread situations.

### Flexible Maps 
The method names have changed for the flexible Maps.

* breinBase.setBase(..) instead of breinBase.setBaseMap(..)
* breinActivity.setActivity(..) instead of breinActivity.setActivityMap(..)
* breinUser.set(..) instead of breinUser.setUserMap(..)
* breinUser.setAdditional(..) instead of breinUser.setAdditionalMap(..)



# Version 1.3.1

## Modifications

### TemporalData
Support of new endpoint temporalData

### Request will be signed if secret is set
This implies that no additional sign flag has to be set within the call and has
been removed from the appropriate:
* activity
* temporalData
* lookup

calls.


### Flexible Maps 
The flexible maps now belongs to their related classes. This means that:
* BreinBase has a map for the base level
* BreinActivity has a map for the activity level
* BreinUser has two maps. One for the user level and one for the additional level.


### BreinConfig
The constructors of class BreinConfig have changed.


# Version 1.3.0

## Modifications

###Flexible Key-Value maps
It is now possible to add additional maps on each level (base, user, additional)
to the requst.

```java
final Map<String, Object> dataUserMap = new HashMap<>();
dataUserMap.put("enhancement-user-1", "user-value-1");
dataUserMap.put("enhancement-user-2", "user-value-2");

final BreinUser localBreinUser = new BreinUser()
      .setEmail("fred.firestone@email.com")
      .setMap(dataUserMap);

invokeActivityCall(localBreinUser,
      breinActivityType,
      breinCategoryType,
      "login");

```


# Version 1.2.0

## Modifications

###Callback support

Within the activity request an error callback can now be configured. This one will be invoked if the activity request might fail. 

```java
// callback handler
final Function<String, Void> callback = message -> {
     System.out.println(message);
     // do any logic here -> e.g. stop sending requests
     return null;
};


// invoke activity call
Breinify.activity(breinUser, breinActivityType, breinCategoryType,
                  description, signFlag, callback);
```

###Flexible Key-Value maps 

When creating the activity request it is now possible to add key-value pairs at each level instead of using the dedicated setter methods (e.g. actvitiy.setDescription("xxx") ).

The following levels will be supported:

- base 
- activity
- user
- user additional

```java
// base level
final Map<String, Object> dataBaseLevelMap = new HashMap<>();
dataBaseLevelMap("enhancement-base", "value");

final BreinActivity breinActivity = Breinify.getBreinActivity();
breinActivity.setExtraMap(BreinActivity.MapKind.MK_BASE, dataBaseLevelMap);


// activity level
final Map<String, Object> dataActivityMap = new HashMap<>();
dataActivityMap.put("enhancement-activity-1", "value-1");
dataActivityMap.put("enhancement-activity-2", "value-2");

final BreinActivity breinActivity = Breinify.getBreinActivity();
breinActivity.setExtraMap(BreinActivity.MapKind.MK_ACTIVITY, dataActivityMap);


// user level
final Map<String, Object> dataUserMap = new HashMap<>();
dataUserMap.put("enhancement-user-1", "user-value-1");
dataUserMap.put("enhancement-user-2", "user-value-2");

final BreinActivity breinActivity = Breinify.getBreinActivity();
breinActivity.setExtraMap(BreinActivity.MapKind.MK_USER, dataUserMap);


// user additinal level 
final Map<String, Object> dataAdditionalMap = new HashMap<>();
dataAdditionalMap.put("enhancement-user-additional-1", "user-add-value-1");
dataAdditionalMap.put("enhancement-user-additional-2", "user-add-value-2");

final BreinActivity breinActivity = Breinify.getBreinActivity();
breinActivity.setExtraMap(BreinActivity.MapKind.MK_USER_ADDITIONAL, dataAdditionalMap);
```



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
- tagsMap

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
