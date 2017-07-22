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
 * This class represents all the properties of a Country domain object.
 * 
 * @author Virtusa Corporation
 */
public class Country extends BaseDomain implements Serializable {
    
    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a Country.
     */
    private int countryId;
    
    /**
     * Represents the name of a Country.
     */
    private String countryName;
    
    /**
     * Represents the code of a Country.
     */
     private String countryCode;
    
    
    /** key to hold string startFrom. */
    private static final String START_FROM = ", startFrom=";
    
    /** key to hold string maxNumber. */
    private static final String MAX_NUMBER = ", maxNumber=";
    
    /**
     * maximum number of records.
     */
    private int maxNumber;
    
    /**
     * starting row number.
     */
    private int startFrom;
    
    /**
     * Gets the Maximum number.
     * 
     * @return the maxNumber.
     */
    public int getMaxNumber() {
    
        return maxNumber;
    }
    
    /**
     * Sets the Maximum number.
     * 
     * @param maximumNumber number of records to be displays.
     */
    public void setMaxNumber(int maximumNumber) {
    
        this.maxNumber = maximumNumber;
    }
    
    /**
     * Gets the Starting number.
     * 
     * @return the startFrom.
     */
    public int getStartFrom() {
    
        return startFrom;
    }
    
    /**
     * Sets the Starting number.
     * 
     * @param strtFrom starting row number for search.
     */
    public void setStartFrom(int strtFrom) {
    
        this.startFrom = strtFrom;
    }
    
    /**
     * Constructor for country.
     */
    public Country() {

    }

    /**
     * Get the countryId.
     * 
     * @return countryId.
     */    
    public final int getCountryId() {
        
        return countryId;
    }

    /**
     * Set the countryId.
     * 
     * @param countryIdVal to be set.
     */    
    public final void setCountryId(int countryIdVal) {
        
        this.countryId = countryIdVal;
    }

    /**
     * Get the country name.
     * 
     * @return the country name.
     */    
    public final String getCountryName() {
        
        return countryName;
    }

    /**
     * Set the country name.
     * 
     * @param countryNameVal the country name to be set.
     */    
    public final void setCountryName(String countryNameVal) {
        
        this.countryName = countryNameVal;
    }
    /**
     * String attribute for Modified date time.
     */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /**
     * String attribute for name.
     */
    private static final String COUNTRY_NAME = " country name = ";
    
    /**
     * String attribute for City id.
     */
    private static final String COUNTRY_ID = "country id = ";

    /**
     * The String representation of Country domain object.
     * 
     * @return string representation of country.
     * 
     */
    public String toString() {

        return COUNTRY_ID + this.countryId + COUNTRY_NAME + this.countryName + MODIFIED_DATE_TIME +
        this.getModifiedTime()+ MAX_NUMBER
        + this.maxNumber + START_FROM +this.startFrom ;
    }

    /**
     * Get the country code.
     * 
     * @return the country code.
     */ 
     public String getCountryCode() {
		return countryCode;
	}

    /**
     * Set the country code.
     * 
     * @param countryCodeVal the country name to be set.
     */ 
    
    public void setCountryCode(String countryCodeVal) {
		
		this.countryCode =  countryCodeVal;
	}

	
	
    
    
    
}
