package com.ahirajustice.app.services.permission;

import java.util.List;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

public interface IPermissionService {

    List<PermissionViewModel> getPermissions();

    PermissionViewModel getPermission(long id) throws NotFoundException;

    boolean authorize(Permission permission);

}
