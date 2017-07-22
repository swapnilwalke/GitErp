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

import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This class validates fields for attendance - swipe in/out by Student,user input data.
 *
 * @author Virtusa Corporation
 */
public class StudentWiseSwipeInOutAttendanceValidator {

    /** error message for field required. */
    private static final String ERROR_MSG_REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** error message for field incorrect. */
    private static final String ERROR_FIELD_REF_UI_DATE_FIELD_INCORRECT = "REF.UI.DATE.FIELD.INCORRECT";

    /** error message for invalid date field. */
    private static final String ERROR_INVALID_MSG_REF_UI_DATE_FIELD_INVALID = "REF.UI.DATE.FIELD.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO = "REF.UI.DATE.FIELD.FUTURE.TO.INVALID";

    /** error message for invalid date field. */
    private static final String ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM = "REF.UI.DATE.FIELD.FUTURE.FROM.INVALID";

    /** date format for year-month-day . */
    private static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /** date pattern . */
    private static final String DATE_PATTERN = "^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$";

    /** variable for date from . */
    private static final String VAR_DATE_FROM = "dateFrom";

    /** variable for date to . */
    private static final String VAR_DATE_TO = "dateTo";

    /**
     * property dateFrom type Date. used to track From date.
     */

    private static final String STRING_NULL = "";

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

        return StudentWiseSwipInOutTemplate.class.equals(myClass);
    }

    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of StudentWiseAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     * @throws AkuraAppException - throws when exception occurs.
     */
    public void validate(Object object, Errors errors) throws AkuraAppException {

        Date dateFrom = null;

        StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate = (StudentWiseSwipInOutTemplate) object;

        if (studentWiseSwipInOutTemplate.getDateFrom().equals(STRING_NULL)||studentWiseSwipInOutTemplate.
                getDateTo().equals(STRING_NULL)) {
            errors.rejectValue(VAR_DATE_TO, ERROR_MSG_REF_UI_MANDATORY_FIELD_REQUIRED);
        }else{
            Pattern datePattern = Pattern.compile(DATE_PATTERN);

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
            if (!studentWiseSwipInOutTemplate.getDateFrom().equals(STRING_NULL)) {
                Matcher dateFromPatternMatcher = datePattern.matcher(studentWiseSwipInOutTemplate.getDateFrom());
                if (!dateFromPatternMatcher.find()) {
                    errors.rejectValue(VAR_DATE_FROM, ERROR_INVALID_MSG_REF_UI_DATE_FIELD_INVALID);
                    dateFrom = null;
                } else {
                    try {
                        dateFrom = sdf.parse(studentWiseSwipInOutTemplate.getDateFrom());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            Matcher dateToPatternMatcher = datePattern.matcher(studentWiseSwipInOutTemplate.getDateTo());
            if (!studentWiseSwipInOutTemplate.getDateTo().equals(STRING_NULL)) {
                if (!dateToPatternMatcher.find()) {
                    errors.rejectValue(VAR_DATE_TO, ERROR_INVALID_MSG_REF_UI_DATE_FIELD_INVALID);
                    dateTo = null;
                } else {
                    try {
                        dateTo = sdf.parse(studentWiseSwipInOutTemplate.getDateTo());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                dateTo = null;
            }

            if ((dateFrom != null) && (dateTo != null)) {

                if (dateFrom.after(new Date())) {
                    errors.rejectValue(VAR_DATE_FROM, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_FROM);
                }
                if (dateTo.after(new Date())) {
                    errors.rejectValue(VAR_DATE_TO, ERROR_FIELD_DATE_FIELD_INCORRECT_FUTURE_TO);
                }

            }

            if ((dateFrom != null) && (dateTo != null)) {
                if (dateFrom.compareTo(dateTo) > 0) {
                    errors.rejectValue(VAR_DATE_TO, ERROR_FIELD_REF_UI_DATE_FIELD_INCORRECT);
                }
            }
            
        }
        
        
    }

}
