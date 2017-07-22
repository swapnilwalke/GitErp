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
 * This class is to associates all the properties of Race domain object.
 * 
 * @author Virtusa Corporation
 */

public class Race extends BaseDomain implements Serializable{
	
	 /** attribute for holding string modified date and time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** attribute for holding string description. */
    private static final String DESCRIPTION = " description = ";
    
    /** attribute for holding string nationality id. */
    private static final String RACE_ID = "race id = ";
    /**
     * Holds the serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the unique id for Race.
     */
    private int raceId;
    
    /**
     * Holds description for Race.
     */
    private String description;
    
    
    /**
     * default constructor.
     */
    public Race() {
       
    }
    /**
     * Get the race id for the race.
     * 
     * @return the raceId
     */
    public final int getRaceId() {
    
        return raceId;
    }
    /**
     * Set the race id for the race.
     * 
     * @param intRaceId the raceId to set
     */
    public final void setRaceId(final int intRaceId) {
    
        this.raceId = intRaceId;
    }
    /**
     * Get the description for the race.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    /**
     * Set the description for the race.
     * 
     * @param strDescription the description to set
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
    
        return RACE_ID + this.raceId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }

}
