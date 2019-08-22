package shorturl.routerhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import shorturl.service.UrlStoreService;
import java.net.URI;

@Component
@Slf4j
public class HandlingRedirect {

    @Autowired
    UrlStoreService urlStoreService;

    public Mono<ServerResponse> redirect(ServerRequest request) {
        // Assuming short urls are on shortest/topdomain path
        log.info(request.uri().getPath());
        String longUrl = urlStoreService.getLongUrl(request.uri().getPath().replace("/", ""));
        if (longUrl == null){
            log.info("unknown short url");
            return ServerResponse.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("return long url: " + longUrl);

        return ServerResponse.permanentRedirect(URI.create(longUrl)).build();
    }
}
