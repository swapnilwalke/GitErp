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

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;

/**
 * class to validate a report of Teacher Early Late Attendance.
 */
public class TeacherEarlyLateAttendeesValidator implements Validator {
    
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
    
        AttendeesWrapperTemplate attendeesWrapperTemplate = (AttendeesWrapperTemplate) object;
        
        // Validation related to teacher late attendees.
        if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate() != null) {
            if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInFrom().equals("0")) {
                
                errors.rejectValue("teacherLateAttendiesTemplate.timeInFrom", "REF.UI.TIME.FIELD.MANDATORY");
            }
            if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInTo().equals("0")) {
                
                errors.rejectValue("teacherLateAttendiesTemplate.timeInTo", "REF.UI.TIME.FIELD.MANDATORY");
            }
            if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom().equals("")) {
                
                errors.rejectValue("teacherLateAttendiesTemplate.dateFrom", "REF.UI.DATE.FIELD.MANDATORY");
            }
            
            if (attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo().equals("")) {
                
                errors.rejectValue("teacherLateAttendiesTemplate.dateTo", "REF.UI.DATE.FIELD.MANDATORY");
            }
            
        }
        // Validation related to teacher early attendees.
        
        if (attendeesWrapperTemplate.getTeacherEarlyComersTemplate() != null) {
            
            if ((attendeesWrapperTemplate.getTeacherEarlyComersTemplate().getDateFrom().equals(""))) {
                
                errors.rejectValue("teacherEarlyComersTemplate.dateFrom", "REF.UI.DATE.FIELD.MANDATORY");
            }
            
            if ((attendeesWrapperTemplate.getTeacherEarlyComersTemplate().getDateTo().equals(""))) {
                
                errors.rejectValue("teacherEarlyComersTemplate.dateTo", "REF.UI.DATE.FIELD.MANDATORY");
            }
            if (attendeesWrapperTemplate.getTeacherEarlyComersTemplate().getTimeInFrom().equals("0")) {
                
                errors.rejectValue("teacherEarlyComersTemplate.timeInFrom", "REF.UI.TIME.FIELD.MANDATORY");
            }
            if (attendeesWrapperTemplate.getTeacherEarlyComersTemplate().getTimeInTo().equals("0")) {
                
                errors.rejectValue("teacherEarlyComersTemplate.timeInTo", "REF.UI.TIME.FIELD.MANDATORY");
            }
            
        }
    }
    
}
