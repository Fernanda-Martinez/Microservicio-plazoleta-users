package com.pragma.powerup.domain.api;


public interface IValidatorServicePort {

    boolean rolesValidator(String tokenRole, String registerRole);
}