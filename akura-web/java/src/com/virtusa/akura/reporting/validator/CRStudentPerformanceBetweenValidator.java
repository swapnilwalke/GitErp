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

import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;

/**
 * class to validate a report of Student Performance.
 */
public class CRStudentPerformanceBetweenValidator implements Validator {

    /** MAXIMUM MARKS. */
    private static final int MAX_MARKS = 100;

    /** error message for in between grater than field. */
    private static final String IN_BETWEEN_GREATER_VALUE = "inBetweenGreaterValue";

    /** error message for in between field. */
    private static final String REF_UI_FORMAT_ERROR_INBETWEEN = "REF.UI.FORMAT.ERROR.INBETWEEN";

    /** error message for mandotary field. */
    private static final String REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** variable for holding string zero. */
    private static final String VAR_ZERO = "0";

    /** error message for in between less and grater than field. */
    private static final String ERROR_MSG_FORMAT_ERROR_INBETWEEN_LESS_GRATER =
            "REF.UI.FORMAT.ERROR.INBETWEEN.LESS.GRATERTHAN";

    /** error message for in between less and grater than negetive field. */
    private static final String ERROR_MSG_FORMAT_ERROR_INBETWEEN_LESS_GRATER_NEGETIVE =
            "REF.UI.FORMAT.ERROR.INBETWEEN.LESS.GRATERTHAN.NEGETIVE";

    /** error message for in between less and grater than maximum marks. */
    private static final String ERROR_MSG_FORMAT_ERROR_THANHUNDARD =
            "REF.UI.FORMAT.ERROR.INBETWEEN.LESS.GRATERTHAN.MAXIMUM.MARKS";

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the CRStudentPerformanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        CRStudentPerformanceTemplate.class.equals(myClass);
        return false;
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of CRStudentPerformanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        CRStudentPerformanceTemplate cRStudentPerformanceTemplate = (CRStudentPerformanceTemplate) object;

        if (cRStudentPerformanceTemplate.getGradeDescription().equals(VAR_ZERO)
                || cRStudentPerformanceTemplate.getTerm().equals(VAR_ZERO)) {
            errors.rejectValue(IN_BETWEEN_GREATER_VALUE, REF_UI_MANDATORY_FIELD_REQUIRED);

        } else if (cRStudentPerformanceTemplate.getInBetweenLessValue() == 0
                && cRStudentPerformanceTemplate.getInBetweenGreaterValue() == 0) {

            errors.rejectValue(IN_BETWEEN_GREATER_VALUE, ERROR_MSG_FORMAT_ERROR_INBETWEEN_LESS_GRATER);

        } else

        if (cRStudentPerformanceTemplate.getInBetweenLessValue() < 0
                || cRStudentPerformanceTemplate.getInBetweenGreaterValue() < 0) {
            errors.rejectValue(IN_BETWEEN_GREATER_VALUE, ERROR_MSG_FORMAT_ERROR_INBETWEEN_LESS_GRATER_NEGETIVE);
        } else if (cRStudentPerformanceTemplate.getInBetweenLessValue() >= cRStudentPerformanceTemplate
                .getInBetweenGreaterValue()) {
            errors.rejectValue(IN_BETWEEN_GREATER_VALUE, REF_UI_FORMAT_ERROR_INBETWEEN);
        } else if (cRStudentPerformanceTemplate.getInBetweenLessValue() > MAX_MARKS
                || cRStudentPerformanceTemplate.getInBetweenGreaterValue() > MAX_MARKS) {

            errors.rejectValue(IN_BETWEEN_GREATER_VALUE, ERROR_MSG_FORMAT_ERROR_THANHUNDARD);
        }

    }
}
