package io.tapkey.developers.controllers;

import io.tapkey.developers.auth.SampleUserPrincipal;
import io.tapkey.developers.service.ApplicationGrantDto;
import io.tapkey.developers.service.TapkeyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GrantsController {

    @Autowired
    private TapkeyManagementService tapkeyManagementService;

    /*
     * Returns the corresponding application-specific grants for the specified Tapkey grant IDs.
     */
    @GetMapping("/user/grants")
    public Mono<List<ApplicationGrantDto>> getGrantsForUser(Authentication authentication, @RequestParam String[] grantIds) {
        SampleUserPrincipal principal = (SampleUserPrincipal) authentication.getPrincipal();

        return tapkeyManagementService.getGrants(principal.getTapkeyContactId(), grantIds).map(tapkeyGrants -> tapkeyGrants
                .stream()
                .map(tapkeyGrant -> new ApplicationGrantDto(
                        principal.getFirstName(),
                        principal.getLastName(),
                        "Sample App Service",
                        "Sample Lock A",
                        "Building A, Level 3",
                        tapkeyGrant)
                )
                .collect(Collectors.toList()));
    }

}
