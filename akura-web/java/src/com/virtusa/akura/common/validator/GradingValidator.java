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

import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This is a Validator for Grading object which validates property description is null/empty and it should
 * contain only alphabet characters and space between words.
 *
 * @author Virtusa Corporation
 */
public class GradingValidator implements Validator {

    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_GRADING_VALIDATOR = "REFERENCE.GRADING.VALIDATOR";

    /** Error message for type mismatch. */
    private static final String ERROR_MSG_GRADING_TYPE_MISMATCH = "REF.UI.GRADING.TYPE.MISMATCH";

    /** the field name to check. */
    private static final String FIELD_NAME_DESCRIPTION = "description";

    /** the field name to check. */
    private static final String FIELD_NAME_GRADEACRONYM = "gradeAcronym";

    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "gradingId";

    /** description can only have alphanumeric characters with space. */
    private static final String REGEX_PATTERN_DESCRIPTION = "^[^A-Za-z0-9][^A-Za-z0-9-()<> ]*";



    /**
     * Grading is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return Grading.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        Grading grading = (Grading) object;

        if ("".equals(grading.getGradeAcronym()) || "".equals(grading.getDescription())) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

        Pattern descriptionPattern = Pattern.compile(REGEX_PATTERN_DESCRIPTION);
        Matcher descriptionMatch = descriptionPattern.matcher(grading.getDescription());

        Pattern acronymPattern =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_GRADING_VALIDATOR));
        Matcher acronymMatch = acronymPattern.matcher(grading.getGradeAcronym());
        if (descriptionMatch.find()) {
            errors.rejectValue(FIELD_NAME_DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }
        if (acronymMatch.find()) {
            errors.rejectValue(FIELD_NAME_GRADEACRONYM, ERROR_MSG_GRADING_TYPE_MISMATCH);
        }
    }

}
