package io.tapkey.developers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;
// import reactor.netty.http.client.HttpClient;
// import org.springframework.http.client.reactive.ReactorClientHttpConnector;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository serverOAuth2AuthorizedClientRepository) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, serverOAuth2AuthorizedClientRepository);
        oauth.setDefaultClientRegistrationId("tapkey");
        return WebClient.builder()
                /*
                 * Uncomment the following lines and add
                 * logging.level.reactor.netty.http.client.HttpClient=DEBUG
                 * to get detailed logging on HTTP requests.
                 */
                //.clientConnector(new ReactorClientHttpConnector(
                //HttpClient.create().wiretap(true)
                //))
                .filter(oauth)
                .build();
    }

}
