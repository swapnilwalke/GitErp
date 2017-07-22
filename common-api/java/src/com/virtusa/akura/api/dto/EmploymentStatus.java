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
 * This class is to associates all the properties of EmploymentStatus domain object.
 * 
 * @author Virtusa Corporation
 */

public class EmploymentStatus extends BaseDomain implements Serializable {
    
    /**
     * Holds the serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the unique id for EmploymentStatus.
     */
    private int employmentStatusId;
    
    /**
     * Holds description for Race.
     */
    private String description;
    
    /**
     * default constructor.
     */
    public EmploymentStatus() {
    
    }
    
    /**
     * Get the employmentStatusId for the EmploymentStatus.
     * 
     * @return the employmentStatusId
     */
    public int getEmploymentStatusId() {
    
        return employmentStatusId;
    }
    
    /**
     * Set the employmentStatusId for the EmploymentStatus.
     * 
     * @param employmentStatusIdRef the employmentStatusId to set
     */
    public void setEmploymentStatusId(int employmentStatusIdRef) {
    
        this.employmentStatusId = employmentStatusIdRef;
    }
    
    /**
     * Get the description for the EmploymentStatus.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description for the EmploymentStatus.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
}
