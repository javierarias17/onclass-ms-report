package co.com.pragma.api;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    private static final String BOOTCAMP_REPORTS_PATH = "/api/v1/bootcamp-reports";

    @Bean
    @RouterOperations({
            @RouterOperation(path = BOOTCAMP_REPORTS_PATH, method = {
                    RequestMethod.POST }, beanClass = Handler.class, beanMethod = "listenRegisterBootcampReport")
    })
    public RouterFunction<ServerResponse> bootcampReportRouterFunction(Handler handler) {
        //HU-08
        return route(POST(BOOTCAMP_REPORTS_PATH), handler::listenRegisterBootcampReport);
    }
}
