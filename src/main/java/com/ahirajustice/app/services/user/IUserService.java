package com.ahirajustice.app.services.user;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.entities.User;

public interface IUserService {

    User createUser(UserCreateDto userDto);

    User getUser(String email);

    User getUser(long id);

}
