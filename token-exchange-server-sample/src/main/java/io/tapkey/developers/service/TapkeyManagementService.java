package io.tapkey.developers.service;

import reactor.core.publisher.Mono;

import java.util.List;

public interface TapkeyManagementService {
    Mono<String> createContact(String userId);
    Mono<String> createGrant(String contactId, String boundLockId);
    Mono<List<GrantDto>> getGrants(String contactId, String[] grantIds);
}
