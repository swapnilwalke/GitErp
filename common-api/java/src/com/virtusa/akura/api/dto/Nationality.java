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
 * This class represents all the properties of a Nationality domain object.
 * 
 * @author Virtusa Corporation
 */
public class Nationality extends BaseDomain implements Serializable {
    
    /** attribute for holding string modified date and time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** attribute for holding string description. */
    private static final String DESCRIPTION = " description = ";
    
    /** attribute for holding string nationality id. */
    private static final String NATIONALITY_ID = "nationality id = ";
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a Nationality.
     */
    private int nationalityId;
    
    /**
     * Represents the description for a Nationality.
     */
    private String description;
    
    /**
     * Constructs a new Nationality object.
     */
    public Nationality() {
    
    }
    
    /**
     * Returns the id of the Nationality.
     * 
     * @return - the nationality id.
     */
    public final int getNationalityId() {
    
        return nationalityId;
    }
    
    /**
     * Sets the id of the Nationality.
     * 
     * @param intNationalityId - the id of the Nationality.
     */
    public final void setNationalityId(final int intNationalityId) {
    
        this.nationalityId = intNationalityId;
    }
    
    /**
     * Returns the description of a Nationality.
     * 
     * @return - the description of the Nationality.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description of the Nationality.
     * 
     * @param strDescription - the description of the Nationality.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return NATIONALITY_ID + this.nationalityId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
