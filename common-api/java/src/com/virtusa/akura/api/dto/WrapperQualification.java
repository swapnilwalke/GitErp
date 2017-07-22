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

/**
 * This class represents all the properties of WrapperQualification.
 * 
 * @author Virtusa Corporation
 */
public class WrapperQualification extends BaseDomain implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** string constant for holding `modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** Represents the StaffEducation. */
    private StaffEducation staffEducation;
    
    /** Represents the StaffProfessional. */
    private StaffProfessional staffProfessional;
    
    /**
     * Constructs a new WrapperQualification object.
     */
    public WrapperQualification() {
    
        super();
        staffEducation = new StaffEducation();
        staffProfessional = new StaffProfessional();
    }
    
    /**
     * Returns the StaffEducation object for this WrapperQualification object.
     * 
     * @return the StaffEducation
     */
    public StaffEducation getStaffEducation() {
    
        return staffEducation;
    }
    
    /**
     * Sets the StaffEducation object for this WrapperQualification object.
     * 
     * @param objStaffEducation the staffEducation to set
     */
    public void setStaffEducation(StaffEducation objStaffEducation) {
    
        this.staffEducation = objStaffEducation;
    }
    
    /**
     * Returns the StaffProfessional object for this WrapperQualification object.
     * 
     * @return the StaffEducation
     */
    public StaffProfessional getStaffProfessional() {
    
        return staffProfessional;
    }
    
    /**
     * Sets the staffProfessional object for this WrapperQualification object.
     * 
     * @param objstaffProfessional the staffProfessional to set
     */
    public void setStaffProfessional(StaffProfessional objstaffProfessional) {
    
        this.staffProfessional = objstaffProfessional;
    }
    
    /**
     * Returns the details for the WrapperQualification object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return getStaffEducation().toString() + getStaffProfessional().toString() + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
    
}
