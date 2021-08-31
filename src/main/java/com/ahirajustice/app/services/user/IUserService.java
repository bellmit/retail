package com.ahirajustice.app.services.user;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.exceptions.BadRequestException;

public interface IUserService {

    User createUser(UserCreateDto userDto) throws BadRequestException;

    User getUser(String email);

    User getUser(long id);

}
