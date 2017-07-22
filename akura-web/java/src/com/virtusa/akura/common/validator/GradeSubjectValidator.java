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

package com.virtusa.akura.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the properties of the GradeSubject domain object.
 *
 * @author Virtusa Corporation
 */
public class GradeSubjectValidator implements Validator {

    /** key to hold field grade id. */
    private static final String GRADE_ID = "grade.gradeId";

    /**
     * Return whether or not this object can validate objects of the GradeSubject class.
     *
     * @param clazz - the class of the GradeSubject
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return GradeSubject.class.isAssignableFrom(clazz);
    }

    /**
     * Validates objects of the GradeSubject.
     *
     * @param target - Populated object of GradeSubject to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, GRADE_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        GradeSubject gradeSubject = (GradeSubject) target;
        if (gradeSubject.getGrade().getGradeId() == 0) {
            errors.rejectValue(GRADE_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
}
