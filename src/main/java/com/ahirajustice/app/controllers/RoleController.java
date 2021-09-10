package com.ahirajustice.app.controllers;

import java.util.List;

import com.ahirajustice.app.dtos.role.RoleCreateDto;
import com.ahirajustice.app.dtos.role.RoleUpdateDto;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.exceptions.ValidationException;
import com.ahirajustice.app.services.role.IRoleService;
import com.ahirajustice.app.validators.ValidatorUtils;
import com.ahirajustice.app.validators.role.RoleCreateDtoValidator;
import com.ahirajustice.app.validators.role.RoleUpdateDtoValidator;
import com.ahirajustice.app.viewmodels.role.RoleViewModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/roles")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<RoleViewModel> getRoles() throws ForbiddenException{
        List<RoleViewModel> roles = roleService.getRoles();
        return roles;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel getRole(@PathVariable long id) throws NotFoundException, ForbiddenException {
        RoleViewModel role = roleService.getRole(id);
        return role;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public RoleViewModel createRole(@RequestBody RoleCreateDto roleDto)
            throws BadRequestException, ForbiddenException, ValidationException {
        ValidatorUtils<RoleCreateDto> validator = new ValidatorUtils<RoleCreateDto>();
        validator.validate(new RoleCreateDtoValidator(), roleDto);

        RoleViewModel createdRole = roleService.createRole(roleDto);
        return createdRole;
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public RoleViewModel updateRole(@PathVariable long id, @RequestBody RoleUpdateDto roleDto)
            throws BadRequestException, ForbiddenException, NotFoundException, ValidationException {
        ValidatorUtils<RoleUpdateDto> validator = new ValidatorUtils<RoleUpdateDto>();
        validator.validate(new RoleUpdateDtoValidator(), roleDto);

        if (id != roleDto.getId()) {
            throw new BadRequestException("identifier mismatch");
        }

        RoleViewModel updatedRole = roleService.updateRole(roleDto);
        return updatedRole;
    }

}
