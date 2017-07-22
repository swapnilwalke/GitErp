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

import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the properties of the ExamSubject domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamSubjectValidator implements Validator {

    /** key to hold field exam id. */
    private static final String EXAM_ID = "exam.examId";

    /**
     * Return whether or not this object can validate objects of the ExamSubject class.
     *
     * @param clazz - the class of the ExamSubject
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return ExamSubject.class.isAssignableFrom(clazz);
    }

    /**
     * Validates objects of the ExamSubject.
     *
     * @param target - Populated object of ExamSubject to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, EXAM_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        ExamSubject examSubject = (ExamSubject) target;
        if (examSubject.getExam().getExamId() == 0) {
            errors.rejectValue(EXAM_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
}
