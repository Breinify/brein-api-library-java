<blockquote class="lang-specific java--native">
<p>When calling the activity endpoint, it is assumed that it is a 'fire and forget' call, i.e., the endpoint is just informed
that the activity happened, but the returned information is ignored. Thus, the implementation of 
<code class="prettyprint">Breinify.activity(...)</code> does not return any information.</p>
</blockquote>

>
```java--native
Breinify.activity(new M<String>()
        .set("email", "user.login@gmail.com")
        .set("sessionId", "966542c6-2399-11e7-93ae-92361f002671"), "login");
```

<blockquote class="lang-specific java--native">
<p>Nevertheless, sometimes (e.g., for logging or debugging purposes) it may be of benefit to see the
returned value. The returned value can be read by utilizing a callback function, i.e., 
</blockquote>

>
```java--native
Breinify.activity(new M<String>().set("email", "user.login@gmail.com").set("sessionId", "966542c6-2399-11e7-93ae-92361f002671"), 
        "login", (result) -> {
            // do something with the result, e.g., check the status
            // result.getStatus() == 200
        });
```