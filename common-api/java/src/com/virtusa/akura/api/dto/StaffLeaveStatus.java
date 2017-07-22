
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
 * @author Virtusa Corporation
 *
 */

public class StaffLeaveStatus extends BaseDomain implements Serializable {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds staff leave status id for StaffLeaveStatus object. */
    private int staffLeaveStatusId;
    
    /** Holds description for StaffLeaveStatus object. */
    private String description;

    /**
     * Get staff leave status id.
     * 
     * @return staffLeaveStatusId
     */
    public int getStaffLeaveStatusId() {
    
        return staffLeaveStatusId;
    }

    /**
     * Set staff leave status id.
     * 
     * @param staffLeaveStatusIdRef - staffLeaveStatusId
     */
    public void setStaffLeaveStatusId(int staffLeaveStatusIdRef) {
    
        this.staffLeaveStatusId = staffLeaveStatusIdRef;
    }

    /**
     * Get description.
     * 
     * @return description
     */
    public String getDescription() {
    
        return description;
    }

    /**
     * Set description.
     * 
     * @param descriptionRef - description
     */
    public void setDescription(String descriptionRef) {
    
        this.description = descriptionRef;
    }
    
    /**
     * 
     */
    public StaffLeaveStatus() {
        
    }
    
}
