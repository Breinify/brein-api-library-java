<blockquote class="lang-specific java--native">
<p>The library is available on <a target="_blank" href="https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.breinify%22%20AND%20a%3A%22brein-api-library-java%22">Maven Central</a>.
You can add it to your Maven project by adding the following dependency and replacing the property <code class="prettyprint">${CURRENT_VERSION}</code>, with the current version of the library or the 
version you would like to use.</p>
</blockquote>

>
```java--native
<dependency>
  <groupId>com.breinify</groupId>
  <artifactId>brein-api-library-java</artifactId>
  <version>${CURRENT_VERSION}</version>
</dependency>
```

<blockquote class="lang-specific java--native">
<p>The library utilizes a query engine, to send requests to the API endpoint. Currently, two query engines are 
supported: <code class="prettyprint">UniRest</code> (<b>recommended</b>) or
<code class="prettyprint">Jersey</code>. By default, none of the engines is added as dependency, thus it is necessary
to pick and add one of the two and add its dependency as well.</p>
</blockquote>

>
```java--native
<dependency>
    <groupId>com.mashape.unirest</groupId>
    <artifactId>unirest-java</artifactId>
    <version>1.4.9</version>
</dependency>
```

>
```java--native
<dependency>
    <groupId>com.sun.jersey</groupId>
    <artifactId>jersey-client</artifactId>
    <version>1.19.1</version>
</dependency>
```