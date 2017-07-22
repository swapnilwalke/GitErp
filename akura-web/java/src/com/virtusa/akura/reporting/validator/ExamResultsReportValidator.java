
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

import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.common.AkuraWebConstant;


/**
 * The class ExamResultsValidator.
 * 
 * @author Virtusa Corporation
 *
 */

public class ExamResultsReportValidator implements Validator {
    
    /** The Constant EXAM_RESULTS_REPORT_INVALID. */
    private static final String EXAM_RESULTS_REPORT_INVALID = "EXAM.RESULTS.REPORT.INVALID";
    /** The Constant EXAM_ADMISSION_NO. */
    private static final String EXAM_ADMISSION_NO = "examAdmissionNo";
    /** key to hold pattern of admission number field. */
    private static final String PATTERN_ADDMISSION_NO = "[a-zA-Z0-9]*";
    
    /**
     * Return whether object can validated or not.
     *
     * @param myClass - the class of the TeacherWiseAttendanceTemplate.
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {

        return ExamResults.class.equals(myClass);
    }
    
    /**
     * Validates the user input for criteria.
     *
     * @param object - Populated object of TeacherWiseAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {
        
        ExamResults examResults = (ExamResults) object;
        
        if (examResults.getExamAdmissionNo().trim().isEmpty() || examResults.getExamId() == 0) {
            errors.rejectValue(EXAM_ADMISSION_NO, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
        
        String examAdmissionNumber = PATTERN_ADDMISSION_NO;
        if (!examResults.getExamAdmissionNo().trim().isEmpty() && 
                !examResults.getExamAdmissionNo().trim().matches(examAdmissionNumber)) {
            errors.rejectValue(EXAM_ADMISSION_NO, EXAM_RESULTS_REPORT_INVALID);
        }
    }
    
}
