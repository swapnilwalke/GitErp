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
import java.util.Date;

/**
 * This represents that base entity for all POJOs and DTO objects. It contains common attributes and get/set
 * methods that can be reused across all objects. All DTOs and POJOs in the application should extend this
 * class.
 * 
 * @author Virtusa Corporation
 */

public class BaseDomain implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = -5875802756035670138L;
    
    /**
     * The latest time that the object was modified.
     */
    private Date modifiedTime;
    
    /**
     * Constructs a default BaseDomain object.
     */
    public BaseDomain() {
    
    }
    
    /**
     * Returns the last modified time for this object.
     * 
     * @return - The last updated time.
     */
    public Date getModifiedTime() {
    
        if (modifiedTime != null) {
            
            return (Date) this.modifiedTime.clone();
        }
        return null;
    }
    
    /**
     * Sets the latest time at which the object was updated.
     * 
     * @param modTime - the latest time that the object was modified (systime).
     */
    public void setModifiedTime(Date modTime) {
    
        if (modTime != null) {
            this.modifiedTime = (Date) modTime.clone();
        }
    }
    
}
