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

/**
 * This class represents all the properties need to generate. 
 * Student Special Event Attendance report.
 * 
 * @author Virtusa Corporation
 */
public class StudentSpecialEventAttendanceTemplate {
    
    /** represents the available events to select in the report. */
    private SpecialEvents specialEvents;
    
    /** Student admission use in student special event attendance report. */
    private String studentAdmissionNo;
    
    /** Student name use in student special event attendance report. */
    private String studentName;
    
    /** Attendance use in student special event attendance report. */
    private String attendance;
    
    /**
     * Represents the selected participant for the report generation.
     */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /**
     * Represents the special event participation category id.
     */
    private int specialEventsParticipationCategoryId;
    
     /**
     * Getter method to get special event object.
     * 
     * @return special event object
     */
    public SpecialEvents getSpecialEvents() {
    
        return specialEvents;
    }

    /**
     * Setter method to set special event object.
     * 
     * @param specialEventsValue special event object
     */
    public void setSpecialEvents(SpecialEvents specialEventsValue) {
    
        this.specialEvents = specialEventsValue;
    }

    /**
     * Getter method for student admission no.
     * 
     * @return studentAdmissionNo
     */
    public String getStudentAdmissionNo() {
    
        return studentAdmissionNo;
    }

    /**
     * Setter method for student admission no.
     * 
     * @param vStudentAdmissionNo String
     */
    public void setStudentAdmissionNo(String vStudentAdmissionNo) {
    
        this.studentAdmissionNo = vStudentAdmissionNo;
    }
    
    /**
     * Getter method for student name.
     * 
     * @return studentName.
     */
    public String getStudentName() {
    
        return studentName;
    }

    /**
     * Setter method for student name.
     * 
     * @param vStudentName String
     */
    public void setStudentName(String vStudentName) {
    
        this.studentName = vStudentName;
    }

    /**
     * Getter method for attendance.
     * 
     * @return attendance
     */
    public String getAttendance() {
    
        return attendance;
    }

    /**
     * Setter method for attendance.
     * 
     * @param vAttendance String
     */
    public void setAttendance(String vAttendance) {
    
        this.attendance = vAttendance;
    }
    
    /**
     * Getter method for specialEventsParticipation.
     * 
     * @return specialEventsParticipation
     */
    public SpecialEventsParticipation getSpecialEventsParticipation() {
    
        return specialEventsParticipation;
    }
    
    /**
     * Setter method for specialEventsParticipation.
     * 
     * @param vSpecialEventsParticipation SpecialEventsParticipation object
     */
    public void setSpecialEventsParticipation(SpecialEventsParticipation vSpecialEventsParticipation) {
    
        this.specialEventsParticipation = vSpecialEventsParticipation;
    }

    /**
     * Getter method for special event category id.
     * 
     * @return specialEventsParticipationCategoryId
     */
    public int getSpecialEventsParticipationCategoryId() {
    
        return specialEventsParticipationCategoryId;
    }

    /**
     * Setter method for special event category id.
     * 
     * @param vSpecialEventsParticipationCategoryId int
     */
    public void setSpecialEventsParticipationCategoryId(int vSpecialEventsParticipationCategoryId) {
    
        this.specialEventsParticipationCategoryId = vSpecialEventsParticipationCategoryId;
    }
        
}
