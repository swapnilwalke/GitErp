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

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This validator class validates the fields of the Holiday domain object.
 * 
 * @author Virtusa Corporation
 */
public class HolidayValidator implements Validator {
    
    /** key to hold string code holidayId. */
    private static final String CODE_HOLIDAY_ID = "holidayId";
    
    
    /**
     * Returns whether or not this object can validate objects of the Holiday class.
     * 
     * @param clazz - the class of the Holiday
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Holiday.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the Holiday.
     * 
     * @param object - Populated object of Holiday to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {

        Holiday holiday = (Holiday) object;
        
        if (holiday.getStartDate() == null || holiday.getEndDate() == null
                || "".equals(holiday.getDescription().trim())) {
            err.rejectValue(CODE_HOLIDAY_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
    
}
