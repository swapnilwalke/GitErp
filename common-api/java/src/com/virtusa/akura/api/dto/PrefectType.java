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
 * This class is to associates properties of PrefectType.
 * 
 * @author Virtusa Corporation
 */
public class PrefectType extends BaseDomain implements Serializable {
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = ", description=";
    
    /** attribute for holding string. */
    private static final String PREFECT_TYPE_ID = "prefectTypeId=";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds prefect type id.
     */
    private int prefectTypeId;
    
    /**
     * Holds description.
     */
    private String description;
    
    /**
     * Default constructor.
     */
    public PrefectType() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intPrefectTypeId type int
     * @param strDescription type string
     */
    public PrefectType(int intPrefectTypeId, String strDescription) {
    
        this.prefectTypeId = intPrefectTypeId;
        this.description = strDescription;
    }
    
    /**
     * @return the prefectTypeId
     */
    public int getPrefectTypeId() {
    
        return prefectTypeId;
    }
    
    /**
     * @param intPrefectTypeId the prefectTypeId to set
     */
    public void setPrefectTypeId(int intPrefectTypeId) {
    
        this.prefectTypeId = intPrefectTypeId;
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
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return PREFECT_TYPE_ID + prefectTypeId + DESCRIPTION + description;
    }
    
}
