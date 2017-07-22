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

import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the Nationality domain object.
 *
 * @author Virtusa Corporation
 */
public class NationalityValidator implements Validator {

    /**
     * Return whether or not this object can validate objects of the Nationality class.
     *
     * @param clazz - the class of the Nationality
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Nationality.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Nationality.
     *
     * @param target - Populated object of Nationality to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "REF.UI.NATIONALITY.DESCRIPTION.REQUIRED");
        Nationality nationality = (Nationality) target;

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFERENCE.NATIONALITY.VALIDATOR");
        Pattern stringOnly = Pattern.compile(validatorPattern);
        String description = nationality.getDescription().trim();
        description = description.replaceAll("( )+", " ");
        Matcher makeMatch = stringOnly.matcher(description);
        if (makeMatch.find()) {
            errors.rejectValue("description", "REF.UI.FIELD.TYPE");
        }
    }
}
