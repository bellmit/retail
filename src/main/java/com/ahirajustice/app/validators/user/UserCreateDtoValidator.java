package com.ahirajustice.app.validators.user;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import com.ahirajustice.app.dtos.user.UserCreateDto;

import br.com.fluentvalidator.AbstractValidator;

public class UserCreateDtoValidator extends AbstractValidator<UserCreateDto> {

    @Override
    public void rules() {
        ruleFor(UserCreateDto::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(UserCreateDto::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
        ruleFor(UserCreateDto::getFirstName)
                .must(not(stringEmptyOrNull())).withMessage("firstName is required").withFieldName("firstName");
        ruleFor(UserCreateDto::getLastName)
                .must(not(stringEmptyOrNull())).withMessage("lastName is required").withFieldName("lastName");
    }

}
