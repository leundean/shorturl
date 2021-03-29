package shorturl.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvExtracted {

    @Value("${GDM_LANG:en}")
    String language;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("hw" + language);
    }

}

