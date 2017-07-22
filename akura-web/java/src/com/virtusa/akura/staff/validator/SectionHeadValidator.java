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

import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the SectionHead domain object.
 * 
 * @author Virtusa Corporation
 */
public class SectionHeadValidator implements Validator {
    
    /** The field name to check. */
    private static final String FIELD_NAME_SECTION_HEAD_ID = "sectionHeadId";
    
    /**
     * Return whether or not this object can validate objects of the SectionHead class.
     * 
     * @param clazz - the class of the SectionHead
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return SectionHead.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the SectionHead.
     * 
     * @param object - Populated object of SectionHead to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object object, Errors errors) {
    
        SectionHead sectionHead = (SectionHead) object;
        if ((sectionHead.getStaff().getStaffId() == 0 && sectionHead.getHiddenStaff().getStaffId() == 0)
                || ((sectionHead.getGradeId() == 0 && sectionHead.getSectionId()==null) && (sectionHead
                        .getHiddenGradeId() == 0 && sectionHead.getSectionId()==null))
                || sectionHead.getStartDate() == null || sectionHead.getEndDate() == null) {
            errors.rejectValue(FIELD_NAME_SECTION_HEAD_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
        
    }
    
}
