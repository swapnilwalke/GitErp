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
 * Domain object to map data for StaffProfessionalId.
 * 
 * @author Virtusa Corporation
 */
public class StaffProfessionalId implements java.io.Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String ", professionalQualId=". */
    private static final String PROFESSIONAL_QUAL_ID = ", professionalQualId=";
    
    /** The constant for String "StaffProfessionalId staffId=". */
    private static final String STAFF_PROFESSIONAL_ID_STAFF_ID = "StaffProfessionalId staffId=";
    
    /**
     * Holds staff id.
     */
    private int staffId;
    
    /**
     * Holds professional qualification id.
     */
    private int professionalQualId;
    
    /**
     * default constructor.
     */
    public StaffProfessionalId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStaffId type int.
     * @param intProfessionalQualId type int.
     */
    public StaffProfessionalId(int intStaffId, int intProfessionalQualId) {
    
        this.staffId = intStaffId;
        this.professionalQualId = intProfessionalQualId;
    }
    
    /**
     * Get Staff Id.
     * 
     * @return the staffId.
     */
    public int getStaffId() {
    
        return staffId;
    }
    
    /**
     * Set Staff Id.
     * 
     * @param intStaffId the staffId to set.
     */
    public void setStaffId(int intStaffId) {
    
        this.staffId = intStaffId;
    }
    
    /**
     * Get Professional Qualification Id.
     * 
     * @return the professionalQualId.
     */
    public int getProfessionalQualId() {
    
        return professionalQualId;
    }
    
    /**
     * Set Professional Qualification Id.
     * 
     * @param intProfessionalQualId the professionalQualId to set.
     */
    public void setProfessionalQualId(int intProfessionalQualId) {
    
        this.professionalQualId = intProfessionalQualId;
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
        if (!(other instanceof StaffProfessionalId)) {
            return false;
        }
        StaffProfessionalId castOther = (StaffProfessionalId) other;
        
        return (this.getStaffId() == castOther.getStaffId())
                && (this.getProfessionalQualId() == castOther.getProfessionalQualId());
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
    
        return STAFF_PROFESSIONAL_ID_STAFF_ID + staffId + PROFESSIONAL_QUAL_ID + professionalQualId;
    }
    
}
