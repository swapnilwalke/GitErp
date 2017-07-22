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
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents all properties of DonationType domain object.
 * 
 * @author Virtusa Corporation
 */
public class DonationType extends BaseDomain implements Serializable {
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** String attribute for to DESCRIPTION_VALUE. */
    private static final String DESCRIPTION_VALUE = " description = ";
    
    /** String attribute for to DONATION_TYPE_ID. */
    private static final String DONATION_TYPE_ID = "donation type id = ";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 2105419204858368392L;
    
    /** Represents the id for a donation type. */
    private int donationTypeId;
    
    /** Represents the description of donation type. */
    private String description;
    
    /** Represents a set of Donations for a Donation Type. */
    private Set<Donation> donations = new HashSet<Donation>(0);
    
    /**
     * Constructs a new Donation Type object.
     */
    public DonationType() {
    
    }
    
    /**
     * Constructs a new donation type object with the specified donation type id.
     * 
     * @param donatTypeId - the donationTypeId
     */
    public DonationType(final int donatTypeId) {
    
        this.donationTypeId = donatTypeId;
    }
    
    /**
     * Returns the id for this DonationType object.
     * 
     * @return the id of the DonationType object
     */
    public final int getDonationTypeId() {
    
        return donationTypeId;
    }
    
    /**
     * Sets the id for this DonationType object with the given key.
     * 
     * @param intDonationTypeId - id of the donationType.
     */
    public final void setDonationTypeId(final int intDonationTypeId) {
    
        this.donationTypeId = intDonationTypeId;
    }
    
    /**
     * Returns the description for this DonationType object.
     * 
     * @return - the description of this DonationType object.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description for this DonationType object with the given information.
     * 
     * @param strDescription - the description of the DonationType object.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns a Set of Donations for this DonationType object.
     * 
     * @return the donations of the Donation Type.
     */
    public Set<Donation> getDonations() {
    
        return donations;
    }
    
    /**
     * Sets a Set of Donations of the DonationType.
     * 
     * @param collDonations - The Donations of the DonationType.
     */
    public void setDonations(final Set<Donation> collDonations) {
    
        this.donations = collDonations;
    }
    
    /**
     * Returns the details for the DonationType object.
     * 
     * @return - the DonationType object details.
     */
    public String toString() {
    
        return DONATION_TYPE_ID + this.donationTypeId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
    
}
