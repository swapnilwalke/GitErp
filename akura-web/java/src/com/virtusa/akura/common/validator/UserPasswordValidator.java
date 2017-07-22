/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import com.virtusa.akura.api.dto.WrapperChangePassword;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation. UserPasswordValidator validates the password entered by the user in the change
 *         password.
 */
public class UserPasswordValidator implements Validator {
    
    /** constant for String "USER.UI.MANDATORY.FIELD.REQUIRED". */
    private static final String USER_UI_MANDATORY_FIELD_REQUIRED = "USER.UI.MANDATORY.FIELD.REQUIRED";
    
    /** constant for String "oldPassword". */
    private static final String OLD_PASSWORD = "oldPassword";
    
    /** Constant to represent minimum password length. */
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    /** Error message for password too short. */
    private static final String USER_CREATE_USER_PASSWORD_TOO_SHORT_ERROR = "USER.CREATE.USER.PASSWORD.TOO.SHORT.ERROR";
    
    /** Error message for password same as user name and password. */
    private static final String USER_CREATE_USER_PASSWORD_SAME_AS_UNAME_OR_EMAIL_ERROR =
            "USER.CREATE.USER.PASSWORD.SAME.AS.UNAME.OR.EMAIL.ERROR";
    
    /**
     * Return whether or not this object can validate objects of the WrapperChangePassword class.
     * 
     * @param clazz - the class of the WrapperChangePassword
     * @return {@link Boolean} true if this class can validate WrapperChangePassword.
     */
    public boolean supports(Class<?> clazz) {

        return WrapperChangePassword.class.isAssignableFrom(clazz);
    }
    
    /**
     * validate the WrapperChangePassword.
     * 
     * @param object WrapperChangePassword object to be validated.
     * @param errors Error object to bind the errors in validation.
     */
    public void validate(Object object, Errors errors) {

        WrapperChangePassword wrapperChangePassword = (WrapperChangePassword) object;
        
        if (wrapperChangePassword != null) {
            String oldPassword = wrapperChangePassword.getOldPassword();
            String newPassword = wrapperChangePassword.getNewPassword();
            String confirmPassword = wrapperChangePassword.getConfirmPassword();
            String username = wrapperChangePassword.getUsername();
            String email = wrapperChangePassword.getEmail();
            
            String[] passwordList = { oldPassword, newPassword, confirmPassword };
            
            boolean isMandatoryFieldsFilled = true;
            
            for (String password : passwordList) {
                if (password == null || password.trim().equals(AkuraConstant.EMPTY_STRING)) {
                    errors.rejectValue(OLD_PASSWORD, USER_UI_MANDATORY_FIELD_REQUIRED);
                    isMandatoryFieldsFilled = false;
                    break;
                }
            }
            
            
            // have minimum 8 characters
            if (isMandatoryFieldsFilled && newPassword.trim().length() < MINIMUM_PASSWORD_LENGTH) {
                errors.rejectValue(OLD_PASSWORD, USER_CREATE_USER_PASSWORD_TOO_SHORT_ERROR);
            }else if (isMandatoryFieldsFilled && !newPassword.trim().isEmpty() && 
                    newPassword.trim().equals(username.trim()) || newPassword.trim().equals(email.trim())) {
                errors.rejectValue(OLD_PASSWORD, USER_CREATE_USER_PASSWORD_SAME_AS_UNAME_OR_EMAIL_ERROR);
            }
            
        }
        
    }
    
}
