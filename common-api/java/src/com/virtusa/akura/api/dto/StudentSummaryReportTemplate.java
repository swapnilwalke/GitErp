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
 * The Class StudentSummaryTemplate.
 * 
 * @author Virtusa Corporation
 */
public class StudentSummaryReportTemplate {
    
    /** represents the studentAdmissionNo of the student. */
    private String studentAdmissionNo;
    
    /**
     * @return the studentAdmissionNo
     */
    public String getStudentAdmissionNo() {
    
        return studentAdmissionNo;
    }
    
    /**
     * @param strStudentAdmissionNo the studentAdmissionNo to set
     */
    public void setStudentAdmissionNo(final String strStudentAdmissionNo) {
    
        this.studentAdmissionNo = strStudentAdmissionNo;
    }
    
    /**
     * Returns a String that represents this Object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return "student admission number " + this.studentAdmissionNo;
    }
}
