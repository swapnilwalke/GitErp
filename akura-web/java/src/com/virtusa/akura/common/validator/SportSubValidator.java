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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the SportSub domain object.
 * 
 * @author Virtusa Corporation
 */
public class SportSubValidator implements Validator {
    
    /** error message for sport sub type. */
    private static final String ERROR_SPORTSUB_TYPE = "REF.UI.FIELD.TYPE.SPORTSUB";
    
    /** error message for sport sub description. */
    private static final String ERROR_DESCRIPTION_REQUIRED = "REF.UI.DESCRIPTION.REQUIRED";
    
    /** the field name to check. */
    private static final String DESCRIPTION_EIELD = "description";
    
    /** String key to hold "REFERENCE.SPORTSSUB.VALIDATOR". */
    private static final String SPORTS_SUB_VALIDATOR =  "REFERENCE.SPORTSSUB.VALIDATOR";
    
    /**
     * Return whether or not this object can validate objects of the SportSub class.
     * 
     * @param clazz - the class of the SportSub
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {
    
        return SportSub.class.isAssignableFrom(clazz);
        
    }
    
    /**
     * Validates the object of the SportSub.
     * 
     * @param object - Populated object of SportSub to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {
    
        ValidationUtils.rejectIfEmptyOrWhitespace(err, DESCRIPTION_EIELD, ERROR_DESCRIPTION_REQUIRED);
        
        SportSub sportSub = (SportSub) object;
        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern(SPORTS_SUB_VALIDATOR);
        Pattern alphaNumericOnly = Pattern.compile(validatorPattern);

        String descript = sportSub.getDescription().trim();
        String description = descript.replaceAll(" ", "");
        Matcher makeMatch = alphaNumericOnly.matcher(description);
        
        if (makeMatch.find()) {
            err.rejectValue(DESCRIPTION_EIELD, ERROR_SPORTSUB_TYPE);
        }
    }
    
}
