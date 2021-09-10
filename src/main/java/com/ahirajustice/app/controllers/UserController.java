package com.ahirajustice.app.controllers;

import java.util.List;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.exceptions.ValidationException;
import com.ahirajustice.app.services.user.IUserService;
import com.ahirajustice.app.validators.ValidatorUtils;
import com.ahirajustice.app.validators.user.UserCreateDtoValidator;
import com.ahirajustice.app.validators.user.UserUpdateDtoValidator;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserViewModel> getUsers() throws ForbiddenException {
        List<UserViewModel> users = userService.getUsers();
        return users;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) throws NotFoundException, ForbiddenException {
        UserViewModel user = userService.getUser(id);
        return user;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@RequestBody UserCreateDto userDto)
            throws BadRequestException, ValidationException {
        ValidatorUtils<UserCreateDto> validator = new ValidatorUtils<UserCreateDto>();
        validator.validate(new UserCreateDtoValidator(), userDto);

        UserViewModel createdUser = userService.createUser(userDto);
        return createdUser;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@PathVariable long id, @RequestBody UserUpdateDto userDto)
            throws BadRequestException, NotFoundException, ValidationException, ForbiddenException {
        ValidatorUtils<UserUpdateDto> validator = new ValidatorUtils<UserUpdateDto>();
        validator.validate(new UserUpdateDtoValidator(), userDto);

        if (id != userDto.getId()) {
            throw new BadRequestException("identifier mismatch");
        }

        UserViewModel updatedUser = userService.updateUser(userDto);
        return updatedUser;
    }

}
