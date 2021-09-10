package com.ahirajustice.app.services.permission;

import com.ahirajustice.app.entities.Permission;

public interface IPermissionValidatorService {

    boolean authorize(Permission permission);

}
