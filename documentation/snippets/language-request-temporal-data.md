>
 ```java
 // Example with just IP address set
final BreinUser breinUser = new BreinUser()
         .setIpAddress("143.127.128.10");

final BreinTemporalDataResult result = Breinify.getBreinTemporalDataRequest();
result.setUser(breinUser);
// Invoke the temporal request
final BreinTemporalDataResult result = req.execute();
 ```