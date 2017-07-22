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
 * Domain object to map data for StaffEducationId.
 * 
 * @author Virtusa Corporation
 */
public class StaffEducationId implements java.io.Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "StaffEducationId staffId=". */
    private static final String STAFF_EDUCATION_ID_STAFF_ID = "StaffEducationId staffId=";
    
    /** The constant for String ", educationalQualId=". */
    private static final String EDUCATIONAL_QUAL_ID = ", educationalQualId=";
    
    /**
     * Holds the staff id.
     */
    private int staffId;
    
    /**
     * Holds the educationalQual id.
     */
    private int educationalQualId;
    
    /**
     * default constructor.
     */
    public StaffEducationId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStaffId type int.
     * @param intEducationalQualId type int.
     */
    public StaffEducationId(int intStaffId, int intEducationalQualId) {
    
        this.staffId = intStaffId;
        this.educationalQualId = intEducationalQualId;
    }
    
    /**
     * Get staffId.
     * 
     * @return the staffId.
     */
    public int getStaffId() {
    
        return staffId;
    }
    
    /**
     * Set the staffId.
     * 
     * @param intStaffId the staffId to set.
     */
    public void setStaffId(int intStaffId) {
    
        this.staffId = intStaffId;
    }
    
    /**
     * Get the Educational Qualification Id.
     * 
     * @return the educationalQualId.
     */
    public int getEducationalQualId() {
    
        return educationalQualId;
    }
    
    /**
     * Set the Educational QualificationId.
     * 
     * @param intEducationalQualId the educationalQualId to set.
     */
    public void setEducationalQualId(int intEducationalQualId) {
    
        this.educationalQualId = intEducationalQualId;
    }
    
    /**
     * Overwritten equals method.
     * 
     * @param other type Object.
     * @return true or false.
     */
    public boolean equals(Object other) {
    
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof StaffEducationId)) {
            return false;
        }
        StaffEducationId castOther = (StaffEducationId) other;
        
        return (this.getStaffId() == castOther.getStaffId())
                && (this.getEducationalQualId() == castOther.getEducationalQualId());
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer.
     */
    public int hashCode() {
    
        return 0;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STAFF_EDUCATION_ID_STAFF_ID + staffId + EDUCATIONAL_QUAL_ID + educationalQualId;
    }
    
}
