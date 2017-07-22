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

import com.virtusa.akura.api.dto.DisciplinaryActionClassWise;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates fields for Student disciplinary reports criteria,user input data.
 *
 * @author Virtusa Corporation
 */
public class DisciplinaryActionClassWiseValidator implements Validator {

    /** the parameter name to hold error message. */
    private static final String GRADE_DESCRIPTION = "gradeDescription";

    /**
     * Return whether object can validated or not.
     *
     * @param clazz - the class of the DisciplinaryActionClassWise
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return DisciplinaryActionClassWise.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param target - Populated object of DisciplinaryActionClassWise to validate
     * @param errors - contain errors related to fields.
     */
    @Override
    public void validate(Object target, Errors errors) {

        DisciplinaryActionClassWise disciplinaryActionObj = (DisciplinaryActionClassWise) target;

        if (disciplinaryActionObj.getGradeDescription().equals("0")) {

            errors.rejectValue(GRADE_DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

    }

}
