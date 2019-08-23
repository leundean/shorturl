package shorturl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import shorturl.service.UrlStoreService;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ShortURLController {

    @Autowired
    UrlStoreService urlStoreService;

    @RequestMapping(value = "/add/{url}", method = {RequestMethod.GET, RequestMethod.PUT})
    @ResponseBody
    public Mono<Object> putValue(@PathVariable("url") String url) {
        log.info("/add/" + url);

        return urlStoreService.getShortUrl(url);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Mono<Object> postValue(@RequestBody List<String> url) {
        log.info("/add/" + url.get(0));

        return urlStoreService.getShortUrl(url.get(0));
    }
}
