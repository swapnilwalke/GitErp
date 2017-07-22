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
 * This class represents all properties of Donation domain object.
 * 
 * @author Virtusa Corporation
 */
public class Donation extends BaseDomain implements Serializable {
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** String attribute for to DONATION_TYPE. */
    private static final String DONATION_TYPE = " donation type = ";
    
    /** String attribute for to PURPOSE. */
    private static final String PURPOSE_VALUE = " purpose = ";
    
    /** String attribute for to DONATION_ID. */
    private static final String DONATION_ID = "donation id = ";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 8291099135026141617L;
    
    /** Represents the id of a donation. */
    private int donationId;
    
    /** Represents the purpose of the donation. */
    private String purpose;
    
    /** Represents the amount of the donation. */
    private String amount;
    
    /** Represents the date of the donation. */
    private Date date;
    
    /** Represents the donationType for a Donation. */
    private DonationType donationType;

    /**
     * Constructs a new Donation object.
     */
    public Donation() {
    
    }
    
    /**
     * Constructs a new donation object with the specified donation id.
     * 
     * @param donatId - the donationId.
     */
    public Donation(final int donatId) {
    
        this.donationId = donatId;
    }
    
    /**
     * Returns the id for this Donation object.
     * 
     * @return the id of the Donation object
     */
    public final int getDonationId() {
    
        return donationId;
    }
    
    /**
     * Sets the id for this Donation object with the given key.
     * 
     * @param intDonationId - id of the donation.
     */
    public final void setDonationId(final int intDonationId) {
    
        this.donationId = intDonationId;
    }
    
    /**
     * Returns the purpose for this Donation object.
     * 
     * @return - the purpose of this Donation object.
     */
    public final String getPurpose() {
    
        return purpose;
    }
    
    /**
     * Sets the purpose for this Donation object with the given information.
     * 
     * @param strPurpose - the purpose of the Donation object.
     */
    public final void setPurpose(final String strPurpose) {
    
        this.purpose = strPurpose;
    }
    
    /**
     * Returns the amount for this Donation object.
     * 
     * @return - the amount of this Donation object.
     */
    public final String getAmount() {
    
        return amount;
    }
    
    /**
     * Set the amount for this Donation object with the given value.
     * 
     * @param floatAmount - the amount of this Donation object.
     */
    public final void setAmount(final String floatAmount) {
    
        this.amount = floatAmount;
    }
    
    /**
     * Returns the date for this Donation object.
     * 
     * @return - the date of the Donation.
     */
    public final Date getDate() {
    
        if (date != null) {
            
            return (Date) date.clone();
        }
        
        return null;
    }
    
    /**
     * Sets the date of the Donation.
     * 
     * @param dateDonation - date of the Donation.
     */
    public final void setDate(final Date dateDonation) {
    
        if (dateDonation != null) {
            
            this.date = (Date) dateDonation.clone();
        }
    }
    
    /**
     * Returns the Donation Type for this Donation object.
     * 
     * @return - the Donation Type of the Donation.
     */
    public final DonationType getDonationType() {
    
        return donationType;
    }
    
    /**
     * Sets the Donation Type for this Donation object.
     * 
     * @param objDonationType - the DonationType of the Donation.
     */
    public final void setDonationType(final DonationType objDonationType) {
    
        this.donationType = objDonationType;
    }

    /**
     * Returns the details for the Donation object.
     * 
     * @return - the Donation object details.
     */
    public String toString() {
    
        return DONATION_ID + this.donationId + PURPOSE_VALUE + this.purpose + DONATION_TYPE
                + this.donationType.getDonationTypeId() + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
    
}
