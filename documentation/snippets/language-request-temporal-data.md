<blockquote class="lang-specific java--native">
<p>The JavaScript library offers several overloaded versions
of the <code class="prettyprint">Breinify.temporalData(...)</code> method.
In addition, it offers the possibility to fire the request directly
from the created request instance by calling <code class="prettyprint">execute()</code>.
The following examples (and also the use-cases) will introduce both types
which are completely interchangable.</p>
</blockquote>

<blockquote class="lang-specific java--native">
<p>If a simple request is fired, the endpoint uses the client's information (attached to the request, e.g., 
the <code class="prettyprint">ipAddress</code>) to determine the different temporal information.</p>
</blockquote>

>
```java--native
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLocalDateTime()
    .execute();
```

<blockquote class="lang-specific java--native">
<p>Another possibility is to provide specific data. This is typically done, if
some specific temporal data should be resolved, e.g., a location based on a free text, 
a pair of coordinates (latitude/longitude), or a specific ip-address. Have a look at the
<a href="#example-use-cases">further use cases</a> to see other examples.</p>
</blockquote>

>
```java--native
final BreinTemporalDataResult result = new BreinTemporalData()
    .setLookUpIpAddress("204.28.127.66")
    .execute();
```

<blockquote class="lang-specific java--native">
<p>The library provides several setter methods to simplify the usage (e.g., the already
used <code class="prettyprint">setLocalDateTime</code> or <code class="prettyprint">setLookUpIpAddress</code>).
In addition, it is also possible to set these values using the more general setter methods, i.e., 
<code class="prettyprint">setAdditional(key, value)</code>, <code class="prettyprint">setUser(key, value)</code>, 
<code class="prettyprint">setLocation(key, value)</code>, or <code class="prettyprint">set(key, value)</code>.
The above requests could also be performed using:</p>
</blockquote>

>
```java--native
final BreinTemporalDataResult result = new BreinTemporalData()
    .setAdditional("localDateTime", 
        ZonedDateTime.now().format(BreinTemporalData.JAVA_SCRIPT_FORMAT))
    .execute();
```

>
```java--native
final BreinTemporalDataResult result = new BreinTemporalData()
    .setAdditional("ipAddress", "204.28.127.66")
    .execute();
```