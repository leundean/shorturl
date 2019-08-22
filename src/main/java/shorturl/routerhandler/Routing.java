package shorturl.routerhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class Routing {

    @Autowired
    HandlingRedirect handlingRedirect;

    @Bean
    RouterFunction<ServerResponse> routerFunction(){
        return route(RequestPredicates.GET("/**")
                .and(RequestPredicates.PUT("/add/**").negate()), handlingRedirect::redirect);
    }
}
