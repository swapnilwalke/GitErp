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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the BloodGroup domain object.
 * 
 * @author Virtusa Corporation
 */
public class BloodGroupValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_BLOODGROUP_VALIDATOR = "REFERENCE.BLOODGROUP.VALIDATOR";

    /** Error message for field type. */
    private static final String ERROR_MSG_FIELD_TYPE = "REF.UI.FIELD.TYPE";
    
    /** Error message for description. */
    private static final String ERROR_MSG_DESCRIPTION = "REF.UI.BLOODGROUP.DESCRIPTION.REQUIRED";
    
    /** The field name to check. */
    private static final String FIELD_NAME_BLOOD_GROUP_ID = "bloodGroupId";
    
    /** The field name to check. */
    private static final String FIELD_NAME_DESCRIPTION = "description";
    
    /** String attribute for holding "-". */
    private static final String MINUS = "-";
    
    /** String attribute for holding "+". */
    private static final String PLUS = "+";
    
    /**
     * Return whether or not this object can validate objects of the BloodGroup class.
     * 
     * @param clazz - the class of the BloodGroup
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return BloodGroup.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the BloodGroup.
     * 
     * @param target - Populated object of BloodGroup to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {
    
        Matcher makeMatch = null;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_NAME_DESCRIPTION, ERROR_MSG_DESCRIPTION);
        BloodGroup bloodGroup = (BloodGroup) target;
        String description = bloodGroup.getDescription().trim();
        
        if (description.length() > 0) {
            
            if (!(description.endsWith(PLUS) || description.endsWith(MINUS))) {
                errors.rejectValue(FIELD_NAME_BLOOD_GROUP_ID, ERROR_MSG_FIELD_TYPE);
            }
            
            if (description.endsWith(PLUS) || description.endsWith(MINUS)) {
                String firstCharacters = description.substring(0, (description.length() - 1));
                
                if (firstCharacters.equals("")) {
                    errors.rejectValue(FIELD_NAME_DESCRIPTION, ERROR_MSG_FIELD_TYPE);
                } else {
                    Pattern stringOnly =
                            Pattern.compile(ValidatorExpressionUtil
                                    .getValidatorPattern(REFERENCE_BLOODGROUP_VALIDATOR));
                    makeMatch = stringOnly.matcher(firstCharacters);
                    if (makeMatch.find()) {
                        errors.rejectValue(FIELD_NAME_DESCRIPTION, ERROR_MSG_FIELD_TYPE);
                    }
                }
                
            }
        }
    }
}
