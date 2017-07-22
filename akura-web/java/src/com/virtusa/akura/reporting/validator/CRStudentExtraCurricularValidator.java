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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;

/**
 * class to validate student extra curricular activities form.
 */
public class CRStudentExtraCurricularValidator implements Validator {
    
    /**
     * Return whether object can validated or not.
     * 
     * @param myClass - the class of the CRExtraCurricularActivitiesTemplate
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> myClass) {
    
        CRExtraCurricularActivitiesTemplate.class.equals(myClass);
        return false;
    }
    
    /**
     * Validates the user input for criteria.
     * 
     * @param object - Populated object of CRExtraCurricularActivitiesTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object object, Errors errors) {
    
        CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate =
                (CRExtraCurricularActivitiesTemplate) object;
        if (cRExtraCurricularActivitiesTemplate.getAdmissionNo().equals("")
                || cRExtraCurricularActivitiesTemplate.getFullName().equals("")) {
            errors.rejectValue("admissionNo", "STQ.UI.MANDATORY.FIELD.REQUIRED");
        }
        
        Pattern namePattern = Pattern.compile("^[a-zA-Z]*[[\\s][a-zA-Z\\s]*]*$");
        Matcher nameMatcher = namePattern.matcher(cRExtraCurricularActivitiesTemplate.getFullName());
        Pattern numberPattern = Pattern.compile("^[0-9a-zA-Z]*$");
        Matcher numberMatcher = numberPattern.matcher(cRExtraCurricularActivitiesTemplate.getAdmissionNo());
        
        if (!cRExtraCurricularActivitiesTemplate.getAdmissionNo().equals("") && !numberMatcher.find()) {
            
            errors.rejectValue("admissionNo", "REF.UI.ADMISSION.FIELD.INVALID");
            
        }
        
        if (!cRExtraCurricularActivitiesTemplate.getFullName().equals("") && !nameMatcher.find()) {
            
            errors.rejectValue("fullName", "REF.UI.STUDENT.FIELD.INVALID");
        }
        
    }
    
}
