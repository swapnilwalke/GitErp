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

import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the Sport domain object.
 * 
 * @author Virtusa Corporation
 */
public class DonationTypeValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_DONATIONTYPE_VALIDATOR = "REFERENCE.DONATIONTYPE.VALIDATOR";

    /** attribute for holding space character. */
    private static final String STRING_SPACE = " ";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_FIELD_LENGTH = "REF.UI.FIELD.LENGTH";
    
    /** attribute for holding description. */
    private static final String DESCRIPTION = "description";
    
    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";
    
    /** constant to hold maximum length of description. */
    private static final int LENGTH_DESCRIPTION = 45;
    
    /**
     * Return whether or not this object can validate objects of the DonationType class.
     * 
     * @param clazz - the class of the DonationType
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {
    
        return Sport.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the DonationType.
     * 
     * @param object - Populated object of DonationType to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {
    
        ValidationUtils.rejectIfEmptyOrWhitespace(err, DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        DonationType donationType = (DonationType) object;
        Pattern stringOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_DONATIONTYPE_VALIDATOR));
        String descript = donationType.getDescription().trim();
        String description = descript.replaceAll(STRING_SPACE, STRING_EMPTY);
        Matcher makeMatch = stringOnly.matcher(description);
        
        if (makeMatch.find()) {
            
            err.rejectValue(DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }
        
        if (descript.length() > LENGTH_DESCRIPTION) {
            
            err.rejectValue(DESCRIPTION, REF_UI_FIELD_LENGTH);
        }
    }
}
