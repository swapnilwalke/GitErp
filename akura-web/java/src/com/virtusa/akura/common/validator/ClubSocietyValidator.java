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

import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the ClubSociety domain object.
 *
 * @author Virtusa Corporation
 */
public class ClubSocietyValidator implements Validator {

    /** Constant to represent maximum length of the description. */
    private static final int MAXIMUM_LENGTH_OF_DESCRIPTION = 250;
    /** Constant to represent regex expression define in ValidatorExpression.properties. */
    private static final String REFERENCE_CLUBANDSOCIETY_VALIDATOR = "REFERENCE.CLUBANDSOCIETY.VALIDATOR";
    /** key to hold field name. */
    private static final String FIELD_NAME = "name";
    
    /** key to hold description field name. */
    private static final String DESCRIPTION_FIELD_NAME = "description";

    /**
     * Return whether or not this object can validate objects of the ClubSociety class.
     *
     * @param clazz - the class of the ClubSociety
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return ClubSociety.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the ClubSociety.
     *
     * @param object - Populated object of ClubSociety to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {

        ValidationUtils.rejectIfEmptyOrWhitespace(err, FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        ClubSociety clubSociety = (ClubSociety) object;
        Pattern stringOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern(REFERENCE_CLUBANDSOCIETY_VALIDATOR));
        String name = clubSociety.getName().trim();
        // String strName = name.replaceAll(" ", "");
        Matcher makeMatch = stringOnly.matcher(name);
        if (makeMatch.find()) {
            err.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
        if(clubSociety.getDescription().length() > MAXIMUM_LENGTH_OF_DESCRIPTION) {
            err.rejectValue(DESCRIPTION_FIELD_NAME, AkuraWebConstant.DESCRIPTION_LENGTH_ERROR);
        }

    }

}
