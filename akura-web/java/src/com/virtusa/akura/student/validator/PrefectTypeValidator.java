/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the PrefectType domain object.
 *
 * @author Virtusa Corporation
 */
public class PrefectTypeValidator implements Validator {

    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String STUDENT_PREFECTTYPE_VALIDATOR = "STUDENT.PREFECTTYPE.VALIDATOR";

    /** key to hold message when mandatory field is missing. */
    private static final String REF_UI_PREFECTTYPE_DESCRIPTION_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** key to hold message when field type is miss matched. */
    private static final String REF_UI_PREFECT_FIELD_INVALID = "REF.UI.PREFECT.FIELD.INVALID";

    /** attribute for holding field name for display error massage. */
    private static final String DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the PrefectType class.
     *
     * @param clazz - the class of the PrefectType
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return PrefectType.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the PrefectType.
     *
     * @param target - Populated object of PrefectType to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        // required field
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DESCRIPTION, REF_UI_PREFECTTYPE_DESCRIPTION_REQUIRED);
        PrefectType prefectType = (PrefectType) target;
        String description = prefectType.getDescription().trim();

        Pattern stringOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(STUDENT_PREFECTTYPE_VALIDATOR));
        String descrip = description.replaceAll(" ", "");

        Matcher makeMatch = stringOnly.matcher(descrip);
        if (makeMatch.find()) {
            errors.rejectValue(DESCRIPTION, REF_UI_PREFECT_FIELD_INVALID);
        }
    }
}
