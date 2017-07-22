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

import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This is a Validator for SubTerm object which validates property description is null/empty and it should
 * contain only alphabet characters and space between words.
 *
 * @author Virtusa Corporation
 */
public class SubTermValidator implements Validator {

    /** the field name to check. */
    private static final String FIELD_NAME = "description";

    /** the field name to check. */
    private static final String FIELD_FROM_MONTH = "fromMonth";

    /** description can only have alphabet characters with space. */
    private static final String REGEX_PATTERN = "[^A-Za-z0-9- ]";

    /** attribute for holding error message key. */
    private static final String ERROR_MSG_SAMEDATE = "REF.UI.TERM.PERIOD";

    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "subTermId";

    /** attribute for holding empty character. */
    private static final String STRING_EMPTY = "";

    /**
     * SubTerm is the class of the actual object instance that is to be validated.
     *
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return SubTerm.class.isAssignableFrom(clazz);
    }

    /**
     * Validate the supplied object.
     *
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {

        SubTerm subTerm = (SubTerm) object;

        if (STRING_EMPTY.equals(subTerm.getDescription()) || subTerm.getFromMonth() == null
                || subTerm.getToMonth() == null || subTerm.getTermId() == 0) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

        if (subTerm.getFromMonth() != null && subTerm.getToMonth() != null
                && subTerm.getFromMonth().compareTo(subTerm.getToMonth()) == 0) {
            errors.rejectValue(FIELD_FROM_MONTH, ERROR_MSG_SAMEDATE);
        }

        Pattern stringOnly = Pattern.compile(REGEX_PATTERN);
        Matcher makeMatch = stringOnly.matcher(subTerm.getDescription());
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
    }

}
