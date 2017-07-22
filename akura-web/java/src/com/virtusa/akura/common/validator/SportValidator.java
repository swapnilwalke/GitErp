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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the Sport domain object.
 *
 * @author Virtusa Corporation
 */
public class SportValidator implements Validator {

    /** the field name to check. */
    private static final String DESCRIPTION = "description";

    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding space. */
    private static final String STRING_SPACE = " ";
    
    /** attribute for holding "REFERENCE.SPORTS.VALIDATOR". */
    private static final String SPORT_VALIDATOR_PATTERN = "REFERENCE.SPORTS.VALIDATOR";

    /**
     * Return whether or not this object can validate objects of the Sport class.
     *
     * @param clazz - the class of the Sport
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return Sport.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Sport.
     *
     * @param object - Populated object of Sport to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {

        ValidationUtils.rejectIfEmptyOrWhitespace(err, DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        Sport sport = (Sport) object;
        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern(SPORT_VALIDATOR_PATTERN);
        Pattern stringOnly = Pattern.compile(validatorPattern);
        String descript = sport.getDescription().trim();
        String description = descript.replaceAll(STRING_SPACE, STRING_EMPTY);
        Matcher makeMatch = stringOnly.matcher(description);
        if (makeMatch.find()) {
            err.rejectValue(DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }

    }

}
