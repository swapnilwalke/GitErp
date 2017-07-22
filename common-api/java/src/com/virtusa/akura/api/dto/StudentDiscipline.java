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
import java.util.Date;

/**
 * This class represents all the properties of StudentDiscipline.
 * 
 * @author Virtusa Corporation
 */

public class StudentDiscipline extends BaseDomain implements Serializable {
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string date occurrence. */
    private static final String DATE_OCCURRENCE = " date occurrence ";
    
    /** key to hold string comment. */
    private static final String COMMENT = " comment ";
    
    /** key to hold string is informed to parent. */
    private static final String IS_INFORMED_TO_PARENT = " is informed to parent = ";
    
    /** key to hold string warning level id. */
    private static final String WARNING_LEVEL_ID = " warning level id ";
    
    /** key to hold string student id. */
    private static final String STUDENT_ID = " student id ";
    
    /** key to hold string modified date/time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time ";
    
    /** key to hold string student discipline id. */
    private static final String STUDENT_DISCIPLINE_ID = "student discipline id = ";
    
    /**
     * Represents the id for a student discipline.
     */
    private int studentDisciplineId;
    
    /**
     * Holds student object to refer the student id.
     */
    private int studentId;
    
    /**
     * Holds warningLevel object to refer the warning level id.
     */
    private int warningLevelId;
    
    /**
     * Whether the student discipline information is informed to parent.
     */
    
    private boolean informedtoParent;
    
    /**
     * The comment for a student discipline.
     */
    private String comment;
    
    /**
     * The date occurrence for a student discipline.
     */
    private Date date;
    
    /**
     * Holds userLoginid integer object to refer the userloginId.
     */
    private Integer userLoginId;
    
    /**
     * Get the user Login id.
     * 
     * @return userLoginId
     */
    public Integer getUserLoginId() {
    
        return userLoginId;
    }
    
    /**
     * Set the user Login Id.
     * 
     * @param userLoginIdRef - userLoginId
     */
    public void setUserLoginId(Integer userLoginIdRef) {
    
        this.userLoginId = userLoginIdRef;
    }
    
    /**
     * Default constructor to constructs a new student discipline object.
     */
    public StudentDiscipline() {
    
    }
    
    /**
     * Constructs a new Student Discipline object with the given student discipline id.
     * 
     * @param studDisciplineId - Student Discipline Id
     */
    public StudentDiscipline(int studDisciplineId) {
    
        this.studentDisciplineId = studDisciplineId;
    }
    
    /**
     * Gets the student discipline Id.
     * 
     * @return studentDisciplineId - integer
     */
    public int getStudentDisciplineId() {
    
        return studentDisciplineId;
    }
    
    /**
     * Sets the student discipline Id.
     * 
     * @param studentDisId - Integer
     */
    public void setStudentDisciplineId(int studentDisId) {
    
        this.studentDisciplineId = studentDisId;
    }
    
    /**
     * Gets the comment.
     * 
     * @return comment - string
     */
    public String getComment() {
    
        return comment;
    }
    
    /**
     * Sets the comment.
     * 
     * @param comments - String.
     */
    public void setComment(String comments) {
    
        this.comment = comments;
    }
    
    /**
     * Gets the boolean value which indicates the informed to parent information.
     * 
     * @return informedtoParent - boolean value
     */
    public boolean isInformedtoParent() {
    
        return informedtoParent;
    }
    
    /**
     * Sets the boolean value which indicates the informed to parent information.
     * 
     * @param informedParent - boolean value.
     */
    public void setInformedtoParent(boolean informedParent) {
    
        this.informedtoParent = informedParent;
    }
    
    /**
     * @return the warningLevelId
     */
    public int getWarningLevelId() {
    
        return warningLevelId;
    }
    
    /**
     * @param warningLevelIdVlaue the warningLevelId to set
     */
    public void setWarningLevelId(int warningLevelIdVlaue) {
    
        this.warningLevelId = warningLevelIdVlaue;
    }
    
    /**
     * @return the studentId
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * @param studentIdValue the studentId to set
     */
    public void setStudentId(int studentIdValue) {
    
        this.studentId = studentIdValue;
    }
    
    /**
     * @return the date
     */
    public Date getDate() {
    
        if (date != null) {
            return (Date) date.clone();
        }
        return null;
    }
    
    /**
     * @param dateValue the date to set
     */
    public void setDate(Date dateValue) {
    
        if (dateValue != null) {
            this.date = (Date) dateValue.clone();
        }
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STUDENT_DISCIPLINE_ID + this.studentDisciplineId + MODIFIED_DATE_TIME + this.studentId + STUDENT_ID
                + this.warningLevelId + WARNING_LEVEL_ID + this.getModifiedTime() + IS_INFORMED_TO_PARENT
                + this.informedtoParent + COMMENT + this.comment + DATE_OCCURRENCE + this.date;
    }
}
