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

import com.virtusa.akura.api.dto.StudentSummaryReportTemplate;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * The Class StudentSummaryTemplateValidator.
 *
 * @author Virtusa Corporation
 */
public class StudentSummaryReportTemplateValidator implements Validator {

    /** The Constant EMPTY_STRING. */
    private static final String EMPTY_STRING = "";

    /** The Constant STUDENT_ADMISSION_NO. */
    private static final String STUDENT_ADMISSION_NO = "studentAdmissionNo";

    /**
     * StudentSummaryTemplate is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return StudentSummaryReportTemplate.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        StudentSummaryReportTemplate studentSummaryTemplate = (StudentSummaryReportTemplate) object;

        if (EMPTY_STRING.equals(studentSummaryTemplate.getStudentAdmissionNo().trim())) {
            errors.rejectValue(STUDENT_ADMISSION_NO, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }

}
