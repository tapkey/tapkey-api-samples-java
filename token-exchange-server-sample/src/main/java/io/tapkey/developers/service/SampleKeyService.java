package io.tapkey.developers.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class SampleKeyService implements KeyService {

    private final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    @Override
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }
}
