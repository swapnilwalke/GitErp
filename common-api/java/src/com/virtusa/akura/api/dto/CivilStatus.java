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

/**
 * This class represents all the properties of a Civil Status domain object.
 * 
 * @author Virtusa Corporation
 */

public class CivilStatus extends BaseDomain implements Serializable{
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** attribute for holding string description. */
    private static final String DESCRIPTION = " description = ";
    
    /** attribute for holding civil status id. */
    private static final String CIVIL_STATUS_ID = "civil status id = ";
    
    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the unique id for a Civil Status.
     */
    private int civilStatusId;
    
    /**
     * Represents the description for a Civil Status.
     */
    private String description;
    
    /**
     * default constructor.
     */
    public CivilStatus() {

    }
    
    
    /**
     * Returns the id of the Civil Status object.
     * 
     * @return civil status id.
     */
    public int getCivilStatusId() {

        return civilStatusId;
    }
    
    /**
     * Sets the id of the Civil Status object.
     * 
     * @param intCivilStatusId - the id of the CivilStatus.
     */
    public void setCivilStatusId(int intCivilStatusId) {

        this.civilStatusId = intCivilStatusId;
    }
    
    /**
     * Returns the description of civil status object.
     * 
     * @return description.
     */
    public String getDescription() {

        return description;
    }
    
    /**
     * Sets the description of the CivilStatus.
     * 
     * @param strDescription - the description of the CivilStatus.
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return CIVIL_STATUS_ID + this.civilStatusId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
