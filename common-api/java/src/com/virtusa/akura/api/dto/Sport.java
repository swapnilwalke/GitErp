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
import java.util.HashSet;
import java.util.Set;

/**
 * Domain object to map data for Sport.
 * 
 * @author Virtusa Corporation
 */

public class Sport extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "Sport sportId=". */
    private static final String SPORT_SPORT_ID = "Sport sportId=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * Unique id of the sport item.
     */
    
    private int sportId;
    
    /**
     * descriptive text for sport.
     */
    
    private String description;
    
    /**
     * The time of modification done to sport object.
     */
    
    private Set<SportCategory> sportCategories = new HashSet<SportCategory>(0);
    
    /**
     * Default constructor to create sport object.
     */
    
    public Sport() {
    
    }
    
    /**
     * Constructor with an integer parameter to create Sport type object.
     * 
     * @param intId sportId of the sport object.
     */
    
    public Sport(int intId) {
    
        sportId = intId;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    
    public int getSportId() {
    
        return sportId;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    
    public void setSportId(int intId) {
    
        sportId = intId;
    }
    
    /**
     * gets the description of the sport.
     * 
     * @return the description in string.
     */
    
    public String getDescription() {
    
        return description;
    }
    
    /**
     * sets the description of the sport.
     * 
     * @param strDescription the description in string
     */
    
    public void setDescription(String strDescription) {
    
        description = strDescription;
    }
    
    /**
     * gets the sports categories of the sport.
     * 
     * @return the sportCategories as set
     */
    
    public Set<SportCategory> getSportCategories() {
    
        return sportCategories;
    }
    
    /**
     * sets sportsCategories of the sport.
     * 
     * @param collSportCategories the sportCategories as set
     */
    
    public void setSportCategories(Set<SportCategory> collSportCategories) {
    
        this.sportCategories = collSportCategories;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return SPORT_SPORT_ID + sportId + DESCRIPTION + description;
    }
    
}
