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

import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the StudyMedium domain object.
 *
 * @author Virtusa Corporation
 */
public class StudyMediumValidator implements Validator {

    /** Model attribute key name for massage. */
    private static final String PROPERTY = "studyMediumName";

    /** Model attribute key name for "REFERENCE.STUDYMEDIUM.VALIDATOR". */
    private static final String STUDYMEDIUM_VALIDATOR_PATTERN = "REFERENCE.STUDYMEDIUM.VALIDATOR";

    /** Model attribute key name for "REF.UI.MANDATORY.FIELD.REQUIRED". */
    private static final String REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** Model attribute key name for " ". */
    private static final String SPACE_STRING = " ";
    
    /** Model attribute key name for "". */
    private static final String EMPTY_STRING = "";

    /**
     * Return whether or not this object can validate objects of the StudyMedium class.
     *
     * @param clazz - the class of the District
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return StudyMedium.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the StudyMedium.
     *
     * @param target - Populated object of StudyMedium to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        StudyMedium studyMedium = (StudyMedium) target;
        String name = studyMedium.getStudyMediumName().trim();
        // required field
        if (name.equals(EMPTY_STRING)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, PROPERTY, REF_UI_MANDATORY_FIELD_REQUIRED);
        }

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern(STUDYMEDIUM_VALIDATOR_PATTERN);
        Pattern stringOnly = Pattern.compile(validatorPattern);

        name = name.replaceAll(SPACE_STRING, EMPTY_STRING);

        Matcher makeMatch = stringOnly.matcher(name);
        if (makeMatch.find()) {
            errors.rejectValue(PROPERTY, AkuraWebConstant.MISMATCH_ERROR);
        }

    }
}
