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
 * This class represents all properties of ParentDonation domain object.
 * 
 * @author Virtusa Corporation
 */
public class ParentDonation extends BaseDomain implements Serializable {
    
    /** attribute for holding string modified date/time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** attribute for holding string parent. */
    private static final String PARENT = " parent = ";
    
    /** attribute for holding string donation. */
    private static final String DONATION = " donation = ";
    
    /** attribute for holding string parent donation id. */
    private static final String PARENT_DONATION_ID = "parent donation id = ";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 6262454976927978223L;
    
    /** Represents the id for a parent donation. */
    private int parentDonationId;
    
    /** Represents the donation of parent donation. */
    private Donation donation;
    
    /** Represents the parent of parent donation. */
    private Parent parent;
    
    /**
     * Constructs a new Donation object.
     */
    public ParentDonation() {
    
    }
    
    /**
     * Constructs a new parent donation object with the specified parent donation id.
     * 
     * @param parentDonatId - the parentDonationId.
     */
    public ParentDonation(final int parentDonatId) {
    
        this.parentDonationId = parentDonatId;
    }
    
    /**
     * Returns the id for this ParentDonation object.
     * 
     * @return the id of the ParentDonation object
     */
    public int getParentDonationId() {
    
        return parentDonationId;
    }
    
    /**
     * Sets the id for this parentDonation object with the given key.
     * 
     * @param intParentDonatId - id of the parentDonation.
     */
    public void setParentDonationId(final int intParentDonatId) {
    
        this.parentDonationId = intParentDonatId;
    }
    
    /**
     * Returns the Donation for this ParentDonation object.
     * 
     * @return - the Donation of the ParentDonation.
     */
    public Donation getDonation() {
    
        return donation;
    }
    
    /**
     * Sets the Donation for this ParentDonation object.
     * 
     * @param objDonation - the Donation of the ParentDonation.
     */
    public void setDonation(final Donation objDonation) {
    
        this.donation = objDonation;
    }
    
    /**
     * Returns the Parent for this ParentDonation object.
     * 
     * @return - the Parent of the ParentDonation.
     */
    public Parent getParent() {
    
        return parent;
    }
    
    /**
     * Sets the Parent for this ParentDonation object.
     * 
     * @param objParent - the Parent of the ParentDonation.
     */
    public void setParent(final Parent objParent) {
    
        this.parent = objParent;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return PARENT_DONATION_ID + this.parentDonationId + DONATION + this.donation + PARENT + this.parent
                + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
