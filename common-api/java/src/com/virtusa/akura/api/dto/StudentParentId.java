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
public class StudentParentId implements java.io.Serializable {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string parentId. */
    private static final String PARENT_ID = ", parentId=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = "studentId=";
    
    /**
     * Holds student id.
     */
    private int studentId;
    
    /**
     * Holds parent id.
     */
    private int parentId;
    
    /**
     * Default constructor.
     */
    public StudentParentId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentId type int
     * @param intParentId type int
     */
    public StudentParentId(int intStudentId, int intParentId) {
    
        this.studentId = intStudentId;
        this.parentId = intParentId;
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
     * @return the parentId
     */
    public int getParentId() {
    
        return parentId;
    }
    
    /**
     * @param intParentId the parentId to set
     */
    public void setParentId(int intParentId) {
    
        this.parentId = intParentId;
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
        if (!(other instanceof StudentParentId)) {
            return false;
        }
        StudentParentId castOther = (StudentParentId) other;
        
        return (this.getStudentId() == castOther.getStudentId()) && (this.getParentId() == castOther.getParentId());
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
     */
    public int hashCode() {
    
        /*
         * int result = 17; result = 37 * result + this.getStudentId(); result = 37 * result +
         * this.getParentId(); return result;
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
    
        return STUDENT_ID + this.studentId + PARENT_ID + this.parentId;
    }
    
}
