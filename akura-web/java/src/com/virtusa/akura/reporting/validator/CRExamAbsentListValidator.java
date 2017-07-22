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

import com.virtusa.akura.api.dto.CRExamAbsentTemplate;

/**
 * class to validate Monthly Student Attendance.
 */
public class CRExamAbsentListValidator implements Validator {

    /** error message for mandatory field. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "STQ.UI.MANDATORY.FIELD.REQUIRED";

    /** variable for zero value. */
    private static final String VAR_STRING = "0";

    /** attribute for grade description. */
    private static final String ATTRIBUTE_GRADE_DESCRIPTION = "gradeDescription";

    /**
     * Return whether object can validated or not.
     *
     * @param clazz - the class of the CRExamAbsentTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return CRExamAbsentTemplate.class.equals(clazz);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of CRExamAbsentTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        CRExamAbsentTemplate cRExamAbsentTemplate = (CRExamAbsentTemplate) object;

        if (cRExamAbsentTemplate.getGradeDescription().equals(VAR_STRING)) {
            errors.rejectValue(ATTRIBUTE_GRADE_DESCRIPTION, ERROR_MSG_MANDATORY_FIELD_REQUIRED);
        }
    }

}
