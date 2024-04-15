package com.pragma.powerup.application.handler;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public interface ITokenHandler {

    String createToken(String user, String email, List<String> role);

    UsernamePasswordAuthenticationToken getAuthenticationToken(String token);

    String getTokenRole(String token);
}