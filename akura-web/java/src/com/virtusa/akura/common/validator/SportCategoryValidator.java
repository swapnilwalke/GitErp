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

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.SportCategory;

/**
 * This validator class validates the fields of the SportCategory domain object.
 * 
 * @author Virtusa Corporation
 */
public class SportCategoryValidator implements Validator {
    
    /** Error message for sport category. */
    private static final String ERROR_MSG_SPORTCATEGORY = "REF.UI.SPORTCATEGORY.REQUIRED";
    
    /** the common field name to check. */
    private static final String FIELD_NAME_SPORT = "sport";
    
    // /** the common field name to check. */
    // private static final String COMMON_FIELD_NAME = "subTermId";
    
    /**
     * Return whether or not this object can validate objects of the SportCategory class.
     * 
     * @param clazz - the class of the SportCategory
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> clazz) {
    
        return SportCategory.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the SportCategory.
     * 
     * @param object - Populated object of SportCategory to validate
     * @param err - Errors object that is building. May contain errors for the fields relating to types.
     */
    @Override
    public void validate(Object object, Errors err) {
    
        SportCategory sportCategory = (SportCategory) object;
        if (sportCategory.getSport().getSportId() == 0 || sportCategory.getSportSubCategory().getSportSubId() == 0) {
            err.rejectValue(FIELD_NAME_SPORT, ERROR_MSG_SPORTCATEGORY);
        }
    }
    
}
