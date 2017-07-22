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

package com.virtusa.akura.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the District domain object.
 *
 * @author Virtusa Corporation
 */
public class DistrictValidator implements Validator {

    /** attribute for holding space character. */
    private static final String STRING_SPACE = " ";

    /** attribute for holding regular expression to replace. */
    private static final String STRING_REGEX = "( )+";

    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding description. */
    private static final String DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the District class.
     *
     * @param clazz - the class of the District
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return District.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the District.
     *
     * @param target - Populated object of District to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        District district = (District) target;

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFERENCE.DISTRICT.VALIDATOR");
        Pattern stringOnly = Pattern.compile(validatorPattern);
        String description = district.getDescription().trim();
        description = description.replaceAll(STRING_REGEX, STRING_SPACE);
        Matcher makeMatch = stringOnly.matcher(description);

        if ((district.getDescription().equals(STRING_EMPTY))) {

            errors.rejectValue(DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);

        } else {

            if (makeMatch.find()) {

                errors.rejectValue(DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
            }
        }
    }
}
