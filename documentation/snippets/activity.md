> ```java
>
> // Simple activity request
> final BreinUser breinUser = new BreinUser()
>         .setFirstName("User")
>         .setLastName("Name")
>         .setIpAddress("10.11.12.130")
>
>
> // Invoke activity call (without secret and no callback)
> Breinify.activity(breinUser,
>                "Login",
>                "Home",
>                "Login-Description",
>                false, null);
>
>
> // Comprehensive sample to set various fields
> final BreinUser breinUser = new BreinUser("User.Name@email.com")
>         .setFirstName("User")
>         .setLastName("Name")
>         .setDateOfBirth(11, 20, 1999)
>         .setDeviceId("DD-EEEEE")
>         .setImei("55544455333");
> 
> final Map<String, String> tagMap = new HashMap<>();
> tagMap.put("t1", "0.0");
> tagMap.put("t2", "0.0");
> tagMap.put("t3", "0.0");
> tagMap.put("t4", "0.0");
> tagMap.put("nr", "1.0");
> tagMap.put("sortid", "1.0");
> 
> final BreinActivity breinActivity = Breinify.getBreinActivity();
> 
> breinActivity.setUnixTimestamp(Instant.now().getEpochSecond());
> breinActivity.setBreinUser(breinUser);
> breinActivity.setBreinCategoryType("apparel");
> breinActivity.setBreinActivityType("pagevisit");
> breinActivity.setDescription("your description");
> breinActivity.setSign(false);
> breinActivity.setTagsMap(tagMap);
> breinActivity.setSessionId("r3V2kDAvFFL_-RBhuc_-Dg");
> breinActivity.setAdditionalUrl("https://sample.com.au/home");
> breinActivity.setReferrer("https://sample.com.au/track");
> breinActivity.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");
> 
> Breinify.activity();
> ```
