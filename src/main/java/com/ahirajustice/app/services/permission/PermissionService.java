package com.ahirajustice.app.services.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IPermissionRepository;
import com.ahirajustice.app.services.user.IUserService;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    IUserService userService;

    @Autowired
    IPermissionRepository permissionRepository;

    @Override
    public List<PermissionViewModel> getPermissions() {
        List<PermissionViewModel> responses = new ArrayList<PermissionViewModel>();

        Iterable<Permission> permissions = permissionRepository.findAll();

        for (Permission permission : permissions) {
            PermissionViewModel response = new PermissionViewModel();
            BeanUtils.copyProperties(permission, response);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public PermissionViewModel getPermission(long id) throws NotFoundException {
        PermissionViewModel response = new PermissionViewModel();

        Optional<Permission> permissionExists = permissionRepository.findById(id);

        if (!permissionExists.isPresent()) {
            throw new NotFoundException(String.format("Permission with id: '%d' does not exist", id));
        }

        BeanUtils.copyProperties(permissionExists.get(), response);

        return response;
    }

    @Override
    public boolean authorize(Permission checkPermission) {
        User user = userService.getCurrentUser();

        Set<Permission> permissions = user.getRole().getPermissions();

        for (Permission permission : permissions) {
            if (checkPermission.getName().equals(permission.getName())) {
                return true;
            }
        }

        return false;
    }

}
