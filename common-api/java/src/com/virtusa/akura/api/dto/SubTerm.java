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

import java.util.Date;

/**
 * This class is to associates all the properties of SubTerm domain object.
 * 
 * @author Virtusa Corporation
 */
public class SubTerm extends BaseDomain {
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";
    
    /** String attribute for to DESCRIPTION_VALUE. */
    private static final String DESCRIPTION_VALUE = " description = ";
    
    /** String attribute for to SUB_TERM_ID. */
    private static final String SUB_TERM_ID = " sub term id = ";
    
    /**
     * Holds the serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds unique id for SubTerm object.
     */
    private int subTermId;
    
    /**
     * Represents term id of the subterm.
     */
    private int termId;
    
    /**
     * Represents description of the subterm.
     */
    private String description;
    
    /**
     * Holds stating date of sub term.
     */
    private Date fromMonth;
    
    /**
     * Holds end date of a sub term.
     */
    private Date toMonth;
    
    /**
     * Default constructor.
     */
    public SubTerm() {
    
    }
    
    /**
     * Overloaded constructor with parameter subTermId.
     * 
     * @param intSubTermId type int
     */
    public SubTerm(int intSubTermId) {
    
        this.subTermId = intSubTermId;
    }
    
    /**
     * Get the sub term id.
     * 
     * @return the subTermId
     */
    public int getSubTermId() {
    
        return subTermId;
    }
    
    /**
     * Set the sub term id.
     * 
     * @param intSubTermId the subTermId to set
     */
    public void setSubTermId(int intSubTermId) {
    
        this.subTermId = intSubTermId;
    }
    
    /**
     * Get the term id of the sub term.
     * 
     * @return the termId
     */
    public int getTermId() {
    
        return termId;
    }
    
    /**
     * Set the term id of the sub term.
     * 
     * @param intTerm the termId to set
     */
    public void setTermId(int intTerm) {
    
        this.termId = intTerm;
    }
    
    /**
     * Get the start date for the sub term.
     * 
     * @return the fromMonth
     */
    public Date getFromMonth() {
    
        if (fromMonth != null) {
            return (Date) fromMonth.clone();
        }
        return null;
    }
    
    /**
     * Set the start date for the sub term.
     * 
     * @param dtFromMonth the fromMonth to set
     */
    public void setFromMonth(Date dtFromMonth) {
    
        if (dtFromMonth != null) {
            this.fromMonth = (Date) dtFromMonth.clone();
        }
    }
    
    /**
     * Get the end date for the sub term.
     * 
     * @return the toMonth
     */
    public Date getToMonth() {
    
        if (toMonth != null) {
            return (Date) toMonth.clone();
        }
        return null;
    }
    
    /**
     * Set the end date for the sub term.
     * 
     * @param dtToMonth the toMonth to set
     */
    public void setToMonth(Date dtToMonth) {
    
        if (dtToMonth != null) {
            this.toMonth = (Date) dtToMonth.clone();
        }
    }
    
    /**
     * Get the description for the sub term.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description for the sub term.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return SUB_TERM_ID + this.subTermId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
