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

package com.virtusa.akura.api.dto;

import java.io.Serializable;

/**
 * This class represents all properties of a SpecialEventsAttendance domain object.
 * 
 * @author Virtusa Corporation
 */
public class SpecialEventsAttendance extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id of the SpecialEventsAttendance.
     */
    private int specialEventsAttendanceId;
    
    /**
     * Represents the specialEventsParticipation of the SpecialEventsAttendance.
     */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /**
     * Represents the studentId of the SpecialEventsAttendance.
     */
    private int studentId;
    
    /**
     * return specialEventsAttendanceId of the SpecialEventsAttendance.
     * 
     * @return the specialEventsAttendanceId
     */
    public int getSpecialEventsAttendanceId() {
    
        return specialEventsAttendanceId;
    }
    
    /**
     * Setter method for specialEventsAttendanceId.
     * 
     * @param specialEventsAttendanceIdRef the specialEventsAttendanceId to set
     */
    public void setSpecialEventsAttendanceId(int specialEventsAttendanceIdRef) {
    
        this.specialEventsAttendanceId = specialEventsAttendanceIdRef;
    }
    
    /**
     * return specialEventsParticipation of the SpecialEventsAttendance.
     * 
     * @return the specialEventsParticipation
     */
    public SpecialEventsParticipation getSpecialEventsParticipation() {
    
        return specialEventsParticipation;
    }
    
    /**
     * Setter method for specialEventsParticipation.
     * 
     * @param specialEventsParticipationRef the specialEventsParticipation to set
     */
    public void setSpecialEventsParticipation(SpecialEventsParticipation specialEventsParticipationRef) {
    
        this.specialEventsParticipation = specialEventsParticipationRef;
    }
    
    /**
     * return studentId of the SpecialEventsAttendance.
     * 
     * @return the studentId
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * Setter method for studentId.
     * 
     * @param studentIdRef the studentId to set
     */
    public void setStudentId(int studentIdRef) {
    
        this.studentId = studentIdRef;
    }
    
}
