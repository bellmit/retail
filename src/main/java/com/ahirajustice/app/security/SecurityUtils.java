package com.ahirajustice.app.security;

import java.util.HashSet;
import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.enums.Roles;

public class SecurityUtils {

    // User permissions
    private static Permission CAN_CREATE_USER = new Permission("CAN_CREATE_USER");
    private static Permission CAN_CREATE_ADMIN_USER = new Permission("CAN_CREATE_ADMIN_USER", true);
    private static Permission CAN_CREATE_SUPER_ADMIN_USER = new Permission("CAN_CREATE_SUPER_ADMIN_USER", true);
    private static Permission CAN_UPDATE_USER = new Permission("CAN_UPDATE_USER");
    private static Permission CAN_UPDATE_ALL_USERS = new Permission("CAN_UPDATE_ALL_USERS", true);

    public static Set<Permission> getAllPermissions() {
        Set<Permission> permissions = new HashSet<Permission>();

        // User permissions
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_CREATE_ADMIN_USER);
        permissions.add(CAN_CREATE_SUPER_ADMIN_USER);
        permissions.add(CAN_UPDATE_USER);
        permissions.add(CAN_UPDATE_ALL_USERS);

        return permissions;
    }

    public static Set<Role> getDefaultRoles() {
        Set<Role> roles = new HashSet<Role>();

        Role user = new Role(Roles.USER.name());
        user.setPermissions(getUserPermissions());
        roles.add(user);

        Role admin = new Role(Roles.ADMIN.name());
        admin.setPermissions(getAdminPermissions());
        roles.add(admin);

        Role superAdmin = new Role(Roles.SUPERADMIN.name());
        superAdmin.setPermissions(getSuperAdminPermissions());
        roles.add(superAdmin);

        return roles;
    }

    private static Set<Permission> getUserPermissions() {
        Set<Permission> permissions = new HashSet<Permission>();

        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_UPDATE_USER);

        return permissions;
    }

    private static Set<Permission> getAdminPermissions() {
		Set<Permission> permissions = new HashSet<Permission>();

        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_UPDATE_USER);

        return permissions;
	}

	private static Set<Permission> getSuperAdminPermissions() {
		return getAllPermissions();
	}
	
}
