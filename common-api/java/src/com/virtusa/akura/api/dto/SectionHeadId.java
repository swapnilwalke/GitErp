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
 * Domain object to map data for SectionHeadId.
 * 
 * @author Virtusa Corporation
 */
public class SectionHeadId implements java.io.Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "SectionHeadId staffId=". */
    private static final String SECTION_HEAD_ID_STAFF_ID = "SectionHeadId staffId=";
    
    /** The constant for String ", startDate=". */
    private static final String START_DATE = ", startDate=";
    
    /**
     * Holds the id of the Staff.
     */
    private int staffId;
    
    /**
     * Holds the start date of the SectionHead.
     */
    private Date startDate;
    
    /**
     * Default constructor.
     */
    public SectionHeadId() {
    
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
     * @return the startDate
     */
    public Date getStartDate() {
    
        if (startDate != null) {
            
            return (Date) startDate.clone();
        }
        
        return null;
    }
    
    /**
     * @param dtStartDate the startDate to set
     */
    public void setStartDate(Date dtStartDate) {
    
        if (dtStartDate != null) {
            
            this.startDate = (Date) dtStartDate.clone();
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
        if (!(other instanceof SectionHeadId)) {
            return false;
        }
        SectionHeadId castOther = (SectionHeadId) other;
        
        return (this.getStaffId() == castOther.getStaffId())
                && ((this.getStartDate() == castOther.getStartDate()) || (this.getStartDate() != null
                        && castOther.getStartDate() != null && this.getStartDate().equals(castOther.getStartDate())));
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
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
    
        return SECTION_HEAD_ID_STAFF_ID + staffId + START_DATE + startDate;
    }
    
}
