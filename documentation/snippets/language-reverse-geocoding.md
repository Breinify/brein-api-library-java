>
final String userLocation = "San Diego";

final Map<String,Object> coords = new HashMap<>();
coords.put("latitude", 37.7609295);
coords.put("longitude", -122.4194155);

final BreinUser user = new BreinUser();
user.setAdditional(Collections.singletonMap("location", coords);

final BreinTemporalDataRequest req = Breinify.getBreinTemporalDataRequest();
req.setBreinUser(user);
BreinTemporalDataResult  res = req.execute();
```