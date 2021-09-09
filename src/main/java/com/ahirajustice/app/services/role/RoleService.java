package com.ahirajustice.app.services.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.app.dtos.role.RoleCreateDto;
import com.ahirajustice.app.dtos.role.RoleUpdateDto;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.repositories.IRoleRepository;
import com.ahirajustice.app.viewmodels.role.RoleViewModel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    public List<RoleViewModel> getRoles() {
        List<RoleViewModel> responses = new ArrayList<RoleViewModel>();

        Iterable<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            RoleViewModel response = new RoleViewModel();
            BeanUtils.copyProperties(role, response);
            responses.add(response);
        }

        return responses;
    }

    public RoleViewModel getRole(long id) throws NotFoundException {
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
        RoleViewModel response = new RoleViewModel();

        Role role = new Role();

        Optional<Role> roleExists = roleRepository.findByName(roleDto.getName());

        if (roleExists.isPresent()) {
            throw new BadRequestException(String.format("Role with name: '%s' already exists", roleDto.getName()));
        }

        BeanUtils.copyProperties(roleDto, role);

        Role createdRole = roleRepository.save(role);

        BeanUtils.copyProperties(createdRole, response);

        return response;
    }

    @Override
    public RoleViewModel updateRole(RoleUpdateDto roleDto) throws BadRequestException, ForbiddenException, NotFoundException {
        RoleViewModel response = new RoleViewModel();

        Optional<Role> roleExists = roleRepository.findById(roleDto.getId());

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", roleDto.getId()));
        }

        Role role = roleExists.get();

        role.setName(roleDto.getName());

        roleRepository.save(role);

        BeanUtils.copyProperties(role, response);

        return response;
    }

}
