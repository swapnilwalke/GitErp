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

// Generated Aug 17, 2011 4:20:13 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * @author Virtusa Corporation
 */
public class Holiday extends BaseDomain implements java.io.Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property holidayId type int.
     */
    private int holidayId;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * property startDate type Date.
     */
    private Date startDate;
    
    /**
     * property endDate type Date.
     */
    private Date endDate;
    
    /**
     * property modifiedTime type Date.
     */
    private Date modifiedTime;
    
    /**
     * Default constructor.
     */
    public Holiday() {
    
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param intHolidayId type int
     * @param strDescription type String
     * @param dtModifiedTime type date
     */
    public Holiday(int intHolidayId, String strDescription, Date dtModifiedTime) {
    
        this.holidayId = intHolidayId;
        this.description = strDescription;
        
        if (dtModifiedTime != null) {
            this.modifiedTime = (Date) dtModifiedTime.clone();
        }
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param intHolidayId type int
     * @param strDescription type String
     * @param startDateObj type date
     * @param endDateObj type date
     * @param dtModifiedTime type date
     */
    public Holiday(int intHolidayId, String strDescription, Date startDateObj, Date endDateObj, Date dtModifiedTime) {
    
        this.holidayId = intHolidayId;
        this.description = strDescription;
        
        if (startDateObj != null) {
            this.startDate = (Date) startDateObj.clone();
        }
        if (endDateObj != null) {
            this.endDate = (Date) endDateObj.clone();
        }
        
        if (dtModifiedTime != null) {
            this.modifiedTime = (Date) dtModifiedTime.clone();
        }
    }
    
    /**
     * @return the holidayId
     */
    public int getHolidayId() {
    
        return holidayId;
    }
    
    /**
     * @param intHolidayId the holidayId to set
     */
    public void setHolidayId(int intHolidayId) {
    
        this.holidayId = intHolidayId;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * @return the date
     */
    public Date getStartDate() {
    
        if (startDate != null) {
            return (Date) startDate.clone();
        }
        return null;
    }
    
    /**
     * @param dtDate the start date to set
     */
    public void setStartDate(Date dtDate) {
    
        if (dtDate != null) {
            this.startDate = (Date) dtDate.clone();
        }
    }
    
    /**
     * @return the end date
     */
    public Date getEndDate() {
    
        if (endDate != null) {
            return (Date) endDate.clone();
        }
        return null;
    }
    
    /**
     * @param dtDate the end date to set
     */
    public void setEndDate(Date dtDate) {
    
        if (dtDate != null) {
            this.endDate = (Date) dtDate.clone();
        }
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
    
}
