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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * class to validate a report of Student wise attendance form.
 */
public class StudentWiseAttendanceValidator implements Validator {

    /** error message for date field invalid. */
    private static final String ERROR_MSG_DATE_FIELD_INCORRECT = "REF.UI.DATE.FIELD.INCORRECT";

    /** variable for date to. */
    private static final String DATE_TO = "dateTo";

    /** error message for date field invalid. */
    private static final String ERROR_MSG_DATE_FIELD_INVALID = "REF.UI.DATE.FIELD.INVALID";

    /** variable for date from. */
    private static final String DATE_FROM = "dateFrom";

    /** variable for date format. */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /** variable for date pattern. */
    private static final String DATE_PATTERN = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";

    /** error message for admission field invalid. */
    private static final String ERROR_MSG_ADMISSION_FIELD_INVALID = "REF.UI.ADMISSION.FIELD.INVALID";

    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO = "REF.UI.DATE.FIELD.FUTURE.TO.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM = "REF.UI.DATE.FIELD.FUTURE.FROM.INVALID";

    /** variable for student id. */
    private static final String STUDENT_ID = "studentID";

    /** variable for string null. */
    private static final String STRING_NULL = "";

    /** variable for zero value. */
    private static final String VAR_STRING = "0";

    /**
     * property dateTo type Date. used to track To date.
     */
    private Date dateTo;

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the StudentWiseAttendanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        return StudentWiseAttendanceTemplate.class.equals(myClass);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of StudentWiseAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        Date dateFrom = null;

        StudentWiseAttendanceTemplate studentWiseAttendanceTemplate = (StudentWiseAttendanceTemplate) object;

        if (studentWiseAttendanceTemplate.getStudentID().equals(VAR_STRING)
                || studentWiseAttendanceTemplate.getDateFrom().equals(STRING_NULL)
                || studentWiseAttendanceTemplate.getDateTo().equals(STRING_NULL)) {
            errors.rejectValue(STUDENT_ID, ERROR_MSG_MANDATORY_FIELD_REQUIRED);
        }

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFORTING.STUDENTWISE.VALIDATOR");

        Pattern studentIDPattern = Pattern.compile(validatorPattern);

        Matcher studentIDMatcher = studentIDPattern.matcher(studentWiseAttendanceTemplate.getStudentID());
        if (!studentWiseAttendanceTemplate.getStudentID().equals(STRING_NULL) && !studentIDMatcher.find()) {

            errors.rejectValue(STUDENT_ID, ERROR_MSG_ADMISSION_FIELD_INVALID);
        }

        Pattern datePattern = Pattern.compile(DATE_PATTERN);
        Matcher dateFromPatternMatcher = datePattern.matcher(studentWiseAttendanceTemplate.getDateFrom());

        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        if (!studentWiseAttendanceTemplate.getDateFrom().equals(STRING_NULL)) {
            if (!dateFromPatternMatcher.find()) {
                errors.rejectValue(DATE_FROM, ERROR_MSG_DATE_FIELD_INVALID);
                dateFrom = null;
            } else {
                try {
                    dateFrom = sdf.parse(studentWiseAttendanceTemplate.getDateFrom());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        Matcher dateToPatternMatcher = datePattern.matcher(studentWiseAttendanceTemplate.getDateTo());
        if (!studentWiseAttendanceTemplate.getDateTo().equals(STRING_NULL)) {
            if (!dateToPatternMatcher.find()) {
                errors.rejectValue(DATE_TO, ERROR_MSG_DATE_FIELD_INVALID);
                dateTo = null;
            } else {
                try {
                    dateTo = sdf.parse(studentWiseAttendanceTemplate.getDateTo());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            dateTo = null;
        }

        if ((dateFrom != null) && (dateTo != null)) {
            if (dateFrom.after(new Date())) {
                errors.rejectValue(DATE_FROM, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM);
            }
            if (dateTo.after(new Date())) {
                errors.rejectValue(DATE_TO, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO);
            }

        }

        if ((dateFrom != null) && (dateTo != null)) {
            if (dateFrom.compareTo(dateTo) > 0) {
                errors.rejectValue(DATE_TO, ERROR_MSG_DATE_FIELD_INCORRECT);
            }
        }

    }

}
