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

import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the SpecialEvents domain object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventsValidator implements Validator {
    
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_MANAGESPECIALEVENT_VALIDATOR = "REFERENCE.MANAGESPECIALEVENT.VALIDATOR";

    /** the field name to check. */
    private static final String SPECIAL_EVENTS_ID = "specialEventsId";
    
    /** the field name to check. */
    private static final String FIELD_NAME = "name";
    
    /**
     * Return whether or not this object can validate objects of the SpecialEvents class.
     * 
     * @param clazz - the class of the SpecialEvents
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return SpecialEvents.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the SpecialEvents.
     * 
     * @param target - Populated object of SpecialEvents to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        SpecialEvents specialEvents = (SpecialEvents) target;
        
        if (specialEvents.getName().trim().equals("") || specialEvents.getDescription().trim().equals("")
                || specialEvents.getDate() == null
                || specialEvents.getParticipantCategory().getParticipantCategoryId() == 0) {
            
            errors.rejectValue(SPECIAL_EVENTS_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            
        }
        
        Pattern stringPattern =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_MANAGESPECIALEVENT_VALIDATOR));
        Matcher makeMatch = stringPattern.matcher(specialEvents.getName());
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
    }
}
