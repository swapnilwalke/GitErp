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
 * This class is to associates all the properties of AppointmentClassification domain object.
 * 
 * @author Virtusa Corporation
 */

public class AppointmentClassification extends BaseDomain implements Serializable {
    
    /** attribute for holding string. */
    private static final String CLASSIFICATION_ID = "classificationId=";
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property classificationId type integer.
     */
    private int classificationId;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * default constructor.
     */
    public AppointmentClassification() {

    }
    
    /**
     * Constructor with an integer parameter to create AppointmentClassification type object.
     * 
     * @param intClassificationId type int
     */
    public AppointmentClassification(int intClassificationId) {

        this.classificationId = intClassificationId;
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intClassificationId type int
     * @param strDescription type string
     */
    public AppointmentClassification(int intClassificationId, String strDescription) {

        this.classificationId = intClassificationId;
        this.description = strDescription;
    }
    
    /**
     * Get the classification id.
     * 
     * @return the classificationId.
     */
    public int getClassificationId() {

        return classificationId;
    }
    
    /**
     * Set the classification id.
     * 
     * @param intClassificationId the id to set.
     */
    public void setClassificationId(int intClassificationId) {

        this.classificationId = intClassificationId;
    }
    
    /**
     * Get the description.
     * 
     * @return the description.
     */
    public String getDescription() {

        return description;
    }
    
    /**
     * Set the description.
     * 
     * @param strDescription the description to set.
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return CLASSIFICATION_ID + classificationId + DESCRIPTION + description;
    }
    
}
