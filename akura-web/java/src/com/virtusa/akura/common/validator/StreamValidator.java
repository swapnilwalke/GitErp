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

import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the properties of the Stream domain object.
 *
 * @author Virtusa Corporation
 */
public class StreamValidator implements Validator {

    /** Field name for description. */
    private static final String FIELD_NAME_DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the Stream class.
     *
     * @param clazz - the class of the Stream
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Stream.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Stream.
     *
     * @param target - Populated object of Stream to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIELD_NAME_DESCRIPTION,
                AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        Stream stream = (Stream) target;

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFERENCE.STREAM.VALIDATOR");
        Pattern stringOnly = Pattern.compile(validatorPattern);
        Matcher makeMatch = stringOnly.matcher(stream.getDescription().trim());
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME_DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }
    }
}
