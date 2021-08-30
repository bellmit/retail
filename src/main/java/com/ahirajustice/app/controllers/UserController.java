package com.ahirajustice.app.controllers;

import java.util.ArrayList;
import java.util.List;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.services.user.IUserService;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

import org.springframework.beans.BeanUtils;
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
    public List<UserViewModel> getUsers() {
        List<UserViewModel> users = new ArrayList<UserViewModel>();
        return users;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@PathVariable long id) {
        UserViewModel response = new UserViewModel();

        User user = userService.getUser(id);
        BeanUtils.copyProperties(user, response);

        return response;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@RequestBody UserCreateDto userDto) {
        UserViewModel response = new UserViewModel();

        User createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, response);

        return response;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@PathVariable long id, @RequestBody UserUpdateDto user) {
        return null;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {

    }
}
