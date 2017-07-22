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

package com.virtusa.akura.student.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.StudentLeave;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This is a Validator for StudentLeave object which validates property description is null/empty and it
 * should contain only alphabet characters and space between words.
 *
 * @author Virtusa Corporation
 */
public class StudentLeaveValidator implements Validator {

    /** the field name to check. */
    private static final String FIELD_NAME = "reason";

    /** description can only have alphabet characters with space. */
    private static final String REGEX_PATTERN = "STUDENT.LEAVE.VALIDATOR.DESCRIPTION";

    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "studentLeaveId";

    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";

    /**
     * StudentDiscipline is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return StudentLeave.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        StudentLeave studentLeave = (StudentLeave) object;

        if (STRING_EMPTY.equals(studentLeave.getReason().trim()) || studentLeave.getFromDate() == null
                || studentLeave.getToDate() == null) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

        Pattern stringOnly = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN));
        Matcher makeMatch = stringOnly.matcher(studentLeave.getReason());
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
    }

}
