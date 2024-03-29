package com.ahirajustice.app.validators.auth;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import com.ahirajustice.app.dtos.auth.LoginDto;
import br.com.fluentvalidator.AbstractValidator;

public class LoginDtoValidator extends AbstractValidator<LoginDto> {

    @Override
    public void rules() {
        ruleFor(LoginDto::getEmail)
                .must(not(stringEmptyOrNull())).withMessage("email is required").withFieldName("email");
        ruleFor(LoginDto::getPassword)
                .must(not(stringEmptyOrNull())).withMessage("password is required").withFieldName("password");
    }

}
