package com.ahirajustice.app.security;

import java.util.HashSet;
import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.enums.Roles;

public class PermissionsProvider {

    // User permissions
    public static Permission CAN_VIEW_USER = new Permission("CAN_VIEW_USER");
    public static Permission CAN_VIEW_ALL_USERS = new Permission("CAN_VIEW_ALL_USERS", true);
    public static Permission CAN_CREATE_USER = new Permission("CAN_CREATE_USER");
    public static Permission CAN_CREATE_ADMIN_USER = new Permission("CAN_CREATE_ADMIN_USER", true);
    public static Permission CAN_CREATE_SUPER_ADMIN_USER = new Permission("CAN_CREATE_SUPER_ADMIN_USER", true);
    public static Permission CAN_UPDATE_USER = new Permission("CAN_UPDATE_USER");
    public static Permission CAN_UPDATE_ALL_USERS = new Permission("CAN_UPDATE_ALL_USERS", true);

    // Permission permissions
    public static Permission CAN_VIEW_PERMISSION = new Permission("CAN_VIEW_PERMISSION", true);
    public static Permission CAN_VIEW_ALL_PERMISSIONS = new Permission("CAN_VIEW_ALL_PERMISSIONS", true);

    // Role permissions
    public static Permission CAN_VIEW_ROLE = new Permission("CAN_VIEW_ROLE", true);
    public static Permission CAN_VIEW_ALL_ROLES = new Permission("CAN_VIEW_ALL_ROLES", true);
    public static Permission CAN_CREATE_ROLE = new Permission("CAN_CREATE_ROLE", true);
    public static Permission CAN_UPDATE_ROLE = new Permission("CAN_UPDATE_ROLE", true);
    public static Permission CAN_UPDATE_ALL_ROLES = new Permission("CAN_UPDATE_ALL_ROLES", true);

    public static Set<Permission> getAllPermissions() {
        Set<Permission> permissions = new HashSet<Permission>();

        // User permissions
        permissions.add(CAN_VIEW_USER);
        permissions.add(CAN_VIEW_ALL_USERS);
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_CREATE_ADMIN_USER);
        permissions.add(CAN_CREATE_SUPER_ADMIN_USER);
        permissions.add(CAN_UPDATE_USER);
        permissions.add(CAN_UPDATE_ALL_USERS);

        // Permission permissions
        permissions.add(CAN_VIEW_PERMISSION);
        permissions.add(CAN_VIEW_ALL_PERMISSIONS);

        // Role permissions
        permissions.add(CAN_VIEW_ROLE);
        permissions.add(CAN_VIEW_ALL_ROLES);
        permissions.add(CAN_CREATE_ROLE);
        permissions.add(CAN_UPDATE_ROLE);
        permissions.add(CAN_UPDATE_ALL_ROLES);

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

        permissions.add(CAN_VIEW_USER);
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_UPDATE_USER);

        return permissions;
    }

    private static Set<Permission> getAdminPermissions() {
        Set<Permission> permissions = new HashSet<Permission>();

        permissions.add(CAN_VIEW_USER);
        permissions.add(CAN_CREATE_USER);
        permissions.add(CAN_UPDATE_USER);

        return permissions;
    }

    private static Set<Permission> getSuperAdminPermissions() {
        return getAllPermissions();
    }

}
