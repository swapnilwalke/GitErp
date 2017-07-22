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

import java.util.Date;

/**
 * This class is to associates all the properties of MeritAward domain object.
 * 
 * @author Virtusa Corporation
 */
public class MeritAward implements java.io.Serializable {
    
    /** attribute for holding string. */
    private static final String MODIFIED_TIME = ", modifiedTime=";
    
    /** attribute for holding string. */
    private static final String AWARD_DESCRIPTION = ", awardDescription=";
    
    /** attribute for holding string. */
    private static final String MERIT_AWARDS_ID = "meritAwardsId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property meritAwardsId type int.
     */
    private int meritAwardsId;
    
    /**
     * property awardDescription type String.
     */
    private String awardDescription;
    
    /**
     * property modifiedTime type Date.
     */
    private Date modifiedTime;
    
    /**
     * Default constructor.
     */
    public MeritAward() {
    
    }
    
    /**
     * Overloaded constructor with one parameter.
     * 
     * @param intMeritAwardsId type int
     */
    public MeritAward(int intMeritAwardsId) {
    
        this.meritAwardsId = intMeritAwardsId;
        
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intMeritAwardsId type int
     * @param strAwardDescription type String
     * @param dtModifiedTime type Date
     */
    public MeritAward(int intMeritAwardsId, String strAwardDescription, Date dtModifiedTime) {
    
        this.meritAwardsId = intMeritAwardsId;
        this.awardDescription = strAwardDescription;
        
        if (dtModifiedTime != null) {
            this.modifiedTime = (Date) dtModifiedTime.clone();
        }
    }
    
    /**
     * @return the meritAwardsId
     */
    public int getMeritAwardsId() {
    
        return meritAwardsId;
    }
    
    /**
     * @param intMeritAwardsId the meritAwardsId to set
     */
    public void setMeritAwardsId(int intMeritAwardsId) {
    
        this.meritAwardsId = intMeritAwardsId;
    }
    
    /**
     * @return the awardDescription
     */
    public String getAwardDescription() {
    
        return awardDescription;
    }
    
    /**
     * @param strAwardDescription the awardDescription to set
     */
    public void setAwardDescription(String strAwardDescription) {
    
        this.awardDescription = strAwardDescription;
    }
    
    /**
     * @return the modifiedTime
     */
    public Date getModifiedTime() {
    
        if (modifiedTime != null) {
            
            return (Date) modifiedTime.clone();
        }
        
        return null;
    }
    
    /**
     * @param dtModifiedTime the modifiedTime to set
     */
    public void setModifiedTime(Date dtModifiedTime) {
    
        if (dtModifiedTime != null) {
            
            this.modifiedTime = (Date) dtModifiedTime.clone();
        }
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return MERIT_AWARDS_ID + meritAwardsId + AWARD_DESCRIPTION + awardDescription + MODIFIED_TIME + modifiedTime;
    }
    
}
