package com.specificgroup.authenticationservice.service;

import com.specificgroup.authenticationservice.mapper.UserMapper;
import com.specificgroup.authenticationservice.dto.RegistrationRequestDTO;
import com.specificgroup.authenticationservice.dto.UserRequestDTO;
import com.specificgroup.authenticationservice.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public UserResponseDTO getUserById(UUID id) {
        return mapper.toResponseDTO(usersResource.get(id.toString()).toRepresentation());
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
