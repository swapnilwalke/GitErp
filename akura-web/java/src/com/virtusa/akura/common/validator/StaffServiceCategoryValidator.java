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

import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * Validator class validate Staff Service Category Object.
 * 
 * @author Virtusa Corporation
 */
public class StaffServiceCategoryValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_STAFFSERVICECATEGORY_VALIDATOR = "REFERENCE.STAFFSERVICECATEGORY.VALIDATOR";
    
    /** key to hold field name. */
    private static final String FIELD_NAME = "description";
    
    /**
     * Return whether or not this object can validate objects of the StaffServiceCategory class.
     * 
     * @param clazz - the class of the StaffServiceCategory
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return StaffServiceCategory.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the StaffServiceCategory.
     * 
     * @param object Validates the object of the StaffServiceCategory.
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {

        ValidationUtils.rejectIfEmptyOrWhitespace(err, FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);        
        StaffServiceCategory staffServiceCategory = (StaffServiceCategory) object;        
        Pattern stringOnly = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern
                                                    (REFERENCE_STAFFSERVICECATEGORY_VALIDATOR));       
        String description = staffServiceCategory.getDescription().trim();                
        Matcher makeMatch = stringOnly.matcher(description);
        
        if (makeMatch.find()) {
            err.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }   
    }
    
}
