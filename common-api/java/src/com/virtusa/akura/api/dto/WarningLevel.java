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

/**
 * This class represents all the properties of WarningLevel.
 * 
 * @author Virtusa Corporation
 */

public class WarningLevel extends BaseDomain implements Serializable {
    
    /** string constant for holding `colour`. */
    private static final String COLOUR = " colour = ";
    
    /** string constant for holding ` description = `. */
    private static final String DESCRIPTION = " description = ";
    
    /** string constant for holding ` modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** string constant for holding `warning level id = `. */
    private static final String WARNING_LEVEL_ID = "warning level id = ";
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The unique ID for the warning level class.
     */
    private int warningLevelId;
    
    /**
     * Text description about the student warning level.
     */
    private String description;
    
    /**
     * Text description about the student warning level which indicates by a color.
     */
    private String color;

    /**
     * Default constructor to constructs a new Warning Level object.
     */
    public WarningLevel() {
    
    }
    
    /**
     * Constructs a new Warning Level object with the given warning level id.
     * 
     * @param warnLevelId - Warning Level Id.
     */
    public WarningLevel(int warnLevelId) {
    
        this.warningLevelId = warnLevelId;
    }
    
    /**
     * Gets the warning level Id.
     * 
     * @return warningLevelId - integer
     */
    public int getWarningLevelId() {
    
        return this.warningLevelId;
    }
    
    /**
     * Sets the warning level Id.
     * 
     * @param warnLevelId - Integer
     */
    public void setWarningLevelId(int warnLevelId) {
    
        this.warningLevelId = warnLevelId;
    }
    
    /**
     * Gets the text description.
     * 
     * @return description - string
     */
    public String getDescription() {
    
        return this.description;
    }
    
    /**
     * Sets the text description.
     * 
     * @param desc - String.
     */
    public void setDescription(String desc) {
    
        this.description = desc;
    }
    
    /**
     * Gets the text description which indicates the color.
     * 
     * @return color - string
     */
    public String getColor() {
    
        return this.color;
    }
    
    /**
     * Sets the text description which indicates the color.
     * 
     * @param strcolor - String.
     */
    public void setColor(String strcolor) {
    
        this.color = strcolor;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return WARNING_LEVEL_ID + this.warningLevelId + DESCRIPTION + this.description + COLOUR + this.color
                + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
