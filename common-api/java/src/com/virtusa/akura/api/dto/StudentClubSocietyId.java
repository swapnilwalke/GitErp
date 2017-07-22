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
 * @author Virtusa Corporation
 */
public class StudentClubSocietyId implements java.io.Serializable {
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string club society id. */
    private static final String CLUB_SOCIETY_ID = ", clubSocietyId=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = "studentId=";
    
    /**
	 *
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * studentId - int.
     */
    private int studentId;
    
    /**
     * clubSocietyId - int.
     */
    private int clubSocietyId;
    
    /**
     * year - int.
     */
    private Date year;
    
    /**
     * Default constructor.
     */
    public StudentClubSocietyId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentId type int
     * @param intClubSocietyId type int
     * @param dtYear type Date
     */
    public StudentClubSocietyId(int intStudentId, int intClubSocietyId, Date dtYear) {
    
        this.studentId = intStudentId;
        this.clubSocietyId = intClubSocietyId;
        
        if (dtYear != null) {
            this.year = (Date) dtYear.clone();
        }
    }
    
    /**
     * @return the studentId
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * @param intStudentId the studentId to set
     */
    public void setStudentId(int intStudentId) {
    
        this.studentId = intStudentId;
    }
    
    /**
     * @return the clubSocietyId
     */
    public int getClubSocietyId() {
    
        return clubSocietyId;
    }
    
    /**
     * @param intClubSocietyId the clubSocietyId to set
     */
    public void setClubSocietyId(int intClubSocietyId) {
    
        this.clubSocietyId = intClubSocietyId;
    }
    
    /**
     * @return the year
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * @param dtYear the year to set
     */
    public void setYear(Date dtYear) {
    
        if (dtYear != null) {
            this.year = (Date) dtYear.clone();
        }
    }
    
    /**
     * Overwritten equals method.
     * 
     * @param other type Object
     * @return true or false
     */
    public boolean equals(Object other) {
    
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof StudentClubSocietyId)) {
            return false;
        }
        StudentClubSocietyId castOther = (StudentClubSocietyId) other;
        
        return (this.getStudentId() == castOther.getStudentId())
                && (this.getClubSocietyId() == castOther.getClubSocietyId())
                && ((this.getYear() == castOther.getYear()) || (this.getYear() != null && castOther.getYear() != null && this
                        .getYear().equals(castOther.getYear())));
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
     */
    public int hashCode() {
    
        /*
         * int result = 17; result = 37 * result + this.getStudentId(); result = 37 * result +
         * this.getClubSocietyId(); result = 37 * result + (getYear() == null ? 0 :
         * this.getYear().hashCode()); return result;
         */
        
        return 0;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_ID + this.studentId + CLUB_SOCIETY_ID + this.clubSocietyId + YEAR + this.year;
    }
    
}
