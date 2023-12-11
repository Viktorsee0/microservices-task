package com.specificgroup.userservice.service;

import com.specificgroup.userservice.dto.Credentials;
import com.specificgroup.userservice.dto.UserDto;
import com.specificgroup.userservice.entity.User;
import com.specificgroup.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

        return userRepository.save(user);
    }

    @Override
    public User findUserBy(Credentials credentials) {
        String username = credentials.getUsername();

        Optional<String> optionalPassword = credentials.getPassword();
        if (optionalPassword.isPresent()) {
            String password = optionalPassword.get();
            return userRepository.findUserByUsernameAndPassword(username, password);
        }

        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
