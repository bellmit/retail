package com.ahirajustice.app.services.permission;

import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.services.user.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    IUserService userService;

    @Override
    public boolean Authorize(Permission checkPermission) {
        User user = userService.getCurrentUser();

        Set<Permission> permissions = user.getRole().getPermissions();

        for (Permission permission : permissions) {
            if (checkPermission.getName().equals(permission.getName())){
                return true;
            }
        }

        return false;
    }

}
