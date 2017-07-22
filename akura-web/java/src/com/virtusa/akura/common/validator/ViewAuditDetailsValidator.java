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
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the BusinessAudit domain object.
 * 
 * @author Virtusa Corporation
 */
public class ViewAuditDetailsValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_VIEWAUDITDETAILS_VALIDATOR = "REFERENCE.VIEWAUDITDETAILS.VALIDATOR";

    /** attribute for holding string toDate. */
    private static final String TO_DATE = "toDate";
       
    /** date pattern for date. */
    private static final String DATE_PATTERN = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";
    
    /**
     * Return whether or not this object can validate objects of the BusinessAudit class.
     * 
     * @param clazz - the class of the BusinessAudit
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return BusinessAudit.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the BusinessAudit.
     * 
     * @param target - Populated object of BusinessAudit to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {
    
        boolean hasError = false;
        BusinessAudit businessAudit = (BusinessAudit) target;
        
        Pattern stringOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_VIEWAUDITDETAILS_VALIDATOR));
        
        if (!businessAudit.getBusinessFunction().isEmpty()) {
            
            String businessFunction = businessAudit.getBusinessFunction();
            Matcher makeMatch = stringOnly.matcher(businessFunction);
            
            if (makeMatch.find()) {
                hasError = true;
                
            }
        }
        if (!businessAudit.getModule().isEmpty()) {
            
            String module = businessAudit.getModule();
            Matcher makeMatch = stringOnly.matcher(module);
            
            if (makeMatch.find()) {
                hasError = true;
                
            }
        }
        if (!businessAudit.getFromDate().isEmpty()) {
            Pattern datePattern = Pattern.compile(DATE_PATTERN);
            Matcher dateFromPatternMatcher = datePattern.matcher(businessAudit.getFromDate());
            if (!dateFromPatternMatcher.find()) {
                hasError = true;
                
            }
        }
        if (!businessAudit.getToDate().isEmpty()) {
            Pattern datePattern = Pattern.compile(DATE_PATTERN);
            Matcher dateFromPatternMatcher = datePattern.matcher(businessAudit.getToDate());
            if (!dateFromPatternMatcher.find()) {
                hasError = true;
                
            }
        }
        if (hasError) {
            errors.rejectValue(TO_DATE, AkuraWebConstant.MISMATCH_ERROR);
        }
        
    }
}
