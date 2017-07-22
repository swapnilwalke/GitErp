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
 * This class represents all the properties of a Country domain object.
 * 
 * @author Virtusa Corporation
 */
public class AuditEventType extends BaseDomain implements Serializable {
    
    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a AuditEventType.
     */
    private int auditEventTypeId;
    
    /**
     * Represents description.
     */
    private String description;
    
    /**
     * Constructor for AuditEventType.
     */
    public AuditEventType() {

    }

    /**
     * Get the auditEventTypeId.
     * 
     * @return auditEventTypeId.
     */
    public int getAuditEventTypeId() {
    
        return auditEventTypeId;
    }

    /**
     * Set the auditEventTypeId.
     * 
     * @param auditEventTypeIdRef the auditEventTypeId to be set.
     */
    public void setAuditEventTypeId(int auditEventTypeIdRef) {
    
        this.auditEventTypeId = auditEventTypeIdRef;
    }

    /**
     * Get the description.
     * 
     * @return description.
     */
    public String getDescription() {
    
        return description;
    }

    /**
     * Set the description.
     * 
     * @param descriptionRef the description to be set.
     */
    public void setDescription(String descriptionRef) {
    
        this.description = descriptionRef;
    }
        
}
