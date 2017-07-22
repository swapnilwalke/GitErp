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
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the properties of the Subject domain object.
 * 
 * @author Virtusa Corporation
 */
public class SubjectValidator implements Validator {
    
    /** Property to hold maximum length of subject code. */
    private static final int MAXIMUM_LENGTH_SUBJECT_CODE = 5;
    
    /** Error message for field type. */
    private static final String ERROR_MSG_FIELD_TYPE = "REF.UI.FIELD.TYPE";
    
    /** Error message for maximum length of subject code. */
    private static final String ERROR_MSG_SUBJECT_CODE = "REF.UI.SUBJECT.CODE";
    
    /** Error message for description required. */
    private static final String ERROR_MSG_DESCRIPTION_REQUIRED = "REF.UI.DESCRIPTION.REQUIRED";
    
    /** Field name for description. */
    private static final String FIELD_NAME_DESCRIPTION = "description";
    
    /** Field name for subjectCode. */
    private static final String FIELD_NAME_SUBJECTCODE = "subjectCode";
    
    /** String key for "REFERENCE.SUBEJCT.VALIDATOR". */
    private static final String SUBEJCT_VALIDATOR_PATTERN = "REFERENCE.SUBEJCT.VALIDATOR";
    
    /** String Key for "REFERENCE.SUBEJCT.CODE.VALIDATOR". */
    private static final String SUBJECT_CODE_VALIDATOR_PATTERN = "REFERENCE.SUBEJCT.CODE.VALIDATOR";
    
    /** String key for "( )+". */
    private static final String SPECIAL_CHARACTER_STRING = "( )+";
    
    /** String key for " ". */
    private static final String SPACE_STRING = " ";
    
    /**
     * Return whether or not this object can validate objects of the Subject class.
     * 
     * @param clazz - the class of the Subject
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return Subject.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the Subject.
     * 
     * @param target - Populated object of Subject to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {
    
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_NAME_DESCRIPTION,
        // ERROR_MSG_DESCRIPTION_REQUIRED);
        Subject subject = (Subject) target;
        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern(SUBEJCT_VALIDATOR_PATTERN);
        Pattern stringOnly = Pattern.compile(validatorPattern);
        String description = subject.getDescription().trim();
        Matcher makeMatch = stringOnly.matcher(description);
        description = description.replaceAll(SPECIAL_CHARACTER_STRING, SPACE_STRING);
        
        String subjectCodeValidatorPattern =
                ValidatorExpressionUtil.getValidatorPattern(SUBJECT_CODE_VALIDATOR_PATTERN);
        Pattern subjectCodePattern = Pattern.compile(subjectCodeValidatorPattern);
        String subjectCode = subject.getSubjectCode().trim();
        Matcher subjectCodeMatcher = subjectCodePattern.matcher(subjectCode);
        
        if (subjectCode.length() == 0 || subject.getDescription().trim().isEmpty()) {
            errors.rejectValue(FIELD_NAME_SUBJECTCODE, ERROR_MSG_DESCRIPTION_REQUIRED);
        } else if (subjectCode.length() > MAXIMUM_LENGTH_SUBJECT_CODE) {
            errors.rejectValue(FIELD_NAME_SUBJECTCODE, ERROR_MSG_SUBJECT_CODE);
        } else if (subjectCodeMatcher.find()) {
            errors.rejectValue(FIELD_NAME_SUBJECTCODE, ERROR_MSG_FIELD_TYPE);
        } else if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME_DESCRIPTION, ERROR_MSG_FIELD_TYPE);
        }
        
    }
}
