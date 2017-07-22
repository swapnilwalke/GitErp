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

import com.virtusa.akura.api.dto.EarlyLateLeaversTemplate;
import com.virtusa.akura.api.dto.StudentSpecialEventAttendanceTemplate;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates fields for Late Levers,user input data.
 * 
 * @author Virtusa Corporation
 */
public class StudentSpecialEventAttendanceReportValidator implements Validator {
    
    /** Constant for specialEvents.specialEventsId. */
    private static final String SPECIAL_EVENTS_SPECIAL_EVENTS_ID = "specialEvents.specialEventsId";
    
    /**
     * Return whether object can validated or not.
     * 
     * @param myClass - the class of the StudentSpecial EventAttendanceTemplate.
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> myClass) {
    
        return EarlyLateLeaversTemplate.class.equals(myClass);
    }
    
    /**
     * Validates the user input for criteria.
     * 
     * @param object - Populated object of StudentSpecialEventAttendanceTemplate to validate
     * @param errors - contain errors related to fields.
     */
    @Override
    public void validate(Object object, Errors errors) {
    
        StudentSpecialEventAttendanceTemplate studSpecEventAttendeTempl =
                (StudentSpecialEventAttendanceTemplate) object;
        
        if (studSpecEventAttendeTempl.getSpecialEvents().getSpecialEventsId() == 0
                || studSpecEventAttendeTempl.getSpecialEventsParticipation().getSpecialEventsParticipationId() == 0) {
            
            errors.rejectValue(SPECIAL_EVENTS_SPECIAL_EVENTS_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
    }
}
