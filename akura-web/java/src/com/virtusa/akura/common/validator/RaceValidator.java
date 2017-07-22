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

import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This class validates the fields of the Race domain object.
 *
 * @author Virtusa Corporation
 */
public class RaceValidator implements Validator {
    
    /** attribute for holding description. */
    private static final String DESCRIPTION = "description";

    /**
     * Return whether or not this object can validate objects of the Race class.
     *
     * @param clazz - the class of the Race
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Race.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Race.
     *
     * @param target - Populated object of Race to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, DESCRIPTION, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        Race race = (Race) target;

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFERENCE.RACE.VALIDATOR");
        Pattern stringOnly = Pattern.compile(validatorPattern);
        Matcher makeMatch = stringOnly.matcher(race.getDescription());
        if (makeMatch.find()) {
            errors.rejectValue(DESCRIPTION, AkuraWebConstant.MISMATCH_ERROR);
        }
        
        
        

    }
}
