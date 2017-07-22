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

import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * The Class StudentScholarshipReportValidator.
 *
 * @author Virtusa Corporation
 */
public class StudentScholarshipReportValidator implements Validator {

    /** The Constant SCHOLARSHIP. */
    private static final String SCHOLARSHIP = "scholarship";

    /**
     * StudentScholarshipReportValidator is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return StudentScholarshipTemplate.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        StudentScholarshipTemplate studentScholarshipTemplate = (StudentScholarshipTemplate) object;

        if (studentScholarshipTemplate.getScholarship().equals(AkuraWebConstant.STRING_ZERO)) {
            errors.rejectValue(SCHOLARSHIP, AkuraWebConstant.SCHOLARSHIP_SELECT_ERROR_CODE);
        }
    }

}
