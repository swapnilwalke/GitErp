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

import com.virtusa.akura.api.dto.AnnualStudentAttendanceTemplate;
import com.virtusa.akura.common.AkuraWebConstant;


/**
 * The Class ClassWiseStudentReportTemplateValidator.
 *
 * @author Virtusa Corporation
 */
public class AnnualStudentAttendanceTemplateValidator implements Validator {

    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";
    
    /**
     * AnnualStudentAttendanceTemplate is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {
    
        return AnnualStudentAttendanceTemplate.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {
    
        AnnualStudentAttendanceTemplate annualStudentAttendanceTemplate = (AnnualStudentAttendanceTemplate) object;
        
        if (annualStudentAttendanceTemplate.getClassGradeId() == 0) {
            errors.rejectValue(CLASS_GRADE_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
    
}
