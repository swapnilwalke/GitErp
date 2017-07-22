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
 * This class represents all properties of a BusinessAudit domain object.
 * 
 * @author Virtusa Corporation
 */
public class BusinessAudit extends BaseDomain implements Serializable {
    
    /** Default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Represents the businessAuditIf of type integer. */
    private int businessAuditId;
    
    /** Represents the userLogin of type integer. */
    private UserLogin userLogin;
    
    /** Holds BusinessFunction of type String. */
    private String businessFunction;
    
    /** Holds Module of type String. */
    private String module;
    
    /** Holds EventType. */
    private AuditEventType eventType;
    
    /** Represents the date of type Date. */
    private Date date;
    
    /** Represents the fromDate of type String. */
    private String fromDate;
    
    /** Represents the toDate of type String. */
    private String toDate;
    
    /**
     * Getter method for businessAuditId.
     * 
     * @return integer businessAuditId.
     */
    public int getBusinessAuditId() {
    
        return businessAuditId;
    }
    
    /**
     * Setter method for businessAuditId.
     * 
     * @param businessAuditIdValue the businessAuditId to set.
     */
    public void setBusinessAuditId(int businessAuditIdValue) {
    
        this.businessAuditId = businessAuditIdValue;
    }
    
    /**
     * Getter method for BusinessFunction.
     * 
     * @return String the BusinessFunction.
     */
    public String getBusinessFunction() {
    
        return businessFunction;
    }
    
    /**
     * Setter method for BusinessFunction.
     * 
     * @param businessFunctionValue the BusinessFunction to set.
     */
    public void setBusinessFunction(String businessFunctionValue) {
    
        this.businessFunction = businessFunctionValue;
    }
    
    /**
     * Getter method for EventType.
     * 
     * @return String the EventType.
     */
    public AuditEventType getEventType() {
    
        return eventType;
    }
    
    /**
     * Setter method for EventType.
     * 
     * @param eventTypeValue the EventType to set.
     */
    public void setEventType(AuditEventType eventTypeValue) {
    
        this.eventType = eventTypeValue;
    }
    
    /**
     * return date of the SpecialEvents.
     * 
     * @return the date
     */
    public Date getDate() {
    
        if (date != null) {
            return (Date) date.clone();
        }
        
        return null;
    }
    
    /**
     * Setter method for participantCategoryId.
     * 
     * @param dateRef the date to set
     */
    public void setDate(Date dateRef) {
    
        if (dateRef != null) {
            
            this.date = (Date) dateRef.clone();
        }
    }
    
    /**
     * Getter method for userLogin.
     * 
     * @return userLogin object.
     */
    public UserLogin getUserLogin() {
    
        return userLogin;
    }
    
    /**
     * Setter method for userLogin.
     * 
     * @param userLoginValue the userLogin to set.
     */
    public void setUserLogin(UserLogin userLoginValue) {
    
        this.userLogin = userLoginValue;
    }
    
    /**
     * Getter method for Module.
     * 
     * @return String the ModuleId.
     */
    public String getModule() {
    
        return module;
    }
    
    /**
     * Setter method for Module.
     * 
     * @param moduleValue the Module to set.
     */
    public void setModule(String moduleValue) {
    
        this.module = moduleValue;
    }
    
    /**
     * Getter method for FromDate.
     * 
     * @return Date type fromDate.
     */
    public String getFromDate() {
    
        return fromDate;
    }
    
    /**
     * Setter method for FromDate.
     * 
     * @param fromDateRef the FromDate to set.
     */
    public void setFromDate(String fromDateRef) {
    
        this.fromDate = fromDateRef;
    }
    
    /**
     * Getter method for ToDate.
     * 
     * @return Date type ToDate.
     */
    public String getToDate() {
    
        return toDate;
    }
    
    /**
     * Setter method for ToDate.
     * 
     * @param toDateRef the ToDate to set.
     */
    public void setToDate(String toDateRef) {
    
        this.toDate = toDateRef;
    }
    
}
