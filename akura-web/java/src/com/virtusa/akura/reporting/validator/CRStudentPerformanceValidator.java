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

import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;

/**
 * class to validate a report of Student Performance.
 */
public class CRStudentPerformanceValidator implements Validator {

    /** variable for holding string less than. */
    private static final String VAR_LESS_THAN = "lessThan";

    /** variable for holding string error message. */
    private static final String ERROR_MSG_FORMAT_ERROR_LESSTHAN = "REF.UI.FORMAT.ERROR.LESSTHAN";

    /** variable for holding string error message. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** variable for holding string zero. */
    private static final String VAR_ZERO = "0";

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the CRStudentPerformanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        CRStudentPerformanceTemplate.class.equals(myClass);
        return false;
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of CRStudentPerformanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        CRStudentPerformanceTemplate cRStudentPerformanceTemplate = (CRStudentPerformanceTemplate) object;

        if (cRStudentPerformanceTemplate.getGradeDescriptionLess().equals(VAR_ZERO)) {
            errors.rejectValue(VAR_LESS_THAN, ERROR_MSG_MANDATORY_FIELD_REQUIRED);

        }

        if (cRStudentPerformanceTemplate.getLessThan() == 0.0 || cRStudentPerformanceTemplate.getLessThan() == 0
                || cRStudentPerformanceTemplate.getLessThan() < 0) {

            errors.rejectValue(VAR_LESS_THAN, ERROR_MSG_FORMAT_ERROR_LESSTHAN);

        }

    }

}
