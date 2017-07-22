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
 * This class is to associates all the properties of Religion domain object.
 * 
 * @author Virtusa Corporation
 */
public class Religion extends BaseDomain {
    
    /**
     * Holds the serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String " religion id = ". */
    private static final String RELIGION_ID = " religion id = ";
    
    /** The constant for String " description = ". */
    private static final String DESCRIPTION = " description = ";
    
    /** The constant for String " modified date/time ". */
    private static final String MODIFIED_DATE_TIME = " modified date/time ";
    
    /**
     * Represents the unique id for Religion.
     */
    private int religionId;
    
    /**
     * Holds description for Religion.
     */
    private String description;
    
    /**
     * Default constructor.
     */
    public Religion() {
    
    }
    
    /**
     * Constructor with parameter religionId.
     * 
     * @param intReligionId type int
     */
    public Religion(int intReligionId) {
    
        this.religionId = intReligionId;
    }
    
    /**
     * Get the religion id for the religion.
     * 
     * @return the religionId
     */
    public int getReligionId() {
    
        return religionId;
    }
    
    /**
     * Set the religion id for the religion.
     * 
     * @param intReligionId the religionId to set
     */
    public void setReligionId(int intReligionId) {
    
        this.religionId = intReligionId;
    }
    
    /**
     * Get the description for the religion.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description for the religion.
     * 
     * @param descriptionStr the description to set
     */
    public void setDescription(String descriptionStr) {
    
        this.description = descriptionStr;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return RELIGION_ID + this.religionId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
