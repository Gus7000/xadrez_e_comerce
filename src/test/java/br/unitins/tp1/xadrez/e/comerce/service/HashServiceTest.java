package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HashServiceTest {

    @Test
    void shouldHashAndVerifyPassword() {
        HashService hashService = new HashService();

        String senhaHash = hashService.hash("123456");

        assertTrue(hashService.verify("123456", senhaHash));
        assertFalse(hashService.verify("outra-senha", senhaHash));
    }
}