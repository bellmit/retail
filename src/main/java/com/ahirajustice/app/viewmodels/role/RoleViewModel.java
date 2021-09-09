package com.ahirajustice.app.viewmodels.role;

import java.util.HashSet;
import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.viewmodels.BaseViewModel;

public class RoleViewModel extends BaseViewModel {

    private String name;

    private Set<Permission> permissions = new HashSet<Permission>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
