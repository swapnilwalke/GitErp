/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This validator class validates the fields of the ForgotUsernameForm.
 *
 * @author Virtusa Corporation
 */

public class ForgotUserNameValidator implements Validator {

    /**
     * Specify the supportive java classes.
     *
     * @param clazz - the type specific argument.
     * @return boolean - return a boolean.
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return UserLogin.class.isAssignableFrom(clazz);
    }

    /**
     * The actual Validator method validates to make sure that the UserLogin Object passed is null
     * or empty.
     *
     * @param target - the target object pass in for validation.
     * @param errors - contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {

        UserLogin userLogin = (UserLogin) target;

        if (userLogin.getEmail().trim().isEmpty()) {
            errors.rejectValue(AkuraWebConstant.USER_NAME_LOW, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }

}
