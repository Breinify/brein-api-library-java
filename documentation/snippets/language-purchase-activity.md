>
```java
final Map<String, String> tags = new HashMap<>();
tags.put('productIds', Arrays.asList("125689", "982361", "157029"))
tags.put('productPrices', Arrays.asList(134.23, 15.13, 12.99))

final BreinActivity breinActivity = Breinify.getBreinActivity();

breinActivity.setTags(tags);
breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
breinActivity.setType("checkOut");

Breinify.activity();
```