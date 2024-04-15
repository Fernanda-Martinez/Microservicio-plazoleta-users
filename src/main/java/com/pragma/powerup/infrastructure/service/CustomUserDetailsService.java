package com.pragma.powerup.infrastructure.service;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUsuarioRepository userRepository;
    private static final String NOT_FOUND_MESSAGE = "User not found";
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity userEntity = userRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_MESSAGE));
        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getCorreo())
                .password(userEntity.getClave())
                .roles(getRolName(userEntity.getIdRol()))
                .build();
    }


    private String getRolName(int id){
        switch (id) {
            case 1:
                return Constants.REGISTER_ROLE_ADMIN;
            case 2:
                return Constants.REGISTER_ROLE_OWNER;
            case 3:
                return Constants.REGISTER_ROLE_EMPLOYEE;
            default:
                return Constants.CLIENT;
        }
    }
}