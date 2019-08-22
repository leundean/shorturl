package shorturl.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import shorturl.service.UrlStoreService;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class EndpointsTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    UrlStoreService urlStoreService;

    @Test
    public void thatAValidURLIsStoredAndThenRedirectedWithItsShortUrl(){
        String longUrl = "https://www.merriam-webster.com/";

        urlStoreService.getShortUrl(longUrl)
                        .doOnNext(o -> {
                            webTestClient.get().uri((String) o)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                                    .exchange()
                                    .expectStatus().isEqualTo(HttpStatus.PERMANENT_REDIRECT);
                        })
                        .subscribe();
    }

    @Test
    public void thatAValidURLIsStoredByPutMethodAndUrlEncodedInUrl(){
        String longUrl = "https://www.merriam-webster.com/";
        String encoded = null;
        try {
            encoded = URLEncoder.encode(longUrl, "UTF-8");
        }
        catch (Exception e){}

        webTestClient.put().uri("/add/" + encoded)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Arrays.asList(longUrl)), List.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        assertEquals(29, response.getResponseBody().length));
        // We don't have any defined response object and just retrieve a string
        // of baseUrl (22) and shortUrl (5) + 2 hyphens = 29 string characters.
    }


    @Test
    public void thatAValidURLIsStoredByPostMethodAndLongUrlInBody(){
        String longUrl = "https://www.merriam-webster.com/";

        webTestClient.post().uri("/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Arrays.asList(longUrl)), List.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        assertEquals(29, response.getResponseBody().length));
        // We don't have any defined response object and just retrieve a string
        // of baseUrl (22) and shortUrl (5) + 2 hyphens = 29 string characters.
    }

    @Test
    public void thatANonExistingURLReportsNotFound(){
        webTestClient.post().uri("/zzzzz")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    /*
    // Not implemented
    @Ignore
    @Test
    public void thatAnInvalidURLReportsUnprocessableEntity(){
        String longUrl = "htaaatps://wwaaaw.meraaariam-webaaaster.caaaom/";

        webTestClient.post().uri("/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(Arrays.asList(longUrl)), List.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    */
}


