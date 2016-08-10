```java
// create a user you are interested in with their email (mandatory field)
final BreinUser breinUser = new BreinUser("user.anywhere@email.com");

// invoke an activity noting that the user has logged in
Breinify.activity(breinUser, BreinActivityType.LOGIN,
         BreinCategoryType.HOME, "Login-Description", false);

```