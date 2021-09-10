package com.ahirajustice.app.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.ahirajustice.app.config.AppConfig;
import com.ahirajustice.app.constants.SecurityConstants;
import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.enums.Roles;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IRoleRepository;
import com.ahirajustice.app.repositories.IUserRepository;
import com.ahirajustice.app.security.PermissionsProvider;
import com.ahirajustice.app.services.permission.IPermissionValidatorService;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class UserService implements IUserService {

    @Autowired
    AppConfig appConfig;

    @Autowired
    HttpServletRequest request;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IPermissionValidatorService permissionValidatorService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserViewModel> getUsers() throws ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_USERS)) {
            throw new ForbiddenException();
        }

        List<UserViewModel> responses = new ArrayList<UserViewModel>();

        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            UserViewModel response = new UserViewModel();
            BeanUtils.copyProperties(user, response);
            response.setRole(user.getRole().getName());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public UserViewModel getUser(String email) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_USER)) {
            throw new ForbiddenException();
        }

        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findByEmail(email);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with email: '%s' does not exist", email));
        }

        BeanUtils.copyProperties(userExists.get(), response);
        response.setRole(userExists.get().getRole().getName());

        return response;
    }

    @Override
    public UserViewModel getUser(long id) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_USER)) {
            throw new ForbiddenException();
        }

        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        BeanUtils.copyProperties(userExists.get(), response);
        response.setRole(userExists.get().getRole().getName());

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
        Role userRole = roleRepository.findByName(Roles.USER.name()).get();
        user.setEncryptedPassword(encryptedPassword);
        user.setRole(userRole);

        User createdUser = userRepository.save(user);

        BeanUtils.copyProperties(createdUser, response);
        response.setRole(createdUser.getRole().getName());

        return response;
    }

    @Override
    public UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_USER)) {
            throw new ForbiddenException();
        }

        UserViewModel response = new UserViewModel();

        Optional<User> userExists = userRepository.findById(userDto.getId());

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", userDto.getId()));
        }

        User user = userExists.get();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        User updatedUser = userRepository.save(user);

        BeanUtils.copyProperties(updatedUser, response);

        return response;
    }

    @Override
    public Optional<User> getCurrentUser() {
        return userRepository.findByEmail(getUsernameFromToken());
    }

    private String getUsernameFromToken() {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null) {
            return null;
        }

        String token = header.split(" ")[1];
        String username = Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody()
                .getSubject();

        return username;
    }

}
