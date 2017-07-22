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

package com.virtusa.akura.user.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the UserRole domain object.
 * 
 * @author Virtusa Corporation
 */
public class UserRoleValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String USER_USERROLE_VALIDATOR = "USER.USERROLE.VALIDATOR";
    /** String constant for hold `role`. */
    private static final String ROLE_STR = "role";
    
    /**
     * Return whether or not this object can validate objects of the UserRole class.
     * 
     * @param clazz - the class of the UserRole
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {
    
        return UserRole.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the UserRole.
     * 
     * @param target - Populated object of UserRole to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {
    
        UserRole userRole = (UserRole) target;
        
        if (userRole.getRole().trim().isEmpty()) {
            
            errors.rejectValue(ROLE_STR, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            
        }
        
        String role = userRole.getRole();
        
        if (!role.trim().isEmpty()
                && !role.matches(ValidatorExpressionUtil.getValidatorPattern(USER_USERROLE_VALIDATOR))) {
            errors.rejectValue(ROLE_STR, AkuraWebConstant.USER_ROLE_INVALID);
        }
        
    }
    
}
