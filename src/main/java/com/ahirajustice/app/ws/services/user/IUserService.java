package com.ahirajustice.app.ws.services.user;

import com.ahirajustice.app.ws.dtos.user.UserCreateDto;
import com.ahirajustice.app.ws.entities.User;

public interface IUserService {

    User createUser(UserCreateDto userDao);

}
