package com.ahirajustice.app.controllers;

import java.util.List;

import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.services.permission.IPermissionService;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/permissions")
public class PermissionsController {

    @Autowired
    IPermissionService permissionService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<PermissionViewModel> getPermissions() {
        List<PermissionViewModel> permissions = permissionService.getPermissions();
        return permissions;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PermissionViewModel getPermission(@PathVariable long id) throws NotFoundException {
        PermissionViewModel permission = permissionService.getPermission(id);
        return permission;
    }

}
