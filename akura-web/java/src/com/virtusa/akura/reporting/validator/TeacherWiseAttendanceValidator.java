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

import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * class to validate a report of Teacher wise attendance.
 */
public class TeacherWiseAttendanceValidator implements Validator {

    /** error message for date field incorrent. */
    private static final String ERROR_MSG_DATE_FIELD_INCORRECT = "REF.UI.DATE.FIELD.INCORRECT";

    /** error message for date field invalid. */
    private static final String ERROR_MSG_DATE_FIELD_INVALID = "REF.UI.DATE.FIELD.INVALID";

    /** variable for date to. */
    private static final String ATT_DATE_TO = "dateTo";

    /** variable for date from. */
    private static final String ATT_DATE_FROM = "dateFrom";

    /** variable for year-month-day. */
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    /** variable for date pattern. */
    private static final String PATTERN_DATE = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";

    /** error message for teacher field invalid. */
    private static final String ERROR_MSG_TEACHER_FIELD_INVALID = "REF.UI.TEACHER.FIELD.INVALID";

    /** variable for teacher number. */
    private static final String TEACHER_NO = "teacherNo";

    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO = "REF.UI.DATE.FIELD.FUTURE.TO.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM = "REF.UI.DATE.FIELD.FUTURE.FROM.INVALID";

    /** variable for null. */
    private static final String STRING_NULL = "";

    /**
     * property dateTo type Date. used to track To date.
     */
    private Date dateTo;

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the TeacherWiseAttendanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        return TeacherWiseAttendanceTemplate.class.equals(myClass);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of TeacherWiseAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        Date dateFrom = null;

        TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate = (TeacherWiseAttendanceTemplate) object;
        if (teacherWiseAttendanceTemplate.getTeacherNo().trim().equals(STRING_NULL)
                || teacherWiseAttendanceTemplate.getDateFrom().trim().equals(STRING_NULL)
                || teacherWiseAttendanceTemplate.getDateTo().trim().equals(STRING_NULL)) {
            errors.rejectValue(TEACHER_NO, ERROR_MSG_MANDATORY_FIELD_REQUIRED);
        }

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern("REFORTING.STAFFWISE.VALIDATOR");

        Pattern idPattern = Pattern.compile(validatorPattern);
        Matcher idMatcher = idPattern.matcher(teacherWiseAttendanceTemplate.getTeacherNo());

        if (!teacherWiseAttendanceTemplate.getTeacherNo().equals(STRING_NULL) && !idMatcher.find()) {

            errors.rejectValue(TEACHER_NO, ERROR_MSG_TEACHER_FIELD_INVALID);
        }

        Pattern datePattern = Pattern.compile(PATTERN_DATE);
        Matcher dateFromPatternMatcher = datePattern.matcher(teacherWiseAttendanceTemplate.getDateFrom());

        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY);

        if (!teacherWiseAttendanceTemplate.getDateFrom().equals(STRING_NULL)) {
            if (!dateFromPatternMatcher.find()) {
                errors.rejectValue(ATT_DATE_FROM, ERROR_MSG_DATE_FIELD_INVALID);
                dateFrom = null;
            } else {
                try {
                    dateFrom = sdf.parse(teacherWiseAttendanceTemplate.getDateFrom());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        Matcher dateToPatternMatcher = datePattern.matcher(teacherWiseAttendanceTemplate.getDateTo());
        if (!teacherWiseAttendanceTemplate.getDateTo().equals(STRING_NULL)) {
            if (!dateToPatternMatcher.find()) {
                errors.rejectValue(ATT_DATE_TO, ERROR_MSG_DATE_FIELD_INVALID);
                dateTo = null;
            } else {
                try {
                    dateTo = sdf.parse(teacherWiseAttendanceTemplate.getDateTo());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            dateTo = null;
        }

        if ((dateFrom != null) && (dateTo != null)) {
            if (dateFrom.after(new Date())) {
                errors.rejectValue(ATT_DATE_FROM, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM);
            }
            if (dateTo.after(new Date())) {
                errors.rejectValue(ATT_DATE_TO, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO);
            }

        }

        if ((dateFrom != null) && (dateTo != null)) {
            if (dateFrom.compareTo(dateTo) > 0) {
                errors.rejectValue(ATT_DATE_TO, ERROR_MSG_DATE_FIELD_INCORRECT);
            }
        }

    }

}
