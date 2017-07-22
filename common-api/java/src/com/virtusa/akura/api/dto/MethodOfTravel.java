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
 * This class is to associates all the properties of MethodOfTravel domain object.
 * 
 * @author Virtusa Corporation
 */
public class MethodOfTravel extends BaseDomain {
    
    /** attribute for holding string modified date and time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";
    
    /** attribute for holding string travel method. */
    private static final String TRAVEL_METHOD = " travel method = ";
    
    /** attribute for holding string travel id. */
    private static final String TRAVEL_ID = " travel id = ";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents a unique id for MethodOfTravel.
     */
    private int travelId;
    
    /**
     * Represents travel method of MethodOfTravel.
     */
    private String travelMethod;
    
    /**
     * Default constructor.
     */
    public MethodOfTravel() {
    
    }
    
    /**
     * Overloaded constructor with parameter travelId.
     * 
     * @param intTravelId type int
     */
    public MethodOfTravel(int intTravelId) {
    
        this.travelId = intTravelId;
    }
    
    /**
     * Get the travel id of MethodOfTravel object.
     * 
     * @return the travelId
     */
    public int getTravelId() {
    
        return travelId;
    }
    
    /**
     * Set the travel id to MethodOfTravel object.
     * 
     * @param intTravelId the travelId to set
     */
    public void setTravelId(int intTravelId) {
    
        this.travelId = intTravelId;
    }
    
    /**
     * Get the travel method of MethodOfTravel object.
     * 
     * @return the travelMethod
     */
    public String getTravelMethod() {
    
        return travelMethod;
    }
    
    /**
     * Set the travel method to MethodOfTravel object.
     * 
     * @param strTravelMethod the travelMethod to set
     */
    public void setTravelMethod(String strTravelMethod) {
    
        this.travelMethod = strTravelMethod;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return TRAVEL_ID + this.travelId + TRAVEL_METHOD + this.travelMethod + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
