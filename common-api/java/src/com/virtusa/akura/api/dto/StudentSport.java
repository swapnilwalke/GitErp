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

package com.virtusa.akura.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Virtusa Corporation
 */
public class StudentSport extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID final.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = ", studentId=";
    
    /** key to hold string positionId. */
    private static final String POSITION_ID = ", positionId=";
    
    /** key to hold string sportCategoryId. */
    private static final String SPORT_CATEGORY_ID = ", sportCategoryId=";
    
    /** key to hold string studentSportId. */
    private static final String STUDENT_SPORT_ID = "studentSportId=";
    
    /**
     * Holds studentSportId.
     */
    private int studentSportId;
    
    /**
     * Holds sportCategory.
     */
    private SportCategory sportCategory;
    
    /**
     * Holds position.
     */
    private Position position;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Holds sportColour.
     */
    private int sportColour;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * Holds status.
     */
    private Byte status;
    
    /**
     * default constructor.
     */
    public StudentSport() {
    
    }
    
    /**
     * constructor with parameter.
     * 
     * @param intStudentSportId integer.
     */
    public StudentSport(int intStudentSportId) {
    
        studentSportId = intStudentSportId;
    }
    
    /**
     * @return the studentSportId
     */
    public int getStudentSportId() {
    
        return studentSportId;
    }
    
    /**
     * @param intStudentSportId the studentSportId to set
     */
    public void setStudentSportId(int intStudentSportId) {
    
        this.studentSportId = intStudentSportId;
    }
    
    /**
     * @return the sportCategory
     */
    public SportCategory getSportCategory() {
    
        return sportCategory;
    }
    
    /**
     * @param objSportCategory the sportCategory to set
     */
    public void setSportCategory(SportCategory objSportCategory) {
    
        this.sportCategory = objSportCategory;
    }
    
    /**
     * @return the position
     */
    public Position getPosition() {
    
        return position;
    }
    
    /**
     * @param objPosition the position to set
     */
    public void setPosition(Position objPosition) {
    
        this.position = objPosition;
    }
    
    /**
     * @return the student
     */
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * @param objStudent the student to set
     */
    public void setStudent(Student objStudent) {
    
        this.student = objStudent;
    }
    
    /**
     * @return the sportColour
     */
    public int getSportColour() {
    
        return sportColour;
    }
    
    /**
     * @param intVal the sportColour to set
     */
    public void setSportColour(int intVal) {
    
        this.sportColour = intVal;
    }
    
    /**
     * @return the status
     */
    public Byte getStatus() {
    
        return status;
    }
    
    /**
     * @param bytStatus the status to set
     */
    public void setStatus(Byte bytStatus) {
    
        this.status = bytStatus;
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
     * @param dateYear the year to set
     */
    public void setYear(Date dateYear) {
    
        if (dateYear != null) {
            this.year = (Date) dateYear.clone();
        }
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_SPORT_ID + studentSportId + SPORT_CATEGORY_ID + sportCategory.getSportCategoryId() + POSITION_ID
                + position.getPositionId() + STUDENT_ID + student.getStudentId() + YEAR + year;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + studentSportId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentSport other = (StudentSport) obj;
        if (studentSportId != other.studentSportId)
            return false;
        return true;
    }
    
    
}
