package com.ahirajustice.app.services.auth;

import java.util.ArrayList;
import java.util.Optional;

import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userExists = userRepository.findByEmail(email);

        if (!userExists.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        User user = userExists.get();

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(),
                new ArrayList<>());
    }

}
