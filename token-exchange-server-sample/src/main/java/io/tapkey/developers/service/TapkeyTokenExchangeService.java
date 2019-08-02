package io.tapkey.developers.service;

import reactor.core.publisher.Mono;

public interface TapkeyTokenExchangeService {
    Mono<String> createTapkeyUser(String userId);
    String createJwtToken(String userId);
}
