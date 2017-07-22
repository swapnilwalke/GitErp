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

/**
 * This class represents all the properties of WrapperQualification.
 * 
 * @author Virtusa Corporation
 */
public class WrapperTerminateStudent extends BaseDomain implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** string constant for holding `modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** Represents the suspendStudent. */
    private SuspendStudent suspendStudent;
    
    /** Represents the studentTemporaryLeave. */
    private StudentTemporaryLeave studentTemporaryLeave;
    
    /** Represents the Past Student. */
    private PastStudent pastStudent;
    
    /**
     * Constructs a new WrapperTerminateStudent object.
     */
    public WrapperTerminateStudent() {

        super();
        suspendStudent = new SuspendStudent();
        studentTemporaryLeave = new StudentTemporaryLeave();
        pastStudent = new PastStudent();
    }
    
    /**
     * Returns the suspendStudent object for this WrapperTerminateStudent object.
     * 
     * @return the suspendStudent
     */
    public SuspendStudent getSuspendStudent() {

        return suspendStudent;
    }
    
    /**
     * Sets the suspendStudent object for this WrapperTerminateStudent object.
     * 
     * @param objSuspendStudent the suspendStudent to set
     */
    public void setSuspendStudent(SuspendStudent objSuspendStudent) {

        this.suspendStudent = objSuspendStudent;
    }
    
    /**
     * Returns the studentTemporaryLeave object for this  WrapperTerminateStudent object.
     * 
     * @return the studentTemporaryLeave
     */
    public StudentTemporaryLeave getStudentTemporaryLeave() {

        return studentTemporaryLeave;
    }
    
    /**
     * Sets the studentTemporaryLeave object for this  WrapperTerminateStudent object.
     * 
     * @param objStudentTemporaryLeave the studentTemporaryLeave to set
     */
    public void setStudentTemporaryLeave(StudentTemporaryLeave objStudentTemporaryLeave) {

        this.studentTemporaryLeave = objStudentTemporaryLeave;
    }
    
    /**
     * Returns the PastStudent object for this  WrapperTerminateStudent object.
     * 
     * @return the pastStudent
     */
    
    public PastStudent getPastStudent() {

        return pastStudent;
    }
    
    /**
     * Sets the PastStudent object for this  WrapperTerminateStudent object.
     * 
     * @param objpastStudent -past student
     */
    public void setPastStudent(PastStudent objpastStudent) {

        this.pastStudent = objpastStudent;
    }
    
    /**
     * Returns the details for the  WrapperTerminateStudent object.
     * 
     * @return - the current object details.
     */
    public String toString() {

        return getSuspendStudent().toString() + getStudentTemporaryLeave().toString() + getPastStudent().toString()
                + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
    
}
