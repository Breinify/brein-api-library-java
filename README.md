<p align="center">
  <img src="https://raw.githubusercontent.com/Breinify/brein-api-library-java/master/documentation/img/logo.png" alt="Breinify API Java Library" width="250">
</p>

<p align="center">
Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips.
</p>

### Step By Step Introduction

#### What is Breinify's DigitialDNA

Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips. We believe that in many situations, a critical component of a great user experience is personalization. With all the data available on the web it should be easy to provide a unique experience to every visitor, and yet, sometimes you may find yourself wondering why it is so difficult.

Thanks to **Breinify's DigitalDNA** you are now able to adapt your online presence to your visitors needs and **provide a unique experience**. Let's walk step-by-step through a simple example.

### Quick start

#### Step 1: Download the Library

Download the Library from [Maven Central Repository] (http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22brein-api-library-java%22)


#### Step 2: Integrate the library

Integrate the Library into your Java project. 


#### Step 3: Configure the library

In order to use the library you need a valid API-key, which you can get for free at [https://www.breinify.com](https://www.breinify.com). In this example, we assume you have the following api-key:

**772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6**

```Java
// this is the valid api-key
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";

// create the configuration object
final BreinConfig breinConfig = new BreinConfig(apiKey);

// set the configuration for later usage
Breinify.setConfig(breinConfig);
```

The Breinify class is now configured with a valid configuration object.


#### Step 4: Start using the library

##### Placing activity triggers

The engine powering the DigitalDNA API provides two endpoints. The first endpoint is used to inform the engine about the activities performed by visitors of your site. The activities are used to understand the user's current interest and infer the intent. It becomes more and more accurate across different users and verticals as more activities are collected. It should be noted, that any personal information is not stored within the engine, thus each individual's privacy is well protected. The engine understands several different activities performed by a user, e.g., landing, login, search, item selection, or logout.

The engine is informed of an activity by executing *Breinify.activity(...)*. 

```Java
// create a user you are interested in 
final BreinUser breinUser = new BreinUser("user.anywhere@email.com")
         .setFirstName("User")
         .setLastName("Anyhere");
               
// invoke an activity request
Breinify.activity(breinUser, 
         BreinActivityType.LOGIN,
         BreinCategoryType.HOME, 
         "Login-Description");

```

That's it! The call will run asynchronously in the background.


##### Placing look-up triggers

Look-ups are used to retrieve dedicated information for a given user. 

```java
// define an array of subjects of interest
final String[] dimensions = {"firstname",
       "gender",
       "age",
       "agegroup",
       "digitalfootprint",
       "images"};

// wrap this array into BreinDimension
final BreinDimension breinDimension = new BreinDimension(dimensions);

// invoke the lookup
final BreinResult result = Breinify.lookup(breinUser, breinDimension);

// retrieve the values of interest
final Object dataFirstname = result.get("firstname");
final Object dataGender = result.get("gender");
final Object dataAge = result.get("age");
final Object dataAgeGroup = result.get("agegroup");
final Object dataDigitalFootprinting = result.get("digitalfootprint");
final Object dataImages = result.get("images");

```

##### Placing temporalData triggers

Temporal triggers will provide <....> information. They can be requested like this:

```Java
// create a user you are interested in 
final BreinUser breinUser = new BreinUser()
         .setTimezone("America/Los_Angeles")
         .setLocalDateTime("Sun 25 Dec 2016 18:15:48 GMT-0800 (PST)");
               
// invoke the temporal request 
final BreinResult result = Breinify.temporalData(breinUser);
final Object timeValues = result.get("time");
final Object weatherValues = result.get("weather");
final Object locationValues = result.get("location");
final Object holidayValues = result.get("holiday");

```

#### Step 5: Teardown of the Library Services

Depending of the rest engine (e.g. UNIREST) some threads needs to be stopped. This will be done by invoking the following statement:

```
// terminates the engine and possible open threads
Breinify.shutdown();
``` 

Please note that after having invoked this call no further Breinify.activity or Breinify.lookup calls are possible. So it should only be part of your termination sequence of your program. Without this statement your program might not terminate.

### Further links
To understand all the capabilities of Breinify's DigitalDNA API, take a look at:

* [Additional code snippets](documentation/more-snippets.md),
* [Change Log](documentation/changelog.md), or
* [Breinify's Website](https://www.breinify.com).
