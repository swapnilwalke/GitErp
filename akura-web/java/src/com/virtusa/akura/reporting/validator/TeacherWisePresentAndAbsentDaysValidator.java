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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.util.DateUtil;

/**
 * @author Virtusa Corporation
 */

public class TeacherWisePresentAndAbsentDaysValidator implements Validator {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(TeacherWisePresentAndAbsentDaysValidator.class);

    /**
     * property dateTo type Date. used to track To date.
     */
    private Date dateTo;

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO = "REF.UI.DATE.FIELD.FUTURE.TO.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM = "REF.UI.DATE.FIELD.FUTURE.FROM.INVALID";

    /**
     * String variable to represent Required field message.
     */
    private static final String REQUIRED_FIELD_ERROR = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /**
     * String variable to represent invalid field message.
     */
    private static final String INVALID_FIELD_ERROR = "REF.UI.TEACHER.FIELD.INVALID";

    /**
     * String variable to represent invalid date message.
     */
    private static final String INAVLID_DATE_ERROR = "REF.UI.DATE.FIELD.INVALID";

    /**
     * String variable to represent incorrect date message.
     */
    private static final String INCORRECT_DATE_ERROR = "REF.UI.DATE.FIELD.INVALID";

    /**
     * String variable to the field name to check.
     */
    private static final String FIELD_NAME = "teacherRegNo";

    /**
     * String variable to represent the date from field name.
     */
    private static final String DATE_FROM_FIELD = "dateFrom";

    /**
     * String variable to represent the date to field name.
     */
    private static final String DATE_TO_FIELD = "dateTo";

    /**
     * String variable to represent Regular expression for teacher reg no.
     */
    private static final String REGEX_REGNO_PATTERN = "^[0-9a-zA-Z]*$";

    /**
     * String variable to represent Regular expression for date.
     */
    private static final String REGEX_DATE_PATTERN = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";

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

        TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate = (TeacherWisePresentAbsentTemplate) object;
        if (teacherWisePresentAbsentTemplate.getTeacherRegNo().trim().equals("")
                || teacherWisePresentAbsentTemplate.getTeacherRegNo().equals("0")
                || teacherWisePresentAbsentTemplate.getDateFrom().trim().equals("")
                || teacherWisePresentAbsentTemplate.getDateTo().trim().equals("")) {
            errors.rejectValue(FIELD_NAME, REQUIRED_FIELD_ERROR);
        }

        Pattern regNoPattern = Pattern.compile(REGEX_REGNO_PATTERN);
        Matcher regNoPatternMatcher = regNoPattern.matcher(teacherWisePresentAbsentTemplate.getTeacherRegNo());

        if (!teacherWisePresentAbsentTemplate.getTeacherRegNo().equals("") && !regNoPatternMatcher.find()) {

            errors.rejectValue(FIELD_NAME, INVALID_FIELD_ERROR);
        }

        Pattern datePattern = Pattern.compile(REGEX_DATE_PATTERN);
        Matcher dateFromPatternMatcher = datePattern.matcher(teacherWisePresentAbsentTemplate.getDateFrom());

        if (!teacherWisePresentAbsentTemplate.getDateFrom().equals("")) {
            if (!dateFromPatternMatcher.find()) {
                errors.rejectValue(DATE_FROM_FIELD, INAVLID_DATE_ERROR);
                dateFrom = null;
            } else {
                try {
                    dateFrom = DateUtil.getParseDate(teacherWisePresentAbsentTemplate.getDateFrom());
                } catch (AkuraAppException e) {
                    LOG.error("Error while parsing the date" + "-->" + e.toString());
                }
            }
        }
        Matcher dateToPatternMatcher = datePattern.matcher(teacherWisePresentAbsentTemplate.getDateTo());
        if (!teacherWisePresentAbsentTemplate.getDateTo().equals("")) {
            if (!dateToPatternMatcher.find()) {
                errors.rejectValue(DATE_TO_FIELD, INAVLID_DATE_ERROR);
                dateTo = null;
            } else {
                try {
                    dateTo = DateUtil.getParseDate(teacherWisePresentAbsentTemplate.getDateTo());

                } catch (AkuraAppException e) {
                    LOG.error("Error while parsing the date" + "-->" + e.toString());
                }
            }
        } else {
            dateTo = null;
        }

        if ((dateFrom != null) && (dateTo != null)) {

            if (dateFrom.after(new Date())) {
                errors.rejectValue(DATE_FROM_FIELD, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM);
            }
            if (dateTo.after(new Date())) {
                errors.rejectValue(DATE_TO_FIELD, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO);
            }

        }

        if ((dateFrom != null) && (dateTo != null)) {
            if (dateFrom.compareTo(dateTo) > 0) {
                errors.rejectValue(DATE_TO_FIELD, INCORRECT_DATE_ERROR);
            }
        }
    }
}
