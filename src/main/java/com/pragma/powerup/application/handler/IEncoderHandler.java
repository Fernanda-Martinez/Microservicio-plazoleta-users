package com.pragma.powerup.application.handler;

public interface IEncoderHandler {

    String encodePassword(String password);

    boolean decodePassword(String originalPassword, String hashPassword);
}