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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the Exam domain object.
 *
 * @author Virtusa Corporation
 */

public class ExamValidator implements Validator {

    /** Represents the regex pattern. */
    private static final String REGEX_PATTERN = "REFERENCE.EXAM.DESCRIPTION.VALIDATOR";

    /** key to hold message when field type is miss matched. */
    private static final String REF_UI_EXAM_FIELD_INVALID = "REF.UI.EXAM.FIELD.INVALID";

    /** attribute for holding field name for display error massage. */
    private static final String DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the Exam class.
     *
     * @param clazz - the class of the Exam
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Exam.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Exam.
     *
     * @param target - Populated object of Exam to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        Exam exam = (Exam) target;
        String description = exam.getDescription().trim();

        if("".equals(description) || exam.getGradeId() <= 0){
           errors.rejectValue(DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

        Pattern stringOnly = Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REGEX_PATTERN));
        String descrip = description.replaceAll(" ", "");

        Matcher makeMatch = stringOnly.matcher(descrip);
        if (makeMatch.find()) {
            errors.rejectValue(DESCRIPTION, REF_UI_EXAM_FIELD_INVALID);
        }

    }
}
