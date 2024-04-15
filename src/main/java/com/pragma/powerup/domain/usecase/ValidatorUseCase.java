package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IValidatorServicePort;

public class ValidatorUseCase implements IValidatorServicePort {
    @Override
    public boolean rolesValidator(String tokenRole, String registerRole) {
        return tokenRole.equals(Constants.TOKEN_ROLE_ADMIN) &&
                registerRole.equals(Constants.REGISTER_ROLE_ADMIN) ||
                tokenRole.equals(Constants.TOKEN_ROLE_ADMIN) &&
                        registerRole.equals(Constants.REGISTER_ROLE_OWNER) ||
                tokenRole.equals(Constants.TOKEN_ROLE_OWNER) &&
                        registerRole.equals(Constants.REGISTER_ROLE_EMPLOYEE);
    }
}
