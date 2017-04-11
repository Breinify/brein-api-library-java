>
```java
final BreinActivity breinActivity = Breinify.getBreinActivity();

final BreinUser breinUser = new BreinUser().setEmail("max@sample.com");

breinActivity.setBreinUser(breinUser);
breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
breinActivity.setType("login");

Breinify.activity();
```