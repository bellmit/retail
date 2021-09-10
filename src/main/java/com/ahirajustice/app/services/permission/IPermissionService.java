package com.ahirajustice.app.services.permission;

import java.util.List;

import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

public interface IPermissionService {

    List<PermissionViewModel> getPermissions() throws ForbiddenException;

    PermissionViewModel getPermission(long id) throws NotFoundException, ForbiddenException;

}
