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

package com.virtusa.akura.attendance.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.AttendanceDashboardDto;

/**
 * This validator class validates the fields of the AttendanceDashboard domain object.
 *
 * @author Virtusa Corporation
 */
public class AttendanceDashboardValidator implements Validator {

    /** the constant of error field to check. */
    private static final String REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** the field name to check grade. */
    private static final String GRADE_ID = "gradeId";

    /**
     * Return whether or not this object can validate objects of the Attendance Dashboard class.
     *
     * @param myClass - the class of the Parent
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> myClass) {

        return AttendanceDashboardDto.class.equals(myClass);
    }

    /**
     * Validates the object of the Attendance Dashboard.
     *
     * @param object - Populated object of Attendance Dashboard to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object object, Errors errors) {

        AttendanceDashboardDto temp = (AttendanceDashboardDto) object;

        if ((temp.getYear() == 0)) {
            errors.rejectValue(GRADE_ID, REF_UI_MANDATORY_FIELD_REQUIRED);
        }

    }

}
