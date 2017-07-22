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
 * This class represents all the properties of a City domain object.
 * 
 * @author Virtusa Corporation
 */
public class City extends BaseDomain implements Serializable {
    
    /**
     * Represents the id for a City.
     */
    private int cityId;
    
    /**
     * Represents the description for a City.
     */
    private String description;
    
    /**
     * Represents the district for a City.
     */
    private District district;
    
    /**
     * Constructs a new City object.
     */
    public City() {
    
    }
    
    /**
     * Returns the id for this City object.
     * 
     * @return city id.
     */
    public final int getCityId() {
    
        return cityId;
    }
    
    /**
     * Sets the City id with the given key.
     * 
     * @param intCityId - the id of the City.
     */
    public final void setCityId(final int intCityId) {
    
        this.cityId = intCityId;
    }
    
    /**
     * Returns the description for this City object.
     * 
     * @return the description of the City.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description for this object.
     * 
     * @param strDescription - the description of the City.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns the District for this City object.
     * 
     * @return - the district of the City.
     */
    public final District getDistrict() {
    
        return district;
    }
    
    /**
     * Sets the district for this City object.
     * 
     * @param disDistrict - the district of the City.
     */
    public final void setDistrict(final District disDistrict) {
    
        this.district = disDistrict;
    }
    
    /**
     * String attribute for Modified date time.
     */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /**
     * String attribute for Description.
     */
    private static final String DESCRIPTION = " description = ";
    
    /**
     * String attribute for City id.
     */
    private static final String CITY_ID = "city id = ";
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return CITY_ID + this.cityId + DESCRIPTION + this.description + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
