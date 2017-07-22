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
 * @author Virtusa Corporation
 */
public class SubjectTeacherId implements java.io.Serializable {
    
    /** String attribute for to CLASS_ID. */
    private static final String CLASS_ID = ", classId=";
    
    /** String attribute for to GRADE_SUBJECT_ID. */
    private static final String GRADE_SUBJECT_ID = ", gradeSubjectId=";
    
    /** String attribute for to STAFF_ID. */
    private static final String STAFF_ID = "SubjectTeacherId [staffId=";
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds staff id.
     */
    private int staffId;
    
    /**
     * Holds gradeSubject id.
     */
    private int gradeSubjectId;
    
    /**
     * Holds class id.
     */
    private int classId;
    
    /**
     * Default constructor.
     */
    public SubjectTeacherId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStaffId type int
     * @param intGradeSubjectId type int
     * @param intClassId type int
     */
    public SubjectTeacherId(int intStaffId, int intGradeSubjectId, int intClassId) {
    
        this.staffId = intStaffId;
        this.gradeSubjectId = intGradeSubjectId;
        this.classId = intClassId;
    }
    
    /**
     * @return the staffId
     */
    public int getStaffId() {
    
        return staffId;
    }
    
    /**
     * @param intStaffId the staffId to set
     */
    public void setStaffId(int intStaffId) {
    
        this.staffId = intStaffId;
    }
    
    /**
     * @return the gradeSubjectId
     */
    public int getGradeSubjectId() {
    
        return gradeSubjectId;
    }
    
    /**
     * @param intGradeSubjectId the gradeSubjectId to set
     */
    public void setGradeSubjectId(int intGradeSubjectId) {
    
        this.gradeSubjectId = intGradeSubjectId;
    }
    
    /**
     * @return the classId
     */
    public int getClassId() {
    
        return classId;
    }
    
    /**
     * @param intClassId the classId to set
     */
    public void setClassId(int intClassId) {
    
        this.classId = intClassId;
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
        if (!(other instanceof SubjectTeacherId)) {
            return false;
        }
        SubjectTeacherId castOther = (SubjectTeacherId) other;
        
        return (this.getStaffId() == castOther.getStaffId())
                && (this.getGradeSubjectId() == castOther.getGradeSubjectId())
                && (this.getClassId() == castOther.getClassId());
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
     */
    public int hashCode() {
    
        /*
         * int result = 17; result = 37 * result + this.getStaffId(); result = 37 * result +
         * this.getGradeSubjectId(); result = 37 * result + this.getClassId(); return result;
         */
        
        return 0;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STAFF_ID + staffId + GRADE_SUBJECT_ID + gradeSubjectId + CLASS_ID + classId;
    }
    
}
