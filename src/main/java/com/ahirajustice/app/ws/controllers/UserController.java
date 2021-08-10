package com.ahirajustice.app.ws.controllers;

import java.util.ArrayList;
import java.util.List;

import com.ahirajustice.app.ws.dtos.user.UserCreateDto;
import com.ahirajustice.app.ws.dtos.user.UserUpdateDto;
import com.ahirajustice.app.ws.viewmodels.user.UserViewModel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UserViewModel> getUsers() {
        List<UserViewModel> users = new ArrayList<UserViewModel>();
        return users;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel getUser(@RequestParam long id) {
        return null;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewModel createUser(@RequestBody UserCreateDto user) {
        return null;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public UserViewModel updateUser(@RequestParam long id, @RequestBody UserUpdateDto user) {
        return null;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam long id) {

    }
}
