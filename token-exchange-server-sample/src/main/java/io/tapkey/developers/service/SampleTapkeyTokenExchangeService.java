package io.tapkey.developers.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * A service that provides functionality required for Tapkey's Token Exchange
 * grant type. It creates JWT tokens for local users that can be exchanged for
 * Tapkey access tokens on mobile clients. It takes care of creating
 * corresponding Tapkey users for local users.
 */
@Service
public class SampleTapkeyTokenExchangeService implements TapkeyTokenExchangeService {

    @Autowired
    private KeyService keyService;

    @Autowired
    private WebClient webClient;

    /*
     * The ID of a Client Credentials OAuth client registered with Tapkey. The
     * client's service user must be granted admin permissions on the owner
     * account holding the identity provider with the ID
     * tapkey.identityProvider.id.
     */
    @Value("${tapkey.oauth.tokenExchange.client.id}")
    private String oauthClientId;

    /*
     * The Tapkey owner account that holds the identity provider with the ID
     * tapkey.identityProvider.id.
     */
    @Value("${tapkey.ownerAccount.id}")
    private String ownerAccountId;

    @Value("${tapkey.identityProvider.id}")
    private String identityProviderId;

    @Value("${tapkey.identityProvider.audience}")
    private String identityProviderAudience;

    @Value("${tapkey.identityProvider.issuer}")
    private String identityProviderIssuer;

    /**
     * Returns a new JWT token that can be exchanged for a Tapkey access token
     * using the Token Exchange grant type.
     *
     * @param userId the ID of the user to create a token for. Note that this
     *               is not the Tapkey user ID, but the local user ID of this
     *               sample server application.
     * @return the JWT token for the specified user. Valid for one hour.
     */
    @Override
    public String createJwtToken(String userId) {
        Calendar expiration = Calendar.getInstance();
        expiration.roll(Calendar.HOUR, 1);

        return Jwts.builder()
                .addClaims(new HashMap<String, Object>() {{
                    put("http://tapkey.net/oauth/token_exchange/client_id", oauthClientId);
                }})
                .setSubject(userId)
                .setAudience(identityProviderAudience)
                .setIssuer(identityProviderIssuer)
                .setIssuedAt(new Date())
                .setExpiration(expiration.getTime())
                .signWith(keyService.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * Creates a corresponding Tapkey user for the given local user ID.
     *
     * @param userId the ID of the local user to create a Tapkey user for.
     * @return the Tapkey user ID of the Tapkey user created for the given
     * local user.
     */
    public Mono<String> createTapkeyUser(String userId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        uriBuilder.scheme("https").host("dev1.dev.tapkey.net")
                .replacePath("api")
                .pathSegment("v1")
                .pathSegment("Owners")
                .pathSegment("{ownerAccountId}")
                .pathSegment("IdentityProviders")
                .pathSegment("{ipId}")
                .pathSegment("Users");

        return webClient
                .put()
                .uri(uriBuilder.buildAndExpand(ownerAccountId, identityProviderId).toUriString())
                .attributes(clientRegistrationId("tapkey"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(new IpUserDto(identityProviderId, userId)), IpUserDto.class)
                .retrieve()
                .bodyToMono(IpUserDto.class)
                .log()
                .map(IpUserDto::getId);
    }

}
