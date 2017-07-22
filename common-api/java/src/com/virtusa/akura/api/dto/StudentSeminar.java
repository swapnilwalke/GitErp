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

import java.util.Date;

/**
 * StudentSeminar is to associates all the properties of StudentSeminar domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentSeminar extends BaseDomain {
    
    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds unique id for studentSeminar. */
    private int studentSeminarId;
    
    /** Holds description for studentSeminar. */
    private String description;
    
    /** Holds year for studentSeminar. */
    private Date year;
    
    /** Holds status for studentSeminar. */
    private Byte status;
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return "StudentSeminar [studentSeminarId=" + studentSeminarId + ", studentId=" + studentId + ", seminar="
                + seminar + ", description=" + description + ", year=" + year + "]";
    }
    
    /** Holds student id for studentSeminar. */
    private int studentId;
    
    /** Holds seminar for studentSeminar. */
    private Seminar seminar;
    
    /**
     * Get the Seminar associated with the StudentSeminar.
     * 
     * @return the associated Seminar object for studentSeminar.
     */
    public Seminar getSeminar() {
    
        return seminar;
    }
    
    /**
     * set the Seminar associated with the StudentSeminar.
     * 
     * @param seminarArg - seminar to set
     */
    public void setSeminar(Seminar seminarArg) {
    
        this.seminar = seminarArg;
    }
    
    /**
     * Get the StudentSeminarId(key) in StudentSeminar table.
     * 
     * @return the studentSeminarId.
     */
    public int getStudentSeminarId() {
    
        return studentSeminarId;
    }
    
    /**
     * set the StudentSeminarId(key) in StudentSeminar table.
     * 
     * @param studentSeminarIdArg - id value to set
     */
    public void setStudentSeminarId(int studentSeminarIdArg) {
    
        this.studentSeminarId = studentSeminarIdArg;
    }
    
    /**
     * get the associated student id.
     * 
     * @return int - studentId to get;
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * set the StudentId(foreign key) in StudentSeminar table.
     * 
     * @param studentIdArg - id of the Student
     */
    public void setStudentId(int studentIdArg) {
    
        this.studentId = studentIdArg;
    }
    
    /**
     * get the description for seminar attended by student.
     * 
     * @return String - description to get
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * set the description in StudentSeminar table.
     * 
     * @param descriptionArg - seminar description for a student
     */
    public void setDescription(String descriptionArg) {
    
        this.description = descriptionArg;
    }
    
    /**
     * get the year for the student seminar.
     * 
     * @return Date - year of the seminar
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * set the year in StudentSeminar table.
     * 
     * @param yearArg - year to set
     */
    public void setYear(Date yearArg) {
    
        if (yearArg != null){            
            this.year = (Date) yearArg.clone();
        }
    }
    
    /**
     * to get status.
     * 
     * @return Byte -get the status.
     */
    public Byte getStatus() {
    
        return status;
    }
    
    /**
     * set the status in StudentSeminar table.
     * 
     * @param statusArg - status value to set
     */
    public void setStatus(Byte statusArg) {
    
        this.status = statusArg;
    }
    
}
