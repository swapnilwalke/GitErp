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
 * dto for data transfer between JSP to controller for Per Day Class Wise Attendance report.
 * 
 * @author Virtusa Corporation
 */

public class PerDayAttendanceTemplate {
    
    /** attribute for holding string. */
    private static final String DATE_CONSIDERED = ", dateConsidered=";
    
    /** attribute for holding string. */
    private static final String ABSENT = ", absent=";
    
    /** attribute for holding string. */
    private static final String GRADE_DESCRIPTION = ", gradeDescription=";
    
    /** attribute for holding string. */
    private static final String CLASS_DESCRIPTION = "classDescription=";
    
    /** The attribute to hold catogaryID. */
    private int catogaryID;
    
    /** The attribute to hold catogaryID. */
    private int reportType;
    
    /**
     * holds the name of the class.
     */
    private String classDescription;
    
    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;
    
    /**
     * Holds absence.
     */
    private String absent;
    
    /**
     * property dateConsidered type java.sql.Date. used to track attendance of a specific day.
     */
    private String dateConsidered;
    
    /** Student admission use in attendance report. */
    private String studentAdmissionNo;
    
    /** Student name use in attendance report. */
    private String studentName;
    
    /**
     * @return the reportType
     */
    public int getReportType() {
        
        return reportType;
    }

    /**
     * @param reportTypeRef the reportType to set
     */
    public void setReportType(int reportTypeRef) {
    
        this.reportType = reportTypeRef;
    }
    
    /**
     * @return the catogaryID
     */
    public int getCatogaryID() {
    
        return catogaryID;
    }
    
    /**
     * @param objcatogaryID the catogaryID to set
     */
    public void setCatogaryID(int objcatogaryID) {
    
        this.catogaryID = objcatogaryID;
    }
    
    /**
     * Getter method for student admission no.
     * 
     * @return studentAdmissionNo
     */
    public String getStudentAdmissionNo() {
    
        return studentAdmissionNo;
    }
    
    /**
     * Setter method for student admission no.
     * 
     * @param vStudentAdmissionNo String
     */
    public void setStudentAdmissionNo(String vStudentAdmissionNo) {
    
        this.studentAdmissionNo = vStudentAdmissionNo;
    }
    
    /**
     * Getter method for student name.
     * 
     * @return studentName.
     */
    public String getStudentName() {
    
        return studentName;
    }
    
    /**
     * Setter method for student name.
     * 
     * @param vStudentName String
     */
    public void setStudentName(String vStudentName) {
    
        this.studentName = vStudentName;
    }
    
    /**
     * @return the dateConsidered
     */
    public String getDateConsidered() {
    
        return dateConsidered;
    }
    
    /**
     * @param objDateConsidered the dateConsidered to set
     */
    public void setDateConsidered(String objDateConsidered) {
    
        this.dateConsidered = objDateConsidered;
    }
    
    /**
     * @return classDescription Returns the name of the class.
     */
    public String getClassDescription() {
    
        return classDescription;
    }
    
    /**
     * @param classDes Sets the value for the name of the class.
     */
    public void setClassDescription(final String classDes) {
    
        this.classDescription = classDes;
    }
    
    /**
     * @return gradeDescription Returns the description of the grade.
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * @param gradeDes Sets the value for the description of the grade.
     */
    public void setGradeDescription(final String gradeDes) {
    
        this.gradeDescription = gradeDes;
    }
    
    /**
     * @return Returns student absent or not.
     */
    public String getAbsent() {
    
        return absent;
    }
    
    /**
     * @param isAbsent Sets student absent or not.
     */
    public void setAbsent(final String isAbsent) {
    
        this.absent = isAbsent;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return CLASS_DESCRIPTION + classDescription + GRADE_DESCRIPTION + gradeDescription + ABSENT + absent
                + DATE_CONSIDERED + dateConsidered;
    }
    
}
