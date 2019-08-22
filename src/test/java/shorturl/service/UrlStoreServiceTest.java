package shorturl.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shorturl.config.YAMLConfig;
import shorturl.controller.ShortURLController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YAMLConfig.class, UrlStoreService.class, ShortURLController.class})
public class UrlStoreServiceTest {

    @Autowired
    UrlStoreService urlStoreService;

    @Test
    public void thatANonValidUrlGetsAHttpPrefix(){
        String withoutHttpUrl = "www.wikipedia.org";
        assertEquals("http://" + withoutHttpUrl,urlStoreService.validate(withoutHttpUrl));
    }

    // Here more tests can be added for validation
}
