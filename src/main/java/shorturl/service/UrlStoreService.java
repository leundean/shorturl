package shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import shorturl.config.YAMLConfig;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UrlStoreService {

    @Autowired
    YAMLConfig yamlConfig;

    // This URLs are stored in a map (no persistant storage)
    private Map<String, String> urlMap = new HashMap<>();

    public Mono<Object> getShortUrl(String url){
        String validUrl = validate(url);
        if (validUrl != null){
            log.info("valid url");
            // If Url is not stored, return shortUrl
            if (!urlMap.containsValue(validUrl)){
                String shortUrl = shortify(validUrl);
                urlMap.put(shortUrl, validUrl);
                log.info("added: " + shortUrl + " --> " + validUrl);
                return Mono.just(yamlConfig.getUrlserver() + "/" + shortUrl);
            }
            // If URL is stored, return the short URL (this use-case assumes not adding multiple new).
            // Easily changed if lookup is costly or wanting multiple short url for other reason
            else {
                for (Map.Entry<String, String> entry: urlMap.entrySet()){
                    if (entry.getValue().equals(validUrl)){
                        log.info("already here: " + entry.getKey() + " --> " + validUrl);
                        return Mono.just(yamlConfig.getUrlserver() + "/" + entry.getKey());
                    }
                }
            }
        }
        // If Url invalid, report
        log.info("invalid url: " + url);
        return Mono.just(new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY));

    }

    // This method simply add the http protocol prefix that some clients might require. If extended it allows "non-http"
    // addresses to be valid http. It could verify from misspelling patterns and it could open a connection to validate
    // actual status, but this would significantly change performance. Also can check http/https or other protocols and
    // be run on intervals to remove or mark outdated URLs.
    public String validate(String url){
        String testUrl = url;
        if (!testUrl.startsWith("http")) {
            testUrl = "http://" + testUrl;
        }
        // Connection test not implemented
        if (true){
            return testUrl;
        }
        else {
            return null;
        }
    }

    // This method allows a "short URL length" of five (5) characters and 11.8 million unique URLs
    public String shortify(String url){
        String shortUrlCode = "";
        for (int i=0; i < 5; i++){
            char n = (char) (96 + (int) Math.ceil(Math.random()*26));
            shortUrlCode += "" + n;
        }
        return shortUrlCode;
    }

    public String getLongUrl(String shortUrlCode){
        return urlMap.get(shortUrlCode);
    }

}
