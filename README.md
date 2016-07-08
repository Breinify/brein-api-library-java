<p align="center">
  <img src="https://www.breinify.com/img/Breinify_logo.png" alt="Breinify API Java Library" width="250">
</p>

<p align="center">
Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips.
</p>

### Step By Step Introduction

#### What is Breinify's DigitialDNA

Breinify's DigitalDNA API puts dynamic behavior-based, people-driven data right at your fingertips. We believe that in many situations, a critical component of a great user experience is personalization. With all the data available on the web it should be easy to provide a unique experience to every visitor, and yet, sometimes you may find yourself wondering why it is so difficult.

Thanks to **Breinify's DigitalDNA** you are now able to adapt your online presence to your visitors needs and **provide a unique experience**. Let's walk step-by-step through a simple example.

#### Step 1: Download the sources

Download the Java sources from here. 


#### Step 2: Build the library

The Java soruce code is based on Java 8. Use maven to build the library. Choose target package. 

````
mvn package
````

#### Step 3: Configure the library

In order to use the library you need a valid API-key, which you can get for free at https://www.breinify.com. In this example, we assume you have the following api-key:

**772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6**

```Java
final String apiKey = "772A-47D7-93A3-4EA9-9D73-85B9-479B-16C6";
final BreinConfig breinConfig = new BreinConfig(apiKey,
            "https://breinify.com",
            BreinEngineType.UNIREST_ENGINE);
Breinify.setConfig(breinConfig);
```

This BreinConfig object is now configured with a valid api-key and a valid rest engine. In this case the UNIREST engine (see: unirest.com) will be used. The configuration will be registered to the Breinify class.


#### Step 4: Start using the library

##### Placing activity triggers

The engine powering the DigitalDNA API provides two endpoints. The first endpoint is used to inform the engine about the activities performed by visitors of your site. The activities are used to understand the user's current interest and infer the intent. It becomes more and more accurate across different users and verticals as more activities are collected. It should be noted, that any personal information is not stored within the engine, thus each individual's privacy is well protected. The engine understands several different activities performed by a user, e.g., landing, login, search, item selection, or logout.

The engine is informed of an activity by executing *Breinify.activity(...)*. 

```Java
// create a user, email is a mandatory field
private final BreinUser breinUser = 
     new BreinUser("user.anywhere@email.com");

// invoke the call  
Breinify.activity(breinUser,
     BreinActivityType.LOGIN,
     BreinCategoryType.HOME,
     "Login-Description",
     false);

```

This is actually all. The call will be invoked asynchronous.


##### Placing look-up triggers

Look-ups are used, e.g., to change the appearance of the site, increase the quality of service by enhancing recommendations or pre-filtering search results. In the following simple example, the site's message is adapted when the page is loaded.

```java
// define an array of subjects of interest
final String[] dimensions = {"firstname",
               "gender",
               "age",
               "agegroup",
               "digitalfootpring",
               "images"};

// wrap this into the BreinDimension
final BreinDimension breinDimension = 
             new BreinDimension(dimensions);

// invoke the lookup                
final BreinResult response = 
             Breinify.lookup(breinUser, breinDimension, sign);
             
             
// get the results
 final Object dataFirstname = result.get("firstname");
        final Object dataGender = result.get("gender");
        final Object dataAge = result.get("age");
        final Object dataAgeGroup = result.get("agegroup");
        final Object dataDigitalFootprinting = 
               result.get("digitalfootpring");
        final Object dataImages = result.get("images");

```
