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

import java.io.Serializable;
import java.util.Date;

/**
 * This class is to associates all the properties of StaffLeave domain object.
 * 
 * @author Virtusa Corporation
 */

public class StaffLeave extends BaseDomain implements Serializable{
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds staff leave id for StaffLeave object. */
    private int staffLeaveId;
    
    /** Holds staff id for StaffLeave object. */
    private int staffId;
    
    /** Holds reason for StaffLeave object. */
    private String reason;
    
    /** Holds fromDate for StaffLeave object. */
    private Date fromDate;
    
    /** Holds toDate for StaffLeave object. */
    private Date toDate;
    
    /** Holds number of days for StaffLeave object. */
    private Float noOfDays;
    
    /** Holds staffLeaveTypeId for StaffLeave object. */
    private Integer staffLeaveTypeId;
   
    /** Holds staffLeaveStatusId for StaffLeave object. */
    private Integer staffLeaveStatusId;
    
    /** Holds staffLeaveTypeId for StaffLeave object. */
    private Date appliedDate;
    
    /** Holds staffLeaveTypeId for StaffLeave object. */
    private Date approvedRejectedDate;
    
    /** Holds staffLeaveTypeId for StaffLeave object. */
    private UserLogin userLogin;
    
    /** Holds dateType for StaffLeave object. */
    private String dateType;
    
    /** Holds comment for StaffLeave object. */
    private String comment;
    
    /**
     * Get comment.
     * 
     * @return comment
     */
    public String getComment() {
    
        return comment;
    }

    /**
     * Set comment.
     * 
     * @param commentRef comment
     */
    public void setComment(String commentRef) {
    
        this.comment = commentRef;
    }

    /**
     * Get dateType.
     * 
     * @return dateType
     */
    public String getDateType() {
    
        return dateType;
    }

    /**
     * Set dateType.
     * 
     * @param dateTypeRef dateType
     */
    public void setDateType(String dateTypeRef) {
    
        this.dateType = dateTypeRef;
    }

    /**
     * Get staffLeaveStatusId.
     * 
     * @return staffLeaveStatusId
     */
    public Integer getStaffLeaveStatusId() {
    
        return staffLeaveStatusId;
    }

    /**
     * Set staffLeaveStatusId.
     * 
     * @param staffLeaveStatusIdRef - staffLeaveStatusId
     */
    public void setStaffLeaveStatusId(Integer staffLeaveStatusIdRef) {
    
        this.staffLeaveStatusId = staffLeaveStatusIdRef;
    }

    /**
     * Get appliedDate.
     * 
     * @return appliedDate
     */
    public Date getAppliedDate() {
    
        return appliedDate;
    }

    /**
     * Set appliedDate.
     * 
     * @param appliedDateRef - appliedDate
     */
    public void setAppliedDate(Date appliedDateRef) {
    
        this.appliedDate = appliedDateRef;
    }

    /**
     * Get approvedRejectedDate.
     * 
     * @return approvedRejectedDate
     */
    public Date getApprovedRejectedDate() {
    
        return approvedRejectedDate;
    }

    /**
     * Set approvedrejectedDate.
     * 
     * @param approvedRejectedDateRef - approvedRejectedDate
     */
    public void setApprovedRejectedDate(Date approvedRejectedDateRef) {
    
        this.approvedRejectedDate = approvedRejectedDateRef;
    }

    /**
     * Get userLogin object.
     * 
     * @return userLogin
     */
    public UserLogin getUserLogin() {
    
        return userLogin;
    }

    /**
     * Set userLogin object.
     * 
     * @param userLoginRef - userLogin
     */
    public void setUserLogin(UserLogin userLoginRef) {
    
        this.userLogin = userLoginRef;
    }

    /**
     * Get StudentLeaveTypeId.
     * 
     * @return staffLeaveTypeId
     */
    public Integer getStaffLeaveTypeId() {
    
        return staffLeaveTypeId;
    }

    /**
     * Set Staff Leave Type.
     * 
     * @param staffLeaveTypeIdRef staffLeaveTypeId
     */
    public void setStaffLeaveTypeId(Integer staffLeaveTypeIdRef) {
    
        this.staffLeaveTypeId = staffLeaveTypeIdRef;
    }
    

    /**
     * Constructor.
     */
    public StaffLeave() {
    
    }
    
    /**
     * holds the current status of the staff, whether he departures or not.
     */
    private boolean deleted;
    
    /**
     * Get the staffLeaveId for the StaffLeave.
     * 
     * @return - staffLeaveId
     */
    public int getStaffLeaveId() {

        return staffLeaveId;
    }
    
    /**
     * Set the staffLeaveId for the StaffLeave.
     * 
     * @param staffLeaveIdValue - staffLeaveIdValue
     */
    public void setStaffLeaveId(int staffLeaveIdValue) {

        this.staffLeaveId = staffLeaveIdValue;
    }
    
    /**
     * Get the staffId for the StaffLeave.
     * 
     * @return - staffId
     */
    public int getStaffId() {

        return staffId;
    }
    
    /**
     * Set the satffId for the StaffLeave.
     * 
     * @param staffIdValue - staffId
     */
    public void setStaffId(int staffIdValue) {

        this.staffId = staffIdValue;
    }
    
    /**
     * Get the reason for the StaffLeave.
     * 
     * @return - reason
     */
    public String getReason() {

        return reason;
    }
    
    /**
     * Set the reason for the StaffLeave.
     * 
     * @param reasonValue - reason
     */
    public void setReason(String reasonValue) {

        this.reason = reasonValue;
    }
    
    /**
     * Get the fromDate for the StaffLeave.
     * 
     * @return - fromDate
     */
    public Date getFromDate() {

        if (fromDate != null) {
            return (Date) fromDate.clone();
        }
        
        return null;
    }
    
    /**
     * Set the fromDate for the StaffLeave.
     * 
     * @param fromDateValue - fromDate
     */
    public void setFromDate(Date fromDateValue) {

        if (fromDateValue != null) {
            
            this.fromDate = (Date) fromDateValue.clone();
        }
    }
    
    /**
     * Get the toDate for the StaffLeave.
     * 
     * @return - toDate
     */
    public Date getToDate() {

        if (toDate != null) {
            return (Date) toDate.clone();
        }
        
        return null;
    }
    
    /**
     * Set the toDate for the StaffLeave.
     * 
     * @param toDateValue - toDate
     */
    public void setToDate(Date toDateValue) {

        if (toDateValue != null) {
            
            this.toDate = (Date) toDateValue.clone();
        }
    }
    
    /**
     * Get the number of days for the StaffLeave.
     * 
     * @return - noOfDays
     */
    public Float getNoOfDays() {

        return noOfDays;
    }
    
    /**
     * Set the number of days for the StaffLeave.
     * 
     * @param noOfDaysValue - noOfValue
     */
    public void setNoOfDays(Float noOfDaysValue) {

        this.noOfDays = noOfDaysValue;
    }
    
    /**
     * return the deleted status of the staff.
     * 
     * @return the deleted status .
     */
    public boolean getDeleted() {
    
        return deleted;
    }
    
    /**
     * set the the deleted status of the staff.
     * 
     * @param deletedVal the deleted status to set
     */
    public void setDeleted(boolean deletedVal) {
    
        this.deleted = deletedVal;
    }
}
