package com.ahirajustice.app.ws.services.user;

import java.util.Optional;

import com.ahirajustice.app.ws.common.Utils;
import com.ahirajustice.app.ws.dtos.user.UserCreateDto;
import com.ahirajustice.app.ws.entities.User;
import com.ahirajustice.app.ws.repositories.IUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreateDto userDto) {
        User user = new User();

        User userExists = userRepository.findByEmail(userDto.getEmail());

        if (userExists != null) {
            throw new RuntimeException("User with email already exists");
        }

        BeanUtils.copyProperties(userDto, user);

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setEncryptedPassword(encryptedPassword);

        User createdUser = userRepository.save(user);

        return createdUser;
    }

    @Override
    public User getUser(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Could not find user with given email");
        }

        return user;
    }

    @Override
    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new RuntimeException("Could not find user with given email");
        }

        return user.get();
    }

}
