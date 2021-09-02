package com.ahirajustice.app.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IUserRepository;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserViewModel> getUsers() {
        List<UserViewModel> responses = new ArrayList<UserViewModel>();

        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            UserViewModel response = new UserViewModel();
            BeanUtils.copyProperties(user, response);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public UserViewModel getUser(String email) throws NotFoundException {
        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findByEmail(email);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with email: '%s' does not exist", email));
        }

        BeanUtils.copyProperties(userExists.get(), response);

        return response;
    }

    @Override
    public UserViewModel getUser(long id) throws NotFoundException {
        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        BeanUtils.copyProperties(userExists.get(), response);

        return response;
    }

    @Override
    public UserViewModel createUser(UserCreateDto userDto) throws BadRequestException {
        UserViewModel response = new UserViewModel();

        User user = new User();

        Optional<User> userExists = userRepository.findByEmail(userDto.getEmail());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with email: '%s' already exists", userDto.getEmail()));
        }

        BeanUtils.copyProperties(userDto, user);

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setEncryptedPassword(encryptedPassword);

        User createdUser = userRepository.save(user);

        BeanUtils.copyProperties(createdUser, response);

        return response;
    }

    @Override
    public UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException {
        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findById(userDto.getId());

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", userDto.getId()));
        }

        User user = userExists.get();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        userRepository.save(user);

        BeanUtils.copyProperties(user, response);

        return response;
    }

}
