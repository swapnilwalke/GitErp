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

import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.enums.UserRole;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the UserLogin domain object.
 * 
 * @author Virtusa Corporation
 */
public class SystemUserValidator implements Validator {
    
    /** Constant to represent minimum password length. */
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String USER_SYSTEMUSER_EMAIL_VALIDATOR = "USER.SYSTEMUSER.EMAIL.VALIDATOR";
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String USER_SYSTEMUSER_NAME_VALIDATOR = "USER.SYSTEMUSER.NAME.VALIDATOR";
    
    /** String constant to hold 'password'. */
    private static final String FIELD_NAME_PASSWORD = "password";
    
    /** The field name to check. */
    private static final String FIELD_NAME_LAST_NAME = "lastName";
    
    /** The field name to check. */
    private static final String FIELD_NAME_FIRST_NAME = "firstName";
    
    /** The field name to check. */
    private static final String FIELD_NAME_EMAIL = "email";
    
    /** The field name to check. */
    private static final String FIELD_NAME_USER_LOGIN_ID = "userLoginId";
    
    /** The field name to check. */
    private static final String FIELD_NAME_USER_IDENTIFICATION_NO = "userIdentificationNo";
    
    /** Error message for password too short. */
    private static final String USER_CREATE_USER_PASSWORD_TOO_SHORT_ERROR = "USER.CREATE.USER.PASSWORD.TOO.SHORT.ERROR";
    
    /** Error message for password same as user name and password. */
    private static final String USER_CREATE_USER_PASSWORD_SAME_AS_UNAME_OR_EMAIL_ERROR =
            "USER.CREATE.USER.PASSWORD.SAME.AS.UNAME.OR.EMAIL.ERROR";
    
    /** Error message for password length too short. */
    private static final String ERROR_MSG_FIRSTLASTNAME_ERROR = "REF.UI.USERLOGIN.FIRSTLASTNAME.ERROR";
    
    /** Error message for email. */
    private static final String ERROR_MSG_EMAIL_ERROR = "REF.UI.USERLOGIN.EMAIL.ERROR";
    
    /** Error message for invalid identification. */
    private static final String ERROR_MSG_INVALID_IDENTIFICATION = "REF.UI.USERLOGIN.INVALID.IDENTIFICATION";
    
    /**
     * Return whether or not this object can validate objects of the UserLogin class.
     * 
     * @param clazz - the class of the UserLogin
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return UserLogin.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the UserLogin.
     * 
     * @param target - Populated object of UserLogin to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object target, Errors errors) {

        UserLogin userLogin = (UserLogin) target;
        String emailPattern = ValidatorExpressionUtil.getValidatorPattern(USER_SYSTEMUSER_EMAIL_VALIDATOR);
        
        String namePattern = ValidatorExpressionUtil.getValidatorPattern(USER_SYSTEMUSER_NAME_VALIDATOR);
        String firstName = userLogin.getFirstName();
        String lastName = userLogin.getLastName();
        String password = userLogin.getPassword();
        String email = userLogin.getEmail();
        String userName = userLogin.getUsername();
        
        if (userLogin.getUsername().trim().isEmpty() || userLogin.getPassword().isEmpty()
                || userLogin.getEmail().trim().isEmpty() || userLogin.getUserRoleId() == 0
                || userLogin.getFirstName().trim().isEmpty() || userLogin.getLastName().trim().isEmpty()) {
            
            errors.rejectValue(FIELD_NAME_USER_LOGIN_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            
        } else if ((userLogin.getUserRoleId() == UserRole.ROLE_CLERICALSTAFF.getUserRoleId()
                || userLogin.getUserRoleId() == UserRole.ROLE_PARENT.getUserRoleId()
                || userLogin.getUserRoleId() == UserRole.ROLE_STUDENT.getUserRoleId() 
                || userLogin.getUserRoleId() == UserRole.ROLE_TEACHER
                .getUserRoleId()) && userLogin.getUserIdentificationNo().trim().isEmpty()) {
            errors.rejectValue(FIELD_NAME_USER_IDENTIFICATION_NO, ERROR_MSG_INVALID_IDENTIFICATION);
        } else {
            
            String userEmail = userLogin.getEmail();
            
            if (!userEmail.trim().isEmpty() && !userEmail.matches(emailPattern)) {
                errors.rejectValue(FIELD_NAME_EMAIL, ERROR_MSG_EMAIL_ERROR);
            }
            if (!firstName.trim().isEmpty() && !firstName.matches(namePattern)) {
                errors.rejectValue(FIELD_NAME_FIRST_NAME, ERROR_MSG_FIRSTLASTNAME_ERROR);
            }
            
            if (!lastName.trim().isEmpty() && !lastName.matches(namePattern)) {
                errors.rejectValue(FIELD_NAME_LAST_NAME, ERROR_MSG_FIRSTLASTNAME_ERROR);
                
            }
            
            // have minimum 8 characters
            if (password.trim().length() < MINIMUM_PASSWORD_LENGTH) {
                errors.rejectValue(FIELD_NAME_PASSWORD, USER_CREATE_USER_PASSWORD_TOO_SHORT_ERROR);
            }else if (!password.trim().isEmpty() && password.trim().equals(userName.trim())
                    || password.trim().equals(email.trim())) {
                errors.rejectValue(FIELD_NAME_PASSWORD, USER_CREATE_USER_PASSWORD_SAME_AS_UNAME_OR_EMAIL_ERROR);
            }
        }
    }
    
}
