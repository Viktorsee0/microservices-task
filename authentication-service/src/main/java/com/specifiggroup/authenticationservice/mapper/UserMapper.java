package com.specifiggroup.authenticationservice.mapper;

import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "credentials", expression  = "java(createPasswordCredentials(userDTO.getPassword()))")
    UserRepresentation toRepresentation(RegistrationRequestDTO userDTO);

    @Mapping(target = "username", source = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    UserRepresentation toRepresentation(UserRequestDTO userDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstname")
    @Mapping(source = "lastName", target = "lastname")
    UserResponseDTO toResponseDTO(UserRepresentation representation);

    default List<CredentialRepresentation> createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return List.of(passwordCredentials);
    }
}
