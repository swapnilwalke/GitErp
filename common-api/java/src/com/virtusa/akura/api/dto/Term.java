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
import java.util.HashSet;
import java.util.Set;

/**
 * This class is to associates all the properties of Term domain object.
 * 
 * @author Virtusa Corporation
 */
public class Term extends BaseDomain {
    
    /** String attribute for to END_DATE. */
    private static final String END_DATE = " End Date = ";
    
    /** String attribute for to START_DATE. */
    private static final String START_DATE = " Start Date = ";
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";
    
    /** String attribute for to DESCRIPTION_DATE_TIME. */
    private static final String DESCRIPTION_VALUE = " description = ";
    
    /** String attribute for to TERM_ID. */
    private static final String TERM_ID = " term id = ";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds unique id for Term object.
     */
    private int termId;
    
    /**
     * Represents description of term object.
     */
    private String description;
    
    /**
     * Represents starting date of the Term.
     */
    private Date fromMonth;
    
    /**
     * Represents ending date of the Term.
     */
    private Date toMonth;
    
    /**
     * Holds subTerms of the term.
     */
    private Set<SubTerm> subTerms = new HashSet<SubTerm>();
    /**
     * Default constructor.
     */
    public Term() {
    
    }
    
    /**
     * Overloaded constructor with parameter termId.
     * 
     * @param intTermId type int
     */
    public Term(int intTermId) {
    
        this.termId = intTermId;
    }
    
    /**
     * Get the term id of Term object.
     * 
     * @return the termId
     */
    public int getTermId() {
    
        return termId;
    }
    
    /**
     * Set the term id to Term object.
     * 
     * @param intTermId the termId to set
     */
    public void setTermId(int intTermId) {
    
        this.termId = intTermId;
    }
    
    /**
     * Get the start date of the Term.
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
     * Set the start date of the term.
     * 
     * @param dtFromMonth the fromMonth to set
     */
    public void setFromMonth(Date dtFromMonth) {
    
        if (dtFromMonth != null) {
            this.fromMonth = (Date) dtFromMonth.clone();
        }
    }
    
    /**
     * Get the end date of the term.
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
     * Set the end date of the term.
     * 
     * @param dtToMonth the toMonth to set
     */
    public void setToMonth(Date dtToMonth) {
    
        if (dtToMonth != null) {
            this.toMonth = (Date) dtToMonth.clone();
        }
    }
    
    /**
     * Get the description of the term.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description of the term.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Get the sub terms of the term.
     * 
     * @return the subTerms
     */
    public Set<SubTerm> getSubTerms() {
    
        return subTerms;
    }
    
    /**
     * Set the sub terms to the term.
     * 
     * @param colSubTerms the subTerms to set
     */
    public void setSubTerms(Set<SubTerm> colSubTerms) {
    
        this.subTerms = colSubTerms;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return TERM_ID + this.termId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime() + START_DATE + this.fromMonth + END_DATE + this.toMonth;
    }
}
