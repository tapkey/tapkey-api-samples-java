package io.tapkey.developers.service;

import reactor.core.publisher.Mono;

public interface TapkeyManagementService {
    Mono<String> createContact(String userId);
    Mono<String> createGrant(String contactId, String boundLockId);
}
