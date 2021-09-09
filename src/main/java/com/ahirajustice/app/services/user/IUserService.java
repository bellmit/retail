package com.ahirajustice.app.services.user;

import java.util.List;
import java.util.Optional;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

public interface IUserService {

    List<UserViewModel> getUsers();

    UserViewModel getUser(String email) throws NotFoundException;

    UserViewModel getUser(long id) throws NotFoundException;

    UserViewModel createUser(UserCreateDto userDto) throws BadRequestException;

    UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException;

    Optional<User> getCurrentUser();

}
