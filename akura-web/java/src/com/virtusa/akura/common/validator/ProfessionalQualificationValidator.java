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

import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This is a Validator for ProfessionalQualification object which validates property description is null/empty
 * and it should contain only alphabet characters and space between words.
 * 
 * @author Virtusa Corporation
 */
public class ProfessionalQualificationValidator implements Validator {
    
    /** the field name to check. */
    private static final String FIELD_NAME = "description";
    
    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "professionalQualificationId";
    
    /** hold the key for string "REFERENCE.PROFESSIONALQUALIFICATION.VALIDATOR". */
    private static final String PROFESSIONALQUALIFICATION_VALIDATOR_PATTERN =
            "REFERENCE.PROFESSIONALQUALIFICATION.VALIDATOR";
    
    /**
     * ProfessionalQualification is the class of the actual object instance that is to be validated.
     * 
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return ProfessionalQualification.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validate the supplied object.
     * 
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        ProfessionalQualification professionalQualification = (ProfessionalQualification) object;
        
        if (AkuraConstant.EMPTY_STRING.equals(professionalQualification.getDescription().trim())) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
        
        String validatorPattern =
                ValidatorExpressionUtil.getValidatorPattern(PROFESSIONALQUALIFICATION_VALIDATOR_PATTERN);
        Pattern stringOnly = Pattern.compile(validatorPattern);
        Matcher makeMatch = stringOnly.matcher(professionalQualification.getDescription());
        
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
    }
    
}
