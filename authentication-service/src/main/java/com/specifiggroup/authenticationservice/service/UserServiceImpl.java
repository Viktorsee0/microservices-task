package com.specifiggroup.authenticationservice.service;

import com.specifiggroup.authenticationservice.mapper.UserMapper;
import com.specifiggroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserRequestDTO;
import com.specifiggroup.authenticationservice.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersResource usersResource;
    private final UserMapper mapper;

    @Override
    public void createUser(RegistrationRequestDTO userDTO) {
        UserRepresentation userRepresentation = mapper.toRepresentation(userDTO);
        usersResource.create(userRepresentation);
    }

    @Override
    public UserResponseDTO getUser(String userName) {
        return mapper.toResponseDTO(usersResource.search(userName).get(0));
    }

    @Override
    public List<UserResponseDTO> getUsers() {
       return mapper.toResponseDTOS(usersResource.list());
    }

    @Override
    public void updateUser(String userId, UserRequestDTO userDTO) {
        UserRepresentation user = mapper.toRepresentation(userDTO);
        usersResource.get(userId).update(user);
    }

    @Override
    public void deleteUser(String userId) {
        usersResource.get(userId).remove();
    }
}
