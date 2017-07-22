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
 * Domain object to map data for SportSub.
 * 
 * @author Virtusa Corporation
 */
public class SportSub extends BaseDomain implements Serializable {
    
    /** The constant for String "SportSub sportSubId=". */
    private static final String SPORT_SUB_SPORT_SUB_ID = "SportSub sportSubId=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Unique id of the sportSub object.
     */
    
    private int sportSubId;
    
    /**
     * descriptive text for sportSub.
     */
    
    private String description;
    
    /**
     * list of sportCategories for the sportSub, returned as a unique values of SportCategory.
     */
    
    private Set<SportCategory> sportCategories = new HashSet<SportCategory>(0);
    
    /**
     * default constructor to create SportSub object.
     */
    
    public SportSub() {
    
    }
    
    /**
     * Constructor with an integer parameter to create SportSub type object.
     * 
     * @param intId the sportSubId to pass.
     */
    
    public SportSub(int intId) {
    
        sportSubId = intId;
    }
    
    /**
     * gets the Id of the sportSub object.
     * 
     * @return the sportSubId in integer
     */
    public int getSportSubId() {
    
        return sportSubId;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportSubId in integer.
     */
    public void setSportSubId(int intId) {
    
        sportSubId = intId;
    }
    
    /**
     * gets the description of the sportSub.
     * 
     * @return the description in string.
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * sets the description of the sportSub.
     * 
     * @param strDescript the description in string.
     */
    public void setDescription(String strDescript) {
    
        description = strDescript;
    }
    
    /**
     * gets the sports categories of the sportSub.
     * 
     * @return the sportCategories as set
     */
    
    public Set<SportCategory> getSportCategories() {
    
        return sportCategories;
    }
    
    /**
     * sets sportsCategories of the sportSub.
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
    
        return SPORT_SUB_SPORT_SUB_ID + sportSubId + DESCRIPTION + description;
    }
    
}
