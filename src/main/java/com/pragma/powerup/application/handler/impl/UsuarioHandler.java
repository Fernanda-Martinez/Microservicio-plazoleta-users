package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import com.pragma.powerup.application.dto.response.UserInfoResponseDto;
import com.pragma.powerup.application.handler.IEncoderHandler;
import com.pragma.powerup.application.handler.ITokenHandler;
import com.pragma.powerup.application.handler.IUsuarioHandler;
import com.pragma.powerup.application.mapper.IAuthenticationResponseMapper;
import com.pragma.powerup.application.mapper.IUsuarioRequestMapper;
import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.api.IValidatorServicePort;
import com.pragma.powerup.domain.model.Usuarios;
import com.pragma.powerup.infrastructure.exception.PasswordIncorrectException;
import com.pragma.powerup.infrastructure.exception.UsernameIncorrectException;
import com.pragma.powerup.infrastructure.exception.UsuarioNoEmpleado;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioHandler implements IUsuarioHandler {

    private final IUsuarioServicePort usuarioServicePort;
    private final IUsuarioRequestMapper usuarioRequestMapper;
    private final IEncoderHandler encoderHandler;
    private final IAuthenticationResponseMapper authenticationResponseMapper;
    private final ITokenHandler tokenHandler;
    private final UserDetailsService userDetailsService;
    private final IValidatorServicePort validatorServicePort;

    private static final String TOKEN_ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    private static final String TOKEN_EMPTY = "";
    @Override
    public AuthenticationResponseDto crear(CrearUsuarioRequestDto crearUsuarioRequestDto) {

        String tokenRole = tokenHandler.getTokenRole(crearUsuarioRequestDto.getToken());

        if(tokenRole.equals(TOKEN_ROLE_ANONYMOUS) && getRolName(crearUsuarioRequestDto.getIdRol()).equals("cliente")){
            crearUsuarioRequestDto.setClave(
                    encoderHandler.encodePassword(crearUsuarioRequestDto.getClave())
            );
            Usuarios user = usuarioRequestMapper.toUser(crearUsuarioRequestDto);
            usuarioServicePort.crear(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(
                    crearUsuarioRequestDto.getCorreo()
            );
            List<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return authenticationResponseMapper.toResponse(
                    tokenHandler.createToken(
                            userDetails.getUsername(), userDetails.getUsername(), roles
                    )
            );
        }

        if (validateRules(tokenRole, getRolName(crearUsuarioRequestDto.getIdRol()))
        ) {

            if(crearUsuarioRequestDto.getIdRol() == 3 && crearUsuarioRequestDto.getIdRestaurante() == null){
                throw new UsuarioNoEmpleado("El usuario debe tener un idRestaurante");
            }

            if(crearUsuarioRequestDto.getIdRol() != 3 && crearUsuarioRequestDto.getIdRestaurante() != null){
                throw new UsuarioNoEmpleado("El usuario no es un empleado, no debe llevar idRestaurante");
            }

            crearUsuarioRequestDto.setClave(
                    encoderHandler.encodePassword(crearUsuarioRequestDto.getClave())
            );
            Usuarios user = usuarioRequestMapper.toUser(crearUsuarioRequestDto);
            usuarioServicePort.crear(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(
                    crearUsuarioRequestDto.getCorreo()
            );
            List<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return authenticationResponseMapper.toResponse(
                    tokenHandler.createToken(
                            userDetails.getUsername(), userDetails.getUsername(), roles
                    )
            );
        }
        return authenticationResponseMapper.toResponse("La operacion no es valida porque no se cumplen los criterios de rol");
    }

    @Override
    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto)  {

        UserDetails userDetails;

        try{
            userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        }
        catch (UsernameNotFoundException e) {
            throw new UsernameIncorrectException("El usuario no existe");
        }

        if(encoderHandler.decodePassword(loginRequestDto.getPassword(), userDetails.getPassword()))
        {
            List<String> roles = userDetails
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return authenticationResponseMapper.toResponse(
                    tokenHandler.createToken(
                            userDetails.getUsername(), userDetails.getUsername(), roles
                    )
            );
        }else{
            throw new PasswordIncorrectException("La contraseña es incorrecta");
        }
    }

    @Override
    public boolean validateOwnerRole(int id) {
        return usuarioServicePort.validateOwnerRole(id);
    }

    @Override
    public boolean validateRestaurantEmployee(int idEmployee, int idRestaurant) {
        return usuarioServicePort.validateRestaurantEmployee(idEmployee, idRestaurant);
    }

    @Override
    public boolean validateAdminRole(int id) {
        return  usuarioServicePort.validateAdminRole(id);
    }

    @Override
    public boolean validateClientRole(int id) {
        return  usuarioServicePort.validateClientRole(id);
    }

    @Override
    public UserInfoResponseDto getUser(int idUsuario){
        Usuarios usuario = usuarioServicePort.getUser(idUsuario);

        UserInfoResponseDto responseDto = new UserInfoResponseDto();
        responseDto.setNombre(usuario.getNombre());
        responseDto.setTelefono(usuario.getCelular());
        responseDto.setEmail(usuario.getCorreo());

        return responseDto;
    }

    public boolean validateRules(String tokenRole, String requestRole){
        if(!validatorServicePort.rolesValidator(tokenRole, requestRole))
        {
            return false;
        }
        return true;
    }

    private String getRolName(int id) {
        switch (id) {
            case 1:
                return Constants.REGISTER_ROLE_ADMIN;
            case 2:
                return Constants.OWNER;
            case 3:
                return Constants.EMPLOYEE;
            default:
                return Constants.CLIENT;
        }
    }

}