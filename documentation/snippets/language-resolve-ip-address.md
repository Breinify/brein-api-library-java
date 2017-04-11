<blockquote class="lang-specific java">
<p>With the Java library it is really simple to resolve temporal information
based on a client's ip-address. The endpoint utilizes the requesting ip-address to
determine, which information to return. Thus, the call does not need any additional
data.</p>
</blockquote>

>
```java
// Invoke the temporal request
final BreinTemporalDataResult result = Breinify.getBreinTemporalDataRequest().execute();
```

<blockquote class="lang-specific java">
<p>Sometimes, it may be necessary to resolve a specific ip-address instead of the client's
one. To specify the ip-address to resolve, the library provides a user object which one can store an ip address in, i.e.,</p>
</blockquote>

>
```java
final BreinUser breinUser = new BreinUser()
         .setIpAddress("143.127.128.10");

final BreinTemporalDataResult result = Breinify.getBreinTemporalDataRequest();
result.setUser(breinUser);
// Invoke the temporal request
final BreinTemporalDataResult result = req.execute();
```