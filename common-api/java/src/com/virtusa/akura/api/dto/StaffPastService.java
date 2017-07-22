/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
 * This class is to associates all the properties of Staff Past Service domain object.
 * 
 * @author Virtusa Corporation
 */
public class StaffPastService extends BaseDomain {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds staff Past Service Id for StaffPastService object. */
    private int staffPastServiceId;
    
    /** Holds the staff for StaffPastService object. */
    private Staff staff;
    
    /** Holds the date of join for StaffPastService object. */
    private Date dateOfJoin;
    
    /** Holds the date of departure for StaffPastService object. */
    private Date dateOfDepature;
    
    /** Holds the reason for StaffPastService object. */
    private String reason;
    
    /** Flag to check if clearance complete. */
    private boolean completeClearance;
    
    /** Holds the registration number of StaffPastService object. */
    private String registrationNo;
    
    /** Default constructor for StaffPastService. */
    public StaffPastService() {

        super();
    }
    
    /**
     * Get the staff past service Id.
     * 
     * @return staffPastServiceId.
     */
    public int getStaffPastServiceId() {

        return staffPastServiceId;
    }
    
    /**
     * Set the staff past service Id.
     * 
     * @param staffPastServiceIdVal to be set
     */
    public void setStaffPastServiceId(int staffPastServiceIdVal) {

        this.staffPastServiceId = staffPastServiceIdVal;
    }
    
    /**
     * Get the staff.
     * 
     * @return staffId.
     */
    public Staff getStaff() {

        return staff;
    }
    
    /**
     * Set the staff.
     * 
     * @param staffVal to be set.
     */
    public void setStaff(Staff staffVal) {

        this.staff = staffVal;
    }
    
    /**
     * Get the date of join.
     * 
     * @return the join date.
     */
    public Date getDateOfJoin() {

        return dateOfJoin;
    }
    
    /**
     * Set the date of join.
     * 
     * @param dateOfJoinVal to be set.
     */
    public void setDateOfJoin(Date dateOfJoinVal) {

        this.dateOfJoin = dateOfJoinVal;
    }
    
    /**
     * Get the date of departure.
     * 
     * @return departure date.
     */
    public Date getDateOfDepature() {

        if (dateOfDepature != null) {
            
            return (Date) dateOfDepature.clone();
        }
        
        return null;
    }
    
    /**
     * Set the date of departure.
     * 
     * @param dateOfDepatureVal to be set.
     */
    public void setDateOfDepature(Date dateOfDepatureVal) {

        if (dateOfDepatureVal != null) {
            
            this.dateOfDepature = (Date) dateOfDepatureVal.clone();
        }
    }
    
    /**
     * Get the reason.
     * 
     * @return reason of departure.
     */
    public String getReason() {

        return reason;
    }
    
    /**
     * Set the reason.
     * 
     * @param reasonVal to be set.
     */
    public void setReason(String reasonVal) {

        this.reason = reasonVal;
    }
    
    /**
     * Returns the boolean value if complete clearance.
     * 
     * @return true, if the clearance is complete or false otherwise.
     */
    public boolean getCompleteClearance() {

        return completeClearance;
    }
    
    /**
     * Sets the boolean value for complete clearance.
     * 
     * @param completeClearanceRef - boolean value for the complete clearance.
     */
    public void setCompleteClearance(boolean completeClearanceRef) {

        this.completeClearance = completeClearanceRef;
    }
    
    /**
     * Returns the registration number of the staff member.
     * 
     * @return registration number of the staff member.
     */
    public String getRegistrationNo() {

        return registrationNo;
    }
    
    /**
     * Set the staff members registration number.
     * 
     * @param registrationNoVal of the staff member to be set.
     */
    public void setRegistrationNo(String registrationNoVal) {

        this.registrationNo = registrationNoVal;
    }
    
    /**
     * The StaffPastService object description.
     * 
     * @return the string representation of StaffPastService object.
     */
    @Override
    public String toString() {

        return "StaffPastService [staffPastServiceId=" + staffPastServiceId + ", staff=" + staff + ", dateOfJoin="
                + dateOfJoin + ", dateOfDepature=" + dateOfDepature + ", reason=" + reason + "]";
    }
    
}
