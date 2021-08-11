package com.ahirajustice.app.ws.services.user;

import com.ahirajustice.app.ws.dtos.user.UserCreateDto;
import com.ahirajustice.app.ws.entities.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    
    User createUser(UserCreateDto userDao);

}
