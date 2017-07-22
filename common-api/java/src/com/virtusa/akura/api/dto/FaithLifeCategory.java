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
 * This class represents all properties of FaithLifeCategory domain object.
 * 
 * @author Virtusa Corporation
 */
public class FaithLifeCategory extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string description. */
    private static final String DESCRIPTION = ", description=";
    
    /** key to hold string faithLifeCategoryId. */
    private static final String FAITH_LIFE_CATEGORY_ID = "faithLifeCategoryId=";
    
    /**
     * Holds faithLifeCategoryId.
     */
    private int faithLifeCategoryId;
    
    /**
     * Holds faithLife category description.
     */
    private String description;
    
    /**
     * Default constructor to create FaithLifeCategory object.
     */
    public FaithLifeCategory() {
    
    }
    
    /**
     * gets the Id of the FaithLifeCategory object.
     * 
     * @return the faithLifeCategoryId
     */
    public int getFaithLifeCategoryId() {
    
        return faithLifeCategoryId;
    }
    
    /**
     * sets the Id of the FaithLifeCategory object.
     * 
     * @param intFaithLifeCategoryId the faithLifeCategoryId to set
     */
    public void setFaithLifeCategoryId(int intFaithLifeCategoryId) {
    
        this.faithLifeCategoryId = intFaithLifeCategoryId;
    }
    
    /**
     * gets the description of the FaithLifeCategory object.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * sets the description of the FaithLifeCategory object.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        description = strDescription;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return FAITH_LIFE_CATEGORY_ID + faithLifeCategoryId + DESCRIPTION + description;
    }
    
}
