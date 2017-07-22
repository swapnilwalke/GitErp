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

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;

/**
 * This class validates fields for Teacher Late comers,user input data.
 *
 * @author Virtusa Corporation
 */
public class LateComersValidator implements Validator {

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO = "REF.UI.DATE.FIELD.FUTURE.TO.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM = "REF.UI.DATE.FIELD.FUTURE.FROM.INVALID";

    /** error message for date field incorrect. */
    private static final String ERROR_MSG_DATE_FIELD_INCORRECT = "REF.UI.DATE.FIELD.INCORRECT";

    /** variable for date to. */
    private static final String DATE_TO = "dateTo";

    /** error message for date field invalid. */
    private static final String ERROR_MSG_DATE_FIELD_INVALID = "REF.UI.DATE.FIELD.INVALID";

    /** variable for date from. */
    private static final String DATE_FROM = "dateFrom";

    /** variable for date format. */
    private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /** date pattern for date. */
    private static final String DATE_PATTERN = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";

    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** variable for time in from. */
    private static final String TEACHER_LATE_ATTENDIES_TEMPLATE_TIME_IN_FROM =
            "teacherLateAttendiesTemplate.timeInFrom";

    /** variable for hours. */
    private static final String STRING_NULL = "";

    /** variable for hours. */
    private static final String VARIABLE_ZERO = "0";

    /**
     * property dateTo type Date. used to track To date.
     */
    private Date dateTo;

    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the AttendeesWrapperTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        AttendeesWrapperTemplate.class.equals(myClass);
        return false;
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of AttendeesWrapperTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {

        Date dateFrom = null;

        AttendeesWrapperTemplate attendeesWrapperTemplate = (AttendeesWrapperTemplate) object;

        if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInFrom().equals(VARIABLE_ZERO)
                || attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInTo().equals(VARIABLE_ZERO)
                || attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom().equals(STRING_NULL)
                || attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo().equals(STRING_NULL)) {

            errors.rejectValue(TEACHER_LATE_ATTENDIES_TEMPLATE_TIME_IN_FROM, ERROR_MSG_MANDATORY_FIELD_REQUIRED);

        }

        Pattern datePattern = Pattern.compile(DATE_PATTERN);
        Matcher dateFromPatternMatcher =
                datePattern.matcher(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        if (!attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom().equals(STRING_NULL)) {
            if (!dateFromPatternMatcher.find()) {
                errors.rejectValue(DATE_FROM, ERROR_MSG_DATE_FIELD_INVALID);
                dateFrom = null;
            } else {
                try {
                    dateFrom = sdf.parse(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        Matcher dateToPatternMatcher =
                datePattern.matcher(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());
        if (!attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo().equals(STRING_NULL)) {
            if (!dateToPatternMatcher.find()) {
                errors.rejectValue(DATE_TO, ERROR_MSG_DATE_FIELD_INVALID);
                dateTo = null;
            } else {
                try {
                    dateTo = sdf.parse(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());

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
