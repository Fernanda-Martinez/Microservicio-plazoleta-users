package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.AuthenticationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthenticationResponseMapper {

    AuthenticationResponseDto toResponse(String token);
}