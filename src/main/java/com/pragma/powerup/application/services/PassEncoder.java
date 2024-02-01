package com.pragma.powerup.application.services;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
@EnableWebSecurity

public class PassEncoder {


    private PassEncoder() {
    }

    public static String passwordEncoder(String clave) {
            return BCrypt.hashpw(clave, BCrypt.gensalt());
        }

        public static Boolean verifyPassword(String clave, String claveEncriptada) {
            return BCrypt.checkpw(clave, claveEncriptada);
        }


}
