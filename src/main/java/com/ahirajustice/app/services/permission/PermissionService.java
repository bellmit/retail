package com.ahirajustice.app.services.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IPermissionRepository;
import com.ahirajustice.app.security.PermissionsProvider;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    IPermissionRepository permissionRepository;

    @Autowired
    IPermissionValidatorService permissionValidatorService;

    @Override
    public List<PermissionViewModel> getPermissions() throws ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_PERMISSIONS)) {
            throw new ForbiddenException();
        }

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
    public PermissionViewModel getPermission(long id) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_PERMISSION)) {
            throw new ForbiddenException();
        }

        PermissionViewModel response = new PermissionViewModel();

        Optional<Permission> permissionExists = permissionRepository.findById(id);

        if (!permissionExists.isPresent()) {
            throw new NotFoundException(String.format("Permission with id: '%d' does not exist", id));
        }

        BeanUtils.copyProperties(permissionExists.get(), response);

        return response;
    }

}
