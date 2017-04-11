>
```java
final String userLocation = "San Diego";

final BreinUser user = new BreinUser();
user.setAdditional(Collections.singletonMap("location", Collections.singletonMap("text", userLocation)));

final BreinTemporalDataRequest req = Breinify.getBreinTemporalDataRequest();
req.setBreinUser(user);
BreinTemporalDataResult  res = req.execute();
});
```