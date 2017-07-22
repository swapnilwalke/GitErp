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

package com.virtusa.akura.student.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This validator class validates the fields of the student FaithLife domain object.
 *
 * @author Virtusa Corporation
 */
public class FaithLifeRatingValidator implements Validator {

    /** key to hold string error code faithLifeRatingId. */
    private static final String FAITH_LIFE_RATING_ID = "faithLifeRatingId";

    /**
     * Return whether or not this object can validate objects of the FaithLifeRating class.
     *
     * @param clazz - the class of the FaithLifeRating
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return FaithLifeRating.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the FaithLifeRating.
     *
     * @param object - Populated object of FaithLifeRating to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {

        FaithLifeRating faithLifeRating = (FaithLifeRating) object;
        if (faithLifeRating.getFaithLifeComment().getFaithLifeCategory().getFaithLifeCategoryId() == 0
                || faithLifeRating.getFaithLifeGrading().getGradingId() == 0) {
            err.rejectValue(FAITH_LIFE_RATING_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }

}
