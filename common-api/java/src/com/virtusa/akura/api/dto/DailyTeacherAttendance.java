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
 * This class represents all the properties of the DailyTeacherAttendance domain object.
 * 
 * @author Virtusa Corporation
 */
public class DailyTeacherAttendance extends BaseDomain implements Serializable {
    
    /** auto-serial no. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a DailyTeacherAttendance.
     */
    private int dailyTeacherAttendanceId;
    
    /** Holds the studentId. */
    private Integer staffId;
    
    /** Holds the attendance date . */
    private Date date;
    
    /** Holds the time in. */
    private String timeIn;
    
    /** Holds the time out. */
    private String timeOut;
    
    /**
     * return dailyTeacherAttendanceId of the daily teacher attendance.
     * 
     * @return the dailyTeacherAttendanceId
     */
    public int getDailyTeacherAttendanceId() {

        return dailyTeacherAttendanceId;
    }
    
    /**
     * Setter method for dailyTeacherAttendanceId.
     * 
     * @param dailyTeacherAttendanceIdRef the dailyTeacherAttendanceId to set
     */
    public void setDailyTeacherAttendanceId(int dailyTeacherAttendanceIdRef) {

        this.dailyTeacherAttendanceId = dailyTeacherAttendanceIdRef;
    }
    
    /**
     * return staffId of the daily teacher attendance.
     * 
     * @return the staffId
     */
    
    public Integer getStaffId() {

        return staffId;
    }
    
    /**
     * Setter method for staffId.
     * 
     * @param staffIdRef the staffId to set
     */
    public void setStaffId(Integer staffIdRef) {

        this.staffId = staffIdRef;
    }
    
    /**
     * return date of the daily student attendance.
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
     * Setter method for date.
     * 
     * @param dateRef the student to set
     */
    public void setDate(Date dateRef) {

        if (dateRef != null) {
            
            this.date = (Date) dateRef.clone();
        }
    }
    
    /**
     * return timeIn of the daily student attendance.
     * 
     * @return the timeIn
     */
    public String getTimeIn() {

        return timeIn;
    }
    
    /**
     * Setter method for time in.
     * 
     * @param timeInRef the time in to set
     */
    public void setTimeIn(String timeInRef) {

        this.timeIn = timeInRef;
    }
    
    /**
     * return timeOut of the daily student attendance.
     * 
     * @return the timeOut
     */
    public String getTimeOut() {

        return timeOut;
    }
    
    /**
     * Setter method for time out.
     * 
     * @param timeOutRef the time out to set
     */
    public void setTimeOut(String timeOutRef) {

        this.timeOut = timeOutRef;
    }
    
}
