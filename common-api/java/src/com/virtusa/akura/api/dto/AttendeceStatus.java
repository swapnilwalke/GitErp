/* < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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
 * Dto to hold attendees status and details .
 * 
 * @author Virtusa Corporation
 */
public class AttendeceStatus extends BaseDomain {
    
    /** Default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** constant to hold present. */
    private static final String PRESENT = "Present";
    
    /** constant to hold absent. */
    private static final String ABSENT = "Absent";
    
    /** constant to hold no time entry. */
    public static final String DASH = "-";
    
    /** name of day of the week . */
    private String day;
    
    /**
     * Absent/present default is absent. no setter for this property.
     */
    private String status = ABSENT;
    
    /** time in. */
    private String timeIn = DASH;
    
    /** time out . */
    private String timeOut = DASH;
    
    /** true=absent ,false = present. */
    private boolean absent = true;
    
    /** hold description. */
    private String description;
    
    /**
     * get the day name.
     * 
     * @return name of the day
     */
    public String getDay() {
    
        return day;
    }
    
    /**
     * set the day name (Sunday,Monday,..).
     * 
     * @param dayArg day name
     */
    public void setDay(String dayArg) {
    
        this.day = dayArg;
    }
    
    /**
     * get the status.
     * 
     * @return status
     */
    public String getStatus() {
    
        return status;
    }
    
    /**
     * get description.
     * 
     * @return description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * string description to set.
     * 
     * @param descriptionArg object to set
     */
    public void setDescription(String descriptionArg) {
    
        this.description = descriptionArg;
    }
    
    /**
     * get time in.
     * 
     * @return string
     */
    public String getTimeIn() {
    
        return timeIn;
    }
    
    /**
     * set time in.
     * 
     * @param timeInArg string
     */
    public void setTimeIn(String timeInArg) {
    
        this.timeIn = timeInArg;
    }
    
    /**
     * get time out.
     * @return string
     */
    public String getTimeOut() {
    
        return timeOut;
    }
    
    /**
     * setter for time out.
     * @param timeOutArg value to set
     */
    public void setTimeOut(String timeOutArg) {
    
        this.timeOut = timeOutArg;
    }
    
    /**
     * get isAbsent, true for absent and false for present.
     * @return boolean
     */
    public boolean isAbsent() {
    
        return absent;
    }
    
    /**
     * set absent (true/false). status attribute will set automatically.
     * 
     * @param absentArg value to set
     */
    public void setAbsent(boolean absentArg) {
    
        this.absent = absentArg;
        if (absentArg) {
            this.status = ABSENT;
        } else {
            this.status = PRESENT;
        }
    }
    
}
