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
 * This class represents all properties of FaithLife domain object.
 * 
 * @author Virtusa Corporation
 */
public class FaithLifeTemplate {
    
    /** attribute for holding string. */
    private static final String CATEGORY = ", category=";
    
    /** attribute for holding string. */
    private static final String FAITH_LIFE_DESC = "faithLifeDesc=";
    
    /**
     * used to hold faith life description.
     */
    private String faithLifeDesc;
    
    /**
     * used to hold category.
     */
    private int category;
    
    /**
     * used to get faith life description.
     * 
     * @return faithLifeDesc
     */
    public String getFaithLifeDesc() {
    
        return faithLifeDesc;
    }
    
    /**
     * used to set faith life description.
     * 
     * @param objFaithLifeDesc faith life description.
     */
    public void setFaithLifeDesc(String objFaithLifeDesc) {
    
        this.faithLifeDesc = objFaithLifeDesc;
    }
    
    /**
     * used to get category.
     * 
     * @return category
     */
    public int getCategory() {
    
        return category;
    }
    
    /**
     * used to set category.
     * 
     * @param objCategory - Category
     */
    public void setCategory(int objCategory) {
    
        this.category = objCategory;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return FAITH_LIFE_DESC + faithLifeDesc + CATEGORY + category;
    }
    
}
