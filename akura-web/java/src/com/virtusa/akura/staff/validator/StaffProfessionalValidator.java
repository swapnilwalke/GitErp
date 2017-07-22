/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.staff.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.StaffProfessional;

/**
 * This class validates the fields of the StaffProfessional domain object.
 * 
 * @author Virtusa Corporation
 */
public class StaffProfessionalValidator implements Validator {
    
    /** key to hold message when mandatory field is missing. */
    private static final String STQ_UI_MANDATORY_FIELD_REQUIRED = "STQ.UI.MANDATORY.FIELD.REQUIRED";
    
    /** attribute for holding field name for display professional qualification errors. */
    private static final String FIELD_PROFESSIONAL_QUALIFICATION_ID =
            "staffProfessional.professionalQualification.professionalQualificationId";
    
    /**
     * Return whether or not this object can validate objects of the StaffProfessional class.
     * 
     * @param clazz - the class of the StaffProfessional
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return StaffProfessional.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the StaffProfessional.
     * 
     * @param target - Populated object of StaffProfessional to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        StaffProfessional staffProfessional = (StaffProfessional) target;
        
        if (staffProfessional.getStaff().getStaffId() == 0 || staffProfessional.getProfessionalQualification() == null
                || staffProfessional.getProfessionalQualification().getProfessionalQualificationId() == 0) {
            errors.rejectValue(FIELD_PROFESSIONAL_QUALIFICATION_ID, STQ_UI_MANDATORY_FIELD_REQUIRED);
        }
    }
    
}
