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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the manageAssignment Form.
 *
 * @author Virtusa Corporation
 */

public class AssignmentValidator implements Validator {

    /** variable for error message description. */
    private static final String REF_UI_ASSIGNMENT_DESCRIPTION_REQUIRED = "REF.UI.ASSIGNMENT.DESCRIPTION.REQUIRED";

    /** variable for null value. */
    private static final String STRING_NULL = "";

    /**
     * The mandatory field error code is to display when mandatory fields not filled, interpretable as message
     * key.
     */
    public static final String MANDATORY_FIELD_ERROR_CODE = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** variable for zero value. */
    private static final String VAR_STRING = "0";

    /** the field name to check. */
    private static final String FIELD_NAME = "name";

    /** attribute for grade description. */
    private static final String ATTRIBUTE_GRADE_DESCRIPTION = "gradeDescription";

    /**
     * Specify the supportive java classes.
     *
     * @param clazz - the type specific argument.
     * @return boolean - return a boolean.
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Assignment.class.isAssignableFrom(clazz);
    }

    /**
     * The actual Assignment Validate method validates to make sure that the assignment Object passed is null
     * or empty.
     *
     * @param target - the target object pass in for validation.
     * @param errors - contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {

        Assignment assignments = (Assignment) target;

        if (assignments.getName().equals(STRING_NULL) || assignments.getDate() == null
                || assignments.getGradeDescription().equals(VAR_STRING)) {
            errors.rejectValue(ATTRIBUTE_GRADE_DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        } else {
            // remove white space in assignments
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_NAME, REF_UI_ASSIGNMENT_DESCRIPTION_REQUIRED);
        }

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFERENCE.ASSIGNMENT.VALIDATOR");

        Pattern stringPattern = Pattern.compile(validatorPattern);
        Matcher makeMatch = stringPattern.matcher(assignments.getName());

        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }

    }

}
