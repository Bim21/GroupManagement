package com.vti.testing.validate.account;

import com.vti.testing.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountUsernameNotExistValidation implements ConstraintValidator<AccountUsernameNotExists, String> {
    @Autowired
    private IAccountService accountService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (!StringUtils.hasLength(username))
            return true;
        return !accountService.isAccountExistsByUsername(username);
    }
}
