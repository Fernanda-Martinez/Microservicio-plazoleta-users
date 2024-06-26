package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.api.IValidatorServicePort;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.domain.usecase.UsuarioUseCase;
import com.pragma.powerup.domain.usecase.ValidatorUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UsuarioJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Bean
    public IUsuarioPersistencePort usuarioPersistencePort(){
        return new UsuarioJpaAdapter(usuarioRepository, usuarioEntityMapper);
    }
    @Bean
    public IUsuarioServicePort usuarioServicePort(){
        return new UsuarioUseCase(usuarioPersistencePort());
    }

    @Bean
    public IValidatorServicePort validatorServicePort () {
        return new ValidatorUseCase();
    }
}