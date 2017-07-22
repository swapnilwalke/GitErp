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
 * This class represents all properties of FaithLifeComment domain object.
 * 
 * @author Virtusa Corporation
 */
public class FaithLifeComment extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string description. */
    private static final String DESCRIPTION = ", description=";
    
    /** key to hold string faithLifeCommentId. */
    private static final String FAITH_LIFE_COMMENT_ID = "faithLifeCommentId=";
    
    /**
     * Holds faithLifeCommentId.
     */
    private int faithLifeCommentId;
    
    /**
     * Holds faithLife comment description.
     */
    private String description;
    
    /**
     * Represents the FaithLifeCategory of the FaithLifeComment.
     */
    private FaithLifeCategory faithLifeCategory;
    
    /**
     * Default constructor to create FaithLifeComment object.
     */
    public FaithLifeComment() {
    
    }
    
    /**
     * gets the Id of the FaithLifeComment object.
     * 
     * @return the faithLifeCommentId
     */
    public int getFaithLifeCommentId() {
    
        return faithLifeCommentId;
    }
    
    /**
     * sets the Id of the FaithLifeComment object.
     * 
     * @param intFaithLifeCommentId the faithLifeCommentId to set
     */
    public void setFaithLifeCommentId(int intFaithLifeCommentId) {
    
        this.faithLifeCommentId = intFaithLifeCommentId;
    }
    
    /**
     * gets the description of the FaithLifeComment object.
     * 
     * @return the sets the Id of the FaithLifeComment object.
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * sets the description of the FaithLifeComment object.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        description = strDescription;
    }
    
    /**
     * gets the faithLifeCategory of the FaithLifeComment object.
     * 
     * @return the faithLifeCategory
     */
    public FaithLifeCategory getFaithLifeCategory() {
    
        return faithLifeCategory;
    }
    
    /**
     * sets the faithLifeCategory object of the FaithLifeComment object.
     * 
     * @param faithLifeCategoryObj the faithLifeCategory to set
     */
    public void setFaithLifeCategory(FaithLifeCategory faithLifeCategoryObj) {
    
        this.faithLifeCategory = faithLifeCategoryObj;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return FAITH_LIFE_COMMENT_ID + faithLifeCommentId + DESCRIPTION + description;
    }
    
}
