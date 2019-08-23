package io.tapkey.developers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class SampleTapkeyManagementService implements TapkeyManagementService {

    @Autowired
    private WebClient webClient;

    @Value("${tapkey.authority}")
    private String tapkeyAuthority;

    /*
     * The Tapkey owner account that holds the identity provider with the ID
     * tapkey.identityProvider.id.
     */
    @Value("${tapkey.ownerAccount.id}")
    private String ownerAccountId;

    @Value("${tapkey.identityProvider.id}")
    private String identityProviderId;

    private UriBuilderFactory uriBuilderFactory;

    @PostConstruct
    public void initialize() {
        assert this.tapkeyAuthority != null;
        uriBuilderFactory = new DefaultUriBuilderFactory(tapkeyAuthority);
    }

    @Override
    public Mono<String> createContact(String userId) {
        UriBuilder uriBuilder = uriBuilderFactory.builder()
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("Contacts");

        return webClient
                .put()
                .uri(uriBuilder.build(ownerAccountId))
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
        UriBuilder uriBuilder = uriBuilderFactory.builder()
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("Grants");

        return webClient
                .put()
                .uri(uriBuilder.build(ownerAccountId))
                .attributes(clientRegistrationId("tapkey"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new GrantDto(contactId, boundLockId)), GrantDto.class)
                .retrieve()
                .bodyToMono(GrantDto.class)
                .log()
                .map(GrantDto::getId);
    }

    @Override
    public Mono<List<GrantDto>> getGrants(String contactId, String[] grantIds) {
        UriBuilder uriBuilder = uriBuilderFactory.builder()
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("Grants")
                .query("$filter=active eq true");

        return webClient
                .get()
                .uri(uriBuilder.build(ownerAccountId))
                .attributes(clientRegistrationId("tapkey"))
                .retrieve()
                .bodyToMono(GrantDto[].class)
                .map(Arrays::asList)
                .map(grants -> grants
                        .stream()
                        .filter(x -> Arrays.asList(grantIds).contains(x.getId()))
                        .filter(x -> x.getContactId().equals(contactId))
                        .collect(Collectors.toList())
                )
                .log();
    }

}
