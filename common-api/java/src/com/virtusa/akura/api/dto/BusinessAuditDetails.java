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
 * This class represents all properties of a BusinessAuditDetails domain object.
 * 
 * @author Virtusa Corporation
 */
public class BusinessAuditDetails extends BaseDomain implements Serializable {
    
    /** Default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Represents the businessAuditDetailsId of type integer. */
    private int businessAuditDetailsId;
    
    /** Represents the businessAudit of type BusinessAudit. */
    private BusinessAudit businessAudit;
    
    /** Holds attribute of type String. */
    private String attributeName;
    
    /** Holds newValue of type String. */
    private String auditDescription;
    
    /**
     * Getter method for businessAuditDetailsId.
     * 
     * @return integer businessAuditDetailsId.
     */
    public int getBusinessAuditDetailsId() {
    
        return businessAuditDetailsId;
    }
    
    /**
     * Setter method for businessAuditDetailsId.
     * 
     * @param businessAuditDetailsIdValue the businessAuditDetailsId to set.
     */
    public void setBusinessAuditDetailsId(int businessAuditDetailsIdValue) {
    
        this.businessAuditDetailsId = businessAuditDetailsIdValue;
    }
    
    /**
     * Getter method for businessAudit object.
     * 
     * @return BusinessAudit businessAudit.
     */
    public BusinessAudit getBusinessAudit() {
    
        return businessAudit;
    }
    
    /**
     * Setter method for businessAudit object.
     * 
     * @param businessAuditValue the businessAudit to set.
     */
    public void setBusinessAudit(BusinessAudit businessAuditValue) {
    
        this.businessAudit = businessAuditValue;
    }
    
    /**
     * Getter method for attributeName.
     * 
     * @return String the attributeName.
     */
    public String getAttributeName() {
    
        return attributeName;
    }
    
    /**
     * Setter method for attributeName.
     * 
     * @param attributeNameValue the attributeName to set.
     */
    public void setAttributeName(String attributeNameValue) {
    
        this.attributeName = attributeNameValue;
    }
    
    /**
     * Getter method for auditDescription.
     * 
     * @return String the auditDescription.
     */
    public String getAuditDescription() {
    
        return auditDescription;
    }
    
    /**
     * Setter method for auditDescription.
     * 
     * @param auditDescriptionRef the auditDescription to set.
     */
    public void setAuditDescription(String auditDescriptionRef) {
    
        this.auditDescription = auditDescriptionRef;
    }
    
}
