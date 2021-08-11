package com.ahirajustice.app.ws.services.user;

import com.ahirajustice.app.ws.common.Utils;
import com.ahirajustice.app.ws.dtos.user.UserCreateDto;
import com.ahirajustice.app.ws.entities.User;
import com.ahirajustice.app.ws.repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

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

}
