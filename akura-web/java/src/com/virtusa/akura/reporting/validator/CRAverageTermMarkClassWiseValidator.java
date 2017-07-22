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

import com.virtusa.akura.api.dto.CRAverageTestMarkTemplate;

/**
 * class to validate student average test marks Class wise.
 */
public class CRAverageTermMarkClassWiseValidator implements Validator {

    /**
     * Represents the key for the error message.
     */
    private static final String MANDATORY_FIELD = "STQ.UI.MANDATORY.FIELD.REQUIRED";

    /**
     * Represents the name of the field.
     */
    private static final String GRADE_DESCRIPTION = "gradeDescription";

    /**
     * Represents the default value for the drop down.
     */
    private static final String DEFAULT_VALUE = "0";

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the CRAverageTestMarkTemplate
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        CRAverageTestMarkTemplate.class.equals(myClass);
        return false;
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of CRAverageTestMarkTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        CRAverageTestMarkTemplate cRAverageTestMarkTemplate = (CRAverageTestMarkTemplate) object;

        if (cRAverageTestMarkTemplate.getGradeDescription() == null
                || cRAverageTestMarkTemplate.getGradeDescription().equals(DEFAULT_VALUE)
                || cRAverageTestMarkTemplate.getTermDescription().equals(DEFAULT_VALUE)) {

            errors.rejectValue(GRADE_DESCRIPTION, MANDATORY_FIELD);

        }
    }

}
