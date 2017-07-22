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
public class StudentSportId implements java.io.Serializable {
    
    /**
     * serialVersionUID final.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string sportCategoryId. */
    private static final String SPORT_CATEGORY_ID = ", sportCategoryId=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = "studentId=";
    
    /**
     * Holds student id.
     */
    private int studentId;
    
    /**
     * Holds sport category id.
     */
    private int sportCategoryId;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * Default constructor.
     */
    public StudentSportId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentId type int
     * @param intSportCategoryId type int
     * @param dtYear type date
     */
    public StudentSportId(int intStudentId, int intSportCategoryId, Date dtYear) {
    
        this.studentId = intStudentId;
        this.sportCategoryId = intSportCategoryId;
        
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
     * @return the sportCategoryId
     */
    public int getSportCategoryId() {
    
        return sportCategoryId;
    }
    
    /**
     * @param intSportCategoryId the sportCategoryId to set
     */
    public void setSportCategoryId(int intSportCategoryId) {
    
        this.sportCategoryId = intSportCategoryId;
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
        if (!(other instanceof StudentSportId)) {
            return false;
        }
        StudentSportId castOther = (StudentSportId) other;
        
        return (this.getStudentId() == castOther.getStudentId())
                && (this.getSportCategoryId() == castOther.getSportCategoryId())
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
         * this.getSportCategoryId(); result = 37 * result + (getYear() == null ? 0 :
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
    
        return STUDENT_ID + studentId + SPORT_CATEGORY_ID + sportCategoryId + YEAR + year;
        
    }
    
}
