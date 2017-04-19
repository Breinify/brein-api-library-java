<blockquote class="lang-specific java--native">
<p>With the Java library it is really simple to resolve temporal information
based on a client's ip-address. The endpoint utilizes the requesting ip-address to
determine, which information to return. Thus, the call does not need any additional 
data.</p>
</blockquote>

>
```java--native
final BreinTemporalDataResult result = Breinify.temporalData();
```

<blockquote class="lang-specific java--native">
<p>Sometimes, it may be necessary to resolve a specific ip-address instead of the client's
one. To specify the ip-address to resolve, the library provides an overloaded version, i.e.,</p>
</blockquote>

>
```java--native
final String ip = "72.229.28.185"; 
final BreinTemporalDataResult result = Breinify.temporalData(ip);
```