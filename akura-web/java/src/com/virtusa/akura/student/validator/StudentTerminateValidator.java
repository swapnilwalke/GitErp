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

package com.virtusa.akura.student.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.PastStudent;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the StaffPastService domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentTerminateValidator implements Validator {
    
    /** the error binding attribute . */
    private static final String REASON = "pastStudent.student.studentId";
    
    /** variable for date to. */
    private static final String ATT_DATE_TO = "pastStudent.student.studentId";
    
    /** error message for date field incorrect. */
    private static final String ERROR_MSG_DATE_FIELD_INCORRECT_DATE = "REF.UI.DATE.FIELD.INCORRECT_DATE";
    
    /**
     * Return whether or not this object can validate objects of the StaffPastService class.
     * 
     * @param clazz - the class of the StaffPastService
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return PastStudent.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the PastStudent.
     * 
     * @param object - Populated object of PastStudent to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object object, Errors errors) {

        PastStudent pastStudent = (PastStudent) object;
        
        if (pastStudent.getReason() == "" || pastStudent.getDateOfDeparture() == null
                || !(pastStudent.getCompleteClearance())) {
            errors.rejectValue(REASON, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        } else if (pastStudent.getDateOfDeparture() != null) {
            if ((pastStudent.getStudent().getFirstSchoolDay()).after((pastStudent.getDateOfDeparture()))) {
                errors.rejectValue(ATT_DATE_TO, ERROR_MSG_DATE_FIELD_INCORRECT_DATE);
            }
        }
        
    }
}
