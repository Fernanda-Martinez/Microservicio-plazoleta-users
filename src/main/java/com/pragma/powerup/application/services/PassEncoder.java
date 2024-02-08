package com.pragma.powerup.application.services;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Configuration
@EnableWebSecurity
@Service
@NoArgsConstructor

public class PassEncoder {

    public static String passwordEncoder(String clave) {
            return BCrypt.hashpw(clave, BCrypt.gensalt());
        }

        public static Boolean verifyPassword(String clave, String claveEncriptada) {
            return BCrypt.checkpw(clave, claveEncriptada);
        }


}
