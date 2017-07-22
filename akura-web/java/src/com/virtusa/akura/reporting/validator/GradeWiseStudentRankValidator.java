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

import com.virtusa.akura.api.dto.GradeWiseStudentRankReportTmpl;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates fields for input data entered to generate student grade rank report .
 * 
 * @author Virtusa Corporation
 */
public class GradeWiseStudentRankValidator implements Validator {
    
    /** Constant for grade.gradeId. */
    private static final String GRADE_GRADE_ID = "grade.gradeId";

    /**
     * Return whether object can validated or not.
     * 
     * @param myClass - the class of the GradeWiseStudentRankReportTmpl.
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> myClass) {
    
        return GradeWiseStudentRankReportTmpl.class.equals(myClass);
    }

    /**
     * Validates the user input for generate report.
     * 
     * @param object - Populated object of GradeWiseStudentRankReportTmpl to validate
     * @param errors - contain errors related to fields.
     */
    @Override
    public void validate(Object object, Errors errors) {
    
        GradeWiseStudentRankReportTmpl gradeWiseStudentRankReportTmpl = (GradeWiseStudentRankReportTmpl) object;
        
        if (gradeWiseStudentRankReportTmpl.getGrade().getGradeId() == 0
                || gradeWiseStudentRankReportTmpl.getTerm().getTermId() == 0
                || gradeWiseStudentRankReportTmpl.getNoOfPrizes() == 0
                || "0".equals(gradeWiseStudentRankReportTmpl.getYear())) {
            
            errors.rejectValue(GRADE_GRADE_ID, AkuraWebConstant.MANDATORY_SLECTED_FIELD_ERROR_CODE);
        }        
    }    
}
