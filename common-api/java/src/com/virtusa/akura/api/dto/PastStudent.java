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
public class PastStudent extends BaseDomain {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds staff Past Service Id for PastStudent object. */
    private int pastStudentId;
    
    /** Holds the staff for PastStudent object. */
    private Student student;
    
    /** Holds the date of departure for PastStudent object. */
    private Date dateOfDeparture;
    
    /** Holds the date of admission for PastStudent object. */
    private Date firstSchoolDay;
    
    /** Holds the reason for PastStudent object. */
    private String reason;
    
    /** Flag to check if clearance complete. */
    private boolean completeClearance;
    
    /** Default constructor for PastStudent. */
    public PastStudent() {

        super();
    }
    
    /**
     * Get the staff past service Id.
     * 
     * @return staffPastServiceId.
     */
    public int getPastStudentId() {

        return pastStudentId;
    }
    
    /**
     * Set the staff past service Id.
     * 
     * @param pastStudentIdVal to be set
     */
    public void setPastStudentId(int pastStudentIdVal) {

        this.pastStudentId = pastStudentIdVal;
    }
    
    /**
     * Get the student.
     * 
     * @return studentId.
     */
    public Student getStudent() {

        return student;
    }
    
    /**
     * Set the student.
     * 
     * @param studentVal to be set.
     */
    public void setStudent(Student studentVal) {

        this.student = studentVal;
    }
    
    /**
     * Get the date of departure.
     * 
     * @return departure date.
     */
    public Date getDateOfDeparture() {

        return dateOfDeparture;
        
    }
    
    /**
     * Set the date of departure.
     * 
     * @param dateOfDepartureVal to be set.
     */
    public void setDateOfDeparture(Date dateOfDepartureVal) {

        this.dateOfDeparture = dateOfDepartureVal;
        
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
     * Get the firstSchoolDay.
     * 
     * @return firstSchoolDay.
     */
    
    public Date getFirstSchoolDay() {

        return firstSchoolDay;
    }
    
    /**
     * Set the firstSchoolDay.
     * 
     * @param firstSchoolDayVal to be set.
     */
    
    public void setFirstSchoolDay(Date firstSchoolDayVal) {

        this.firstSchoolDay = firstSchoolDayVal;
    }
    
    /**
     * The PastStudent object description.
     * 
     * @return the string representation of PastStudent object.
     */
    @Override
    public String toString() {

        return "PastStudent [pastStudentId=" + pastStudentId + ", student=" + student + ", dateOfDeparture="
                + dateOfDeparture + ",firstSchoolDay=" + firstSchoolDay + " reason=" + reason + "]";
    }
    
}
