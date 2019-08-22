
# ShortUrl

ShortUrl is a SpringBoot/SpringWebFlux application that uses the Router/Handler methods to redirect a shortified URL to an actual URL.

Build/test/install it with "mvn clean install" and run it as a SpringBoot jar: "java -jar target/shorturl-0.1.0.jar"

It provides an endpoint <hostname:port>/add that returns a shortened URL as a five characters string value. The endpoints accepts:
- Method: POST, Object/Body: an actual URL as the first element of a List/Array of strings
- Method: PUT, /add/{encodedURL}  (an actual URL which is UTF-8 URLencoded)

A short URL is permanently redirected by entering the path: <hostname:port>/<five-characters-string-value>
Unit- and integration tests are provided showing the expected responses at valid/invalid* URL insertions and valid/invalid shortURL redirections.

No persistant storage is provided so the entered URLs are lost between runs.

*Currently it is possible to add URLs without the "http://" address prefix (but then make sure the link isn't "https" prefixed). Other than that no validation of the links exist.
