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
 * Seminar is to associates all the properties of Seminar domain object.
 * 
 * @author Virtusa Corporation
 */
public class Seminar extends BaseDomain {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Represents the unique id for Seminar. */
    private int seminarId;
    
    /** Holds description for Seminar. */
    private String description;
    
    /**
     * Get the unique id for seminar.
     * 
     * @return the seminarId
     */
    public int getSeminarId() {
    
        return seminarId;
    }
    
    /**
     * Set the unique id for seminar.
     * 
     * @param intSeminarId the seminarId to set
     */
    public void setSeminarId(final int intSeminarId) {
    
        this.seminarId = intSeminarId;
    }
    
    /**
     * Get the description for seminar.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description for seminar.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return " seminar id = " + this.seminarId + " description " + this.description + " modified date/time = "
                + this.getModifiedTime();
    }
}
