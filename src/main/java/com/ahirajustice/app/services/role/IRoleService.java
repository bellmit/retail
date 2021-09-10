package com.ahirajustice.app.services.role;

import java.util.List;

import com.ahirajustice.app.dtos.role.RoleCreateDto;
import com.ahirajustice.app.dtos.role.RoleUpdateDto;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.viewmodels.role.RoleViewModel;

public interface IRoleService {

    List<RoleViewModel> getRoles() throws ForbiddenException;

    RoleViewModel getRole(long id) throws NotFoundException, ForbiddenException;

    RoleViewModel createRole(RoleCreateDto roleDto) throws BadRequestException, ForbiddenException;

    RoleViewModel updateRole(RoleUpdateDto roleDto) throws BadRequestException, ForbiddenException, NotFoundException;

}
