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
 * Domain object to map data for StaffCategory.
 * 
 * @author Virtusa Corporation
 */
public class StaffCategory extends BaseDomain implements Serializable {
    
    /** The constant for String "StaffCategory catogaryID=". */
    private static final String STAFF_CATEGORY_CATOGARY_ID = "StaffCategory catogaryID=";
    
    /** The constant for String ", academic=". */
    private static final String ACADEMIC = ", academic=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the id of the StaffCatagory.
     */
    private int catogaryID;
    
    /**
     * boolean to hold if the staff is academic.
     */
    private boolean academic;
    
    /**
     * Holds the description of the StaffCatagory.
     */
    private String description;

    /**
     * Get the Staff Category type.
     * 
     * @return the catogaryID.
     */
    public int getCatogaryID() {
    
        return catogaryID;
    }
    
    /**
     * Set the Staff category.
     * 
     * @param intCategoryID the catogaryID to set.
     */
    public void setCatogaryID(final int intCategoryID) {
    
        this.catogaryID = intCategoryID;
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
    public void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Check Staff type.
     * 
     * @return the academic.
     */
    public boolean isAcademic() {
    
        return academic;
    }
    
    /**
     * Sets Staff type.
     * 
     * @param checkAcademic if academic then true.
     */
    public void setAcademic(boolean checkAcademic) {
    
        this.academic = checkAcademic;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STAFF_CATEGORY_CATOGARY_ID + catogaryID + ACADEMIC + academic + DESCRIPTION + description;
    }
    
}
