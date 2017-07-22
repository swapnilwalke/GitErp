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
public class StudentPrefectId implements java.io.Serializable {
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string prefectTypeId. */
    private static final String PREFECT_TYPE_ID = ", prefectTypeId=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = "studentId=";
    
    /**
     * Holds student id.
     */
    private int studentId;
    
    /**
     * Holds prefect type id.
     */
    private int prefectTypeId;
    
    /**
     * Default constructor.
     */
    public StudentPrefectId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentId type int
     * @param intPrefectTypeId type int
     */
    public StudentPrefectId(int intStudentId, int intPrefectTypeId) {
    
        this.studentId = intStudentId;
        this.prefectTypeId = intPrefectTypeId;
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
     * @return the prefectTypeId
     */
    public int getPrefectTypeId() {
    
        return prefectTypeId;
    }
    
    /**
     * @param intPrefectTypeId the prefectTypeId to set
     */
    public void setPrefectTypeId(int intPrefectTypeId) {
    
        this.prefectTypeId = intPrefectTypeId;
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
        if (!(other instanceof StudentPrefectId)) {
            return false;
        }
        StudentPrefectId castOther = (StudentPrefectId) other;
        
        return (this.getStudentId() == castOther.getStudentId())
                && (this.getPrefectTypeId() == castOther.getPrefectTypeId());
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
     */
    public int hashCode() {
    
        /*
         * int result = 17; result = 37 * result + this.getStudentId(); result = 37 * result +
         * this.getPrefectTypeId(); return result;
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
    
        return STUDENT_ID + studentId + PREFECT_TYPE_ID + prefectTypeId;
    }
    
}
