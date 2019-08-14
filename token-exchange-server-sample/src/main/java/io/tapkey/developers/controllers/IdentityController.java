package io.tapkey.developers.controllers;

import io.tapkey.developers.auth.SampleUserPrincipal;
import io.tapkey.developers.data.User;
import io.tapkey.developers.data.UserRepository;
import io.tapkey.developers.service.KeyService;
import io.tapkey.developers.service.TapkeyManagementService;
import io.tapkey.developers.service.TapkeyTokenExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

@RestController
public class IdentityController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyService keyService;

    @Autowired
    private TapkeyTokenExchangeService tapkeyTokenExchangeService;

    @Autowired
    private TapkeyManagementService tapkeyManagementService;

    @Value("${tapkey.boundLock.id}")
    private String demoBoundLockId;

    /*
     * Returns the public key of the key pair this instance is using to sign
     * JWT tokens for the Token Exchange grant type. The public key must be
     * registered with the configured identity provider.
     */
    @GetMapping("/public-key")
    public String getPublicKey() {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.keyService.getPublicKey().getEncoded());
        return "-----BEGIN PUBLIC KEY-----\n" +
                Base64.getEncoder().encodeToString(x509EncodedKeySpec.getEncoded())
                        .replaceAll("(.{64})", "$1\n") +
                "\n-----END PUBLIC KEY-----";
    }

    /*
     * Creates a new local user with the specified details. Can be used to
     * authenticate requests to the /user/tapkey-token operation.
     */
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        user.setRoles(new String[]{"ROLE_USER"});
        return userRepository.save(user);
    }

    /*
     * Returns a JWT token for the authenticated user. Requires HTTP Basic
     * Authentication.
     */
    @GetMapping("/user/tapkey-token")
    public Mono<Map<String, String>> tapkeyToken(Authentication authentication) {
        SampleUserPrincipal principal = (SampleUserPrincipal) authentication.getPrincipal();

        // Check if the present user already has a corresponding Tapkey user
        if (principal.getTapkeyUserId() == null) {

            /*
             * Lets the Tapkey Trust Service know about local user. The
             * returned Tapkey user ID is stored with the local user and can be
             * accessed later using {@link User#getTapkeyUserId()}.
             *
             * Furthermore, a contact that corresponds to the newly create user
             * is created. In the last step, a grant is issued for this
             * contact.
             */
            return tapkeyTokenExchangeService.createTapkeyUser(principal.getId())
                    .map(tapkeyUserId -> {
                        // Update user with Tapkey user ID.
                        User user = userRepository.findById(principal.getId()).orElse(null);
                        assert user != null;
                        user.setTapkeyUserId(tapkeyUserId);
                        userRepository.save(user);
                        return principal.getId();
                    })
                    .flatMap(userId -> tapkeyManagementService.createContact(principal.getId()))
                    .flatMap(contactId -> tapkeyManagementService.createGrant(contactId, demoBoundLockId))
                    .map(grantId -> Collections.singletonMap(
                            "externalToken", tapkeyTokenExchangeService.createJwtToken(principal.getId())
                        )
                    );
        } else {
            return Mono.just(Collections.singletonMap(
                    "externalToken", tapkeyTokenExchangeService.createJwtToken(principal.getId())
            ));
        }
    }
}
