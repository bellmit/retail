package com.ahirajustice.app.services.role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ahirajustice.app.dtos.role.RoleCreateDto;
import com.ahirajustice.app.dtos.role.RoleUpdateDto;
import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IPermissionRepository;
import com.ahirajustice.app.repositories.IRoleRepository;
import com.ahirajustice.app.security.PermissionsProvider;
import com.ahirajustice.app.services.permission.IPermissionValidatorService;
import com.ahirajustice.app.viewmodels.role.RoleViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IPermissionRepository permissionRepository;

    @Autowired
    IPermissionValidatorService permissionValidatorService;

    public List<RoleViewModel> getRoles() throws ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_ROLES)) {
            throw new ForbiddenException();
        }

        List<RoleViewModel> responses = new ArrayList<RoleViewModel>();

        Iterable<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            RoleViewModel response = new RoleViewModel();
            BeanUtils.copyProperties(role, response);
            responses.add(response);
        }

        return responses;
    }

    public RoleViewModel getRole(long id) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ROLE)) {
            throw new ForbiddenException();
        }

        RoleViewModel response = new RoleViewModel();

        Optional<Role> roleExists = roleRepository.findById(id);

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", id));
        }

        BeanUtils.copyProperties(roleExists.get(), response);

        return response;
    }

    @Override
    public RoleViewModel createRole(RoleCreateDto roleDto) throws BadRequestException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_CREATE_ROLE)) {
            throw new ForbiddenException();
        }

        RoleViewModel response = new RoleViewModel();

        Role role = new Role();

        Optional<Role> roleExists = roleRepository.findByName(roleDto.getName());

        if (roleExists.isPresent()) {
            throw new BadRequestException(String.format("Role with name: '%s' already exists", roleDto.getName()));
        }

        Set<Permission> permissions = new HashSet<Permission>();
        for (long permissionId : roleDto.getPermissionIds()) {
            permissions.add(permissionRepository.findById(permissionId).get());
        }

        BeanUtils.copyProperties(roleDto, role);
        role.setPermissions(permissions);

        Role createdRole = roleRepository.save(role);

        BeanUtils.copyProperties(createdRole, response);

        return response;
    }

    @Override
    public RoleViewModel updateRole(RoleUpdateDto roleDto)
            throws BadRequestException, ForbiddenException, NotFoundException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_ROLE)) {
            throw new ForbiddenException();
        }

        RoleViewModel response = new RoleViewModel();

        Optional<Role> roleExists = roleRepository.findById(roleDto.getId());

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", roleDto.getId()));
        }

        Role role = roleExists.get();

        Set<Permission> permissions = new HashSet<Permission>();
        for (long permissionId : roleDto.getPermissionIds()) {
            permissions.add(permissionRepository.findById(permissionId).get());
        }

        role.setName(roleDto.getName());
        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        BeanUtils.copyProperties(updatedRole, response);

        return response;
    }

}
