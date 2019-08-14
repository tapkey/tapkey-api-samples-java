package io.tapkey.developers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class SampleTapkeyManagementService implements TapkeyManagementService {

    @Autowired
    private WebClient webClient;

    /*
     * The Tapkey owner account that holds the identity provider with the ID
     * tapkey.identityProvider.id.
     */
    @Value("${tapkey.ownerAccount.id}")
    private String ownerAccountId;

    @Value("${tapkey.identityProvider.id}")
    private String identityProviderId;

    @Override
    public Mono<String> createContact(String userId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme("https").host("dev1.dev.tapkey.net")
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("Contacts");

        return webClient
                .put()
                .uri(uriBuilder.buildAndExpand(ownerAccountId).toUriString())
                .attributes(clientRegistrationId("tapkey"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new ContactDto(identityProviderId, userId)), ContactDto.class)
                .retrieve()
                .bodyToMono(ContactDto.class)
                .log()
                .map(ContactDto::getId);
    }

    @Override
    public Mono<String> createGrant(String contactId, String boundLockId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme("https").host("dev1.dev.tapkey.net")
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("Grants");

        return webClient
                .put()
                .uri(uriBuilder.buildAndExpand(ownerAccountId).toUriString())
                .attributes(clientRegistrationId("tapkey"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new GrantDto(contactId, boundLockId)), GrantDto.class)
                .retrieve()
                .bodyToMono(GrantDto.class)
                .log()
                .map(GrantDto::getId);
    }

}
