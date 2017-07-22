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

package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.StaffProfileReportTemplate;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * The class StaffProfileReportValidator class.
 *
 * @author Virtusa Corporation
 */

public class StaffProfileReportValidator implements Validator {

    /** The Constant EMPTY_STRING. */
    private static final String EMPTY_STRING = "";

    /** The Constant STAFF_REG_NO. */
    private static final String STAFF_REG_NO = "staffRegNo";

    /**
     * StaffProfileReportTemplate is the class of the actual object instance that is to be validated.
     *
     * @param myClass - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> myClass) {

        return StaffProfileReportTemplate.class.equals(myClass);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of StaffProfileReportTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        StaffProfileReportTemplate staffProfileReportTemplate = (StaffProfileReportTemplate) object;

        if (EMPTY_STRING.equals(staffProfileReportTemplate.getStaffRegNo().trim())) {
            errors.rejectValue(STAFF_REG_NO, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
}
