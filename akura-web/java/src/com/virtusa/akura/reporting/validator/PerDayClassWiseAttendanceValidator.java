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

package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.PerDayAttendanceTemplate;

/**
 * class to validate Per Day Class Wise Attendance form.
 */
public class PerDayClassWiseAttendanceValidator implements Validator {
    
    /**
     * Return whether object can validated or not.
     * 
     * @param clazz - the class of the PerDayAttendanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return PerDayAttendanceTemplate.class.equals(clazz);
    }
    
    /** attribute for holding error message key. */
    public static final String MANDATORY_ERROR_MESSAGE = "STQ.UI.MANDATORY.FIELD.REQUIRED";
    
    /** attribute for holding attribute gradeDescription. */
    public static final String GRADE_DESCRIPTION = "gradeDescription";
    
    /** attribute for holding please select value. */
    public static final String PLEASE_SELECT = "0";
    
    /** attribute for holding empty value. */
    public static final String EMPTY = "";
    
    /**
     * Validates the user input for criteria.
     * 
     * @param object - Populated object of PerDayAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {
    
        PerDayAttendanceTemplate perDayAttendanceTemplate = (PerDayAttendanceTemplate) object;
        
        if (perDayAttendanceTemplate.getClassDescription().equals(PLEASE_SELECT)
                || perDayAttendanceTemplate.getDateConsidered().equals(EMPTY)) {
            errors.rejectValue(GRADE_DESCRIPTION, MANDATORY_ERROR_MESSAGE);
        }
        
    }
    
}
