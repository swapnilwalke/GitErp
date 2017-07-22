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
 * This class represents all the properties of a BloodGroup domain object.
 * 
 * @author Virtusa Corporation
 */
public class BloodGroup extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a BloodGroup.
     */
    private int bloodGroupId;
    
    /**
     * Represents the description for a BloodGroup.
     */
    private String description;
    
    /**
     * Constructs a new BloodGroup object.
     */
    public BloodGroup() {
    
    }
    
    /**
     * Returns the id of the BloodGroup.
     * 
     * @return the blood group id of this object.
     */
    public final int getBloodGroupId() {
    
        return bloodGroupId;
    }
    
    /**
     * Sets the BloodGroup id with the given key.
     * 
     * @param intBloodGroupId - the id of the BloodGroup.
     */
    public final void setBloodGroupId(final int intBloodGroupId) {
    
        this.bloodGroupId = intBloodGroupId;
    }
    
    /**
     * Returns the description of a BloodGroup.
     * 
     * @return - the description of the BloodGroup.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description of the BloodGroup.
     * 
     * @param strDescription - the description of this object.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * String attribute for Modified time.
     */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /**
     * String attribute for Description.
     */
    private static final String DESCRIPTION_VALUE = " description = ";
    
    /**
     * String attribute for Blood Group id.
     */
    private static final String BLOOD_GROUP_ID = "bloodGroup id = ";
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return BLOOD_GROUP_ID + this.bloodGroupId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
