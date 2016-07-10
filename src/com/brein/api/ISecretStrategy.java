package com.brein.api;

/**
 * Base class for the secret strategy
 */
public interface ISecretStrategy {

    String createSignature();
}
