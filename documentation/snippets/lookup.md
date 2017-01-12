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