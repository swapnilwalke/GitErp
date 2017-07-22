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

import java.util.List;

/**
 * The Class use for holding properties relate to generate student 
 * marks sheet report.
 * 
 * @author Virtusa Corporation
 */
public class StudentMarksReportData {
    /** Holds admission no. */
    private String admissionNo;
    
    /** Holds name. */
    private String name;
    
    /** Holds list of marks to display in report. */
    private List<String> marks;

    /**
     * Getter method for admission no.
     * 
     * @return admission no
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }

    /**
     * Setter method for admission no.
     * 
     * @param vAdmissionNo admission no
     */
    public void setAdmissionNo(String vAdmissionNo) {
    
        this.admissionNo = vAdmissionNo;
    }

    /**
     * Getter method for name.
     * 
     * @return name
     */
    public String getName() {
    
        return name;
    }

    /**
     * Setter method for name.
     * 
     * @param vName name
     */
    public void setName(String vName) {
    
        this.name = vName;
    }

    /**
     * Getter method for marks. 
     * 
     * @return list of marks
     */
    public List<String> getMarks() {
    
        return marks;
    }
    
    /**
     * Setter method for marks.
     * 
     * @param vMarks list of marks
     */
    public void setMarks(List<String> vMarks) {
    
        this.marks = vMarks;
    }    
}
