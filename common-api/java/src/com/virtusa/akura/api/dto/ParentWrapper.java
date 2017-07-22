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
 * This class represents all the properties of WrapperParent.
 * 
 * @author Virtusa Corporation
 */
public class ParentWrapper extends BaseDomain implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 9056784329943628595L;
    
    /** string constant for holding `modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** Represents the parent for a Parent. */
    private Parent parent;
    
    /** Represents the donation for a Parent. */
    private Donation donation;
    
    /** relation with student and parent. */    
    private String relationship; 
    
    /**
     * get the relation with student and parent. 
     * @return string
     */
	public String getRelationship() {
		return relationship;
	}
	
	/**
	 * set the relation with student and parent.
	 * @param relationshipAr object to set.
	 */
	public void setRelationship(String relationshipAr) {
		this.relationship = relationshipAr;
	}

	/**
     * Constructs a new WrapperParent object.
     * 
     * @param parent
     * @param mother
     * @param donation
     */
    public ParentWrapper() {
    
        super();
        parent = new Parent();
        donation = new Donation();
    }
    
    /**
     * Returns the parent object for this WrapperParent object.
     * 
     * @return the parent
     */
    public final Parent getParent() {
    
        return parent;
    }
    
    /**
     * Sets the parent object for this WrapperParent object.
     * 
     * @param objParent the parent to set
     */
    public final void setParent(Parent objParent) {
    
        this.parent = objParent;
    }

    
    /**
     * Returns the donation object for this WrapperParent object.
     * 
     * @return the donation
     */
    public final Donation getDonation() {
    
        return donation;
    }
    
    /**
     * Sets the donation object for this WrapperParent object.
     * 
     * @param objDonation the donation to set
     */
    public final void setDonation(Donation objDonation) {
    
        this.donation = objDonation;
    }
    
    /**
     * Returns the details for the WrapperParent object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return getParent().toString() + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
    
}
