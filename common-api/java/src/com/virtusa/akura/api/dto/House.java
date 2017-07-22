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
 * This class is to associates all the properties of House domain object.
 * 
 * @author Virtusa Corporation
 */
public class House extends BaseDomain {
    
    /** attribute for holding string modified date and time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";
    
    /** attribute for holding string house color. */
    private static final String HOUSE_COLOR = " house color ";
    
    /** attribute for holding string. */
    private static final String HOUSE_DESCRIPTION = " house description = ";
    
    /** attribute for holding string house name. */
    private static final String HOUSE_NAME = " house name = ";
    
    /** attribute for holding string house id. */
    private static final String HOUSE_ID = " house id = ";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the unique id for House.
     */
    private int houseId;
    
    /**
     * Holds color of the house.
     */
    private String colour;
    
    /**
     * Holds description for house.
     */
    private String description;
    
    /**
     * Holds name of the house.
     */
    private String name;
    
    /**
     * Holds captain for house.
     */
    private Student studentByCaptain;
    
    /**
     * Holds viceCaptain for house.
     */
    private Student studentByViceCaptain;
    
    /**
     * Default constructor.
     */
    public House() {
    
    }
    
    /**
     * Constructor with parameter houseId.
     * 
     * @param intHouseId type int
     */
    public House(int intHouseId) {
    
        this.houseId = intHouseId;
    }
    
    /**
     * Get the house id for the house.
     * 
     * @return the houseId
     */
    public int getHouseId() {
    
        return houseId;
    }
    
    /**
     * Set the house id for the house.
     * 
     * @param intHouseId the houseId to set
     */
    public void setHouseId(int intHouseId) {
    
        this.houseId = intHouseId;
    }
    
    /**
     * Get the name of the house.
     * 
     * @return the name
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * Set the name for the house.
     * 
     * @param strName the name to set
     */
    public void setName(String strName) {
    
        this.name = strName;
    }
    
    /**
     * Get the color of the house.
     * 
     * @return the color
     */
    public String getColour() {
    
        return colour;
    }
    
    /**
     * Set the color for the house.
     * 
     * @param strColour the color to set
     */
    public void setColour(String strColour) {
    
        this.colour = strColour;
    }
    
    /**
     * Get the description of the house.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description for the house.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Get the captain of the house.
     * 
     * @return the studentByCaptain
     */
    public Student getStudentByCaptain() {
    
        return studentByCaptain;
    }
    
    /**
     * Set the captain for the house.
     * 
     * @param objStudentByCaptain the studentByCaptain to set
     */
    public void setStudentByCaptain(Student objStudentByCaptain) {
    
        this.studentByCaptain = objStudentByCaptain;
    }
    
    /**
     * Get the vice captain of the house.
     * 
     * @return the studentByViceCaptain
     */
    public Student getStudentByViceCaptain() {
    
        return studentByViceCaptain;
    }
    
    /**
     * Set the vice captain for the house.
     * 
     * @param objStudentByViceCaptain the studentByViceCaptain to set
     */
    public void setStudentByViceCaptain(Student objStudentByViceCaptain) {
    
        this.studentByViceCaptain = objStudentByViceCaptain;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return HOUSE_ID + this.houseId + HOUSE_NAME + this.name + HOUSE_DESCRIPTION + this.description + HOUSE_COLOR
                + this.colour + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
    
}
