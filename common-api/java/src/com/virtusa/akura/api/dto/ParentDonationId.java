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

/**
 * DTO class to hold an object ParentDonationId.
 * 
 * @author Virtusa Corporation
 */
public class ParentDonationId implements java.io.Serializable {
    
    /** attribute for holding string. */
    private static final String DONATION_ID = ", donationId=";
    
    /** attribute for holding string. */
    private static final String PARENT_ID = "parentId=";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** Holds the parent id of the parent's donation. */
    private int parentId;
    
    /** Holds the donation id of the parent's donation. */
    private int donationId;
    
    /**
     * Default constructor.
     */
    public ParentDonationId() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intParentId type int
     * @param intDonationId type int
     */
    public ParentDonationId(int intParentId, int intDonationId) {
    
        this.parentId = intParentId;
        this.donationId = intDonationId;
    }
    
    /**
     * @return the parentId
     */
    public int getParentId() {
    
        return parentId;
    }
    
    /**
     * @param intParentId the parentId to set
     */
    public void setParentId(int intParentId) {
    
        this.parentId = intParentId;
    }
    
    /**
     * @return the donationId
     */
    public int getDonationId() {
    
        return donationId;
    }
    
    /**
     * @param intDonationId the donationId to set
     */
    public void setDonationId(int intDonationId) {
    
        this.donationId = intDonationId;
    }
    
    /**
     * Overwritten equals method.
     * 
     * @param other type Object
     * @return true or false
     */
    public boolean equals(Object other) {
    
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof ParentDonationId)) {
            return false;
        }
        ParentDonationId castOther = (ParentDonationId) other;
        
        return (this.getParentId() == castOther.getParentId()) && (this.getDonationId() == castOther.getDonationId());
    }
    
    /**
     * Overwritten hashCode method.
     * 
     * @return result type integer
     */
    public int hashCode() {
    
        return 0;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return PARENT_ID + parentId + DONATION_ID + donationId;
    }
    
}
