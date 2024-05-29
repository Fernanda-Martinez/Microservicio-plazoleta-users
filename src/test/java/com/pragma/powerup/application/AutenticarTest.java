package com.pragma.powerup.application;

import com.pragma.powerup.application.dto.request.CrearUsuarioRequestDto;
import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import com.pragma.powerup.application.handler.IEncoderHandler;
import com.pragma.powerup.application.handler.ITokenHandler;
import com.pragma.powerup.application.handler.impl.UsuarioHandler;
import com.pragma.powerup.application.mapper.IAuthenticationResponseMapper;
import com.pragma.powerup.application.mapper.IUsuarioRequestMapper;
import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.api.IValidatorServicePort;
import com.pragma.powerup.domain.model.Usuarios;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class AutenticarTest {

    @Mock
    private IUsuarioServicePort usuarioServicePort;

    @Mock
    private IUsuarioRequestMapper usuarioRequestMapper;

    @Mock
    private ITokenHandler tokenHandler;

    @Mock
    private IEncoderHandler encoderHandler;

    @Mock
    private IAuthenticationResponseMapper authenticationResponseMapper;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private IValidatorServicePort validatorServicePort;

    @InjectMocks
    private UsuarioHandler usuarioHandler;

    private static final String ACCESS_TOKEN_SECRET = "fa52f4e13bc9557eb253c02ed25d0ecaf9c37a3e";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 10800;
    private static final String NAME = "name";

    // validar que si creo un usuario propietario, la persona que llamo el servicio sera usuario administrador.
    @Test
    void testCrearPropietarioWithValidTokenAndRol() {

        //Obtener el token
        UserDetails userDetails = new User("admin@gmail.com", "123", List.of(new SimpleGrantedAuthority(Constants.TOKEN_ROLE_ADMIN)));


        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        int expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put(NAME, userDetails.getUsername());
        extra.put(Constants.ROLE, roles);

        String token = Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

//
        CrearUsuarioRequestDto requestDto = new CrearUsuarioRequestDto();
        requestDto.setToken(token);
        requestDto.setNombre("Maria");
        requestDto.setApellido("Martinez");
        requestDto.setNumeroDocumento(1002776730);
        requestDto.setCelular("3022074060");
        requestDto.setFechaNacimiento(new Date(2002, Calendar.FEBRUARY, 21));
        requestDto.setCorreo("propietario@gmail.com");
        requestDto.setClave("123");
        requestDto.setIdRol(2);

        when(userDetailsService.loadUserByUsername("propietario@gmail.com")).thenReturn(userDetails);


        Usuarios user = new Usuarios();
        user.setNombre("Maria");
        user.setApellido("Martinez");
        user.setNumeroDocumento(1002776730);
        user.setCelular("3022074060");
        user.setFechaNacimiento(new Date(2002 +1900, Calendar.FEBRUARY, 21));
        user.setCorreo("propietario@gmail.com");
        user.setClave("passwordEncriptada");
        user.setIdRol(2);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setToken("Token");

        // Configurar el comportamiento del mock de TokenHandler
        when(tokenHandler.createToken(anyString(), anyString(), anyList())).thenReturn("Token");
        when(tokenHandler.getTokenRole(token)).thenReturn("ROLE_administrador");
        when(validatorServicePort.rolesValidator("ROLE_administrador", "propietario")).thenReturn(true);
        when(authenticationResponseMapper.toResponse(any(String.class))).thenReturn(authenticationResponseDto);
        when(encoderHandler.encodePassword(anyString())).thenReturn("passwordEncriptada");
        when(usuarioRequestMapper.toUser(any(CrearUsuarioRequestDto.class))).thenReturn(user);

        AuthenticationResponseDto result = usuarioHandler.crear(requestDto);

        verify(usuarioRequestMapper).toUser(requestDto);
        verify(tokenHandler).getTokenRole(anyString());
        verify(usuarioServicePort).crear(user);

        assertEquals(2, user.getIdRol());
        assertEquals(requestDto.getCorreo(),user.getCorreo());
        assertNotNull(result);


    }

    //validar que si creo un empleado la persona que llamo el servicio sea usuario propietario
    @Test
    void testCrearEmpleadoWithValidTokenAndRol() {
    //Obtener el token
    UserDetails userDetails = new User("propietario@gmail.com", "123", List.of(new SimpleGrantedAuthority(Constants.TOKEN_ROLE_OWNER)));


    List<String> roles = userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    int expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
    Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

    Map<String, Object> extra = new HashMap<>();
        extra.put(NAME, userDetails.getUsername());
        extra.put(Constants.ROLE, roles);

    String token = Jwts
            .builder()
            .setSubject(userDetails.getUsername())
            .setExpiration(expirationDate)
            .addClaims(extra)
            .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
            .compact();

    //
    CrearUsuarioRequestDto requestDto = new CrearUsuarioRequestDto();
        requestDto.setToken(token);
        requestDto.setNombre("Juan");
        requestDto.setApellido("Perez");
        requestDto.setNumeroDocumento(1002776730);
        requestDto.setCelular("3022074060");
        requestDto.setFechaNacimiento(new Date(2002, Calendar.FEBRUARY, 21));
        requestDto.setCorreo("empleado@gmail.com");
        requestDto.setClave("123");
        requestDto.setIdRol(3);
        requestDto.setIdRestaurante(1);

    when(userDetailsService.loadUserByUsername("empleado@gmail.com")).thenReturn(userDetails);


    Usuarios user = new Usuarios();
        user.setNombre("Juan");
        user.setApellido("Perez");
        user.setNumeroDocumento(1002776730);
        user.setCelular("3022074060");
        user.setFechaNacimiento(new Date(2002 +1900, Calendar.FEBRUARY, 21));
        user.setCorreo("empleado@gmail.com");
        user.setClave("passwordEncriptada");
        user.setIdRol(3);
        user.setIdRestaurante(1);

    AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setToken("Token");

    // Configurar el comportamiento del mock de TokenHandler
    when(tokenHandler.createToken(anyString(), anyString(), anyList())).thenReturn("Token");
    when(tokenHandler.getTokenRole(token)).thenReturn("ROLE_propietario");
    when(validatorServicePort.rolesValidator("ROLE_propietario", "empleado")).thenReturn(true);
    when(authenticationResponseMapper.toResponse(any(String.class))).thenReturn(authenticationResponseDto);
    when(encoderHandler.encodePassword(anyString())).thenReturn("passwordEncriptada");
    when(usuarioRequestMapper.toUser(any(CrearUsuarioRequestDto.class))).thenReturn(user);

    AuthenticationResponseDto result = usuarioHandler.crear(requestDto);

    verify(usuarioRequestMapper).toUser(requestDto);
    verify(tokenHandler).getTokenRole(anyString());
    verify(usuarioServicePort).crear(user);

    assertEquals(requestDto.getIdRol(), user.getIdRol());
    assertEquals(requestDto.getCorreo(),user.getCorreo());
    assertNotNull(result);


    }

    //validar que si creo un cliente que la persona que llamo el servicio sea usuario anonimo
    @Test
    void testCrearClientel() {

        UserDetails userDetails = new User("anonimo@gmail.com", "123", List.of(new SimpleGrantedAuthority(Constants.CLIENT)));


        CrearUsuarioRequestDto requestDto = new CrearUsuarioRequestDto();
        requestDto.setToken("ROLE_ANONYMOUS");
        requestDto.setNombre("Pepito");
        requestDto.setApellido("Suarez");
        requestDto.setNumeroDocumento(1002987126);
        requestDto.setCelular("3022074060");
        requestDto.setFechaNacimiento(new Date(2002, Calendar.FEBRUARY, 21));
        requestDto.setCorreo("cliente@gmail.com");
        requestDto.setClave("123");
        requestDto.setIdRol(4);

        when(userDetailsService.loadUserByUsername("cliente@gmail.com")).thenReturn(userDetails);


        Usuarios user = new Usuarios();
        user.setNombre("Pepito");
        user.setApellido("Suarez");
        user.setNumeroDocumento(1002776730);
        user.setCelular("3022074060");
        user.setFechaNacimiento(new Date(2002 +1900, Calendar.FEBRUARY, 21));
        user.setCorreo("cliente@gmail.com");
        user.setClave("passwordEncriptada");
        user.setIdRol(4);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setToken("Token");

        // Configurar el comportamiento del mock de TokenHandler
        when(tokenHandler.createToken(anyString(), anyString(), anyList())).thenReturn("Token");
        when(tokenHandler.getTokenRole(requestDto.getToken())).thenReturn("ROLE_ANONYMOUS");
        when(authenticationResponseMapper.toResponse(any(String.class))).thenReturn(authenticationResponseDto);
        when(encoderHandler.encodePassword(anyString())).thenReturn("passwordEncriptada");
        when(usuarioRequestMapper.toUser(any(CrearUsuarioRequestDto.class))).thenReturn(user);

        AuthenticationResponseDto result = usuarioHandler.crear(requestDto);

        verify(usuarioRequestMapper).toUser(requestDto);
        verify(tokenHandler).getTokenRole(anyString());
        verify(usuarioServicePort).crear(user);

        assertEquals(requestDto.getIdRol(), user.getIdRol());
        assertEquals(requestDto.getCorreo(),user.getCorreo());
        assertNotNull(result);


    }
}







