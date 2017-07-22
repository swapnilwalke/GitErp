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
 * StudentTemporaryLeave Domain Class which holds the properties of the StudentTemporaryLeave.
 * 
 * @author Virtusa Corporation
 */
public class StudentTemporaryLeave extends BaseDomain implements Serializable {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds Student Temporary Leave Id for StudentTemporaryLeave object. */
    private int studentTemporaryLeaveId;
    
    /** Holds the student for StudentTemporaryLeave object. */
    private Student student;
    
    /** Holds the toDate for StudentTemporaryLeave object. */
    private Date toDate;
    
    /** Holds the fromDate of departure for StudentTemporaryLeave object. */
    private Date fromDate;
    
    /** Holds the reason for StudentTemporaryLeave object. */
    private String reason;
    
    /** Flag to check if clearance complete. */
    private boolean completeClearance;
    
    /** Holds the activateDate for StudentTemporaryLeave object. */
    private Date activateDate;
    
    /** holds the grade class description. */
    private String classGradeDescription;
    
    /** holds the noOfDays. */
    private int noOfDays;
    
    /** holds the year. */
    private int year;
    
    /**
     * Get the year.
     * 
     * @return the year.
     */
    public int getYear() {
    
        return year;
    }
    
    /**
     * Set the year.
     * 
     * @param yearVal the year value to set.
     */
    public void setYear(int yearVal) {
    
        this.year = yearVal;
    }
    
    /** Default constructor for StudentTemporaryLeave. */
    public StudentTemporaryLeave() {
    
        super();
    }
    
    /**
     * Set the studentTemporaryLeaveId.
     * 
     * @param intStudentTemporaryLeaveId the studentTemporaryLeaveId to set.
     */
    public void setStudentTemporaryLeaveId(int intStudentTemporaryLeaveId) {
    
        this.studentTemporaryLeaveId = intStudentTemporaryLeaveId;
    }
    
    /**
     * Get the studentTemporaryLeaveId.
     * 
     * @return the studentTemporaryLeaveId.
     */
    public int getStudentTemporaryLeaveId() {
    
        return studentTemporaryLeaveId;
    }
    
    /**
     * Set the student.
     * 
     * @param stuStudent the student to set.
     */
    public void setStudent(Student stuStudent) {
    
        this.student = stuStudent;
    }
    
    /**
     * Get the student.
     * 
     * @return the student.
     */
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * Set the toDate.
     * 
     * @param dateToDate the student to set.
     */
    public void setToDate(Date dateToDate) {
    	 if (dateToDate != null) {

             this.toDate = (Date) dateToDate.clone();
         }
        
    }
    
    /**
     * Get the toDate.
     * 
     * @return the toDate.
     */
    public Date getToDate() {
    	  if (toDate != null) {
              return (Date) toDate.clone();
          }

          return null;
        
    }
    
    /**
     * Set the fromDate.
     * 
     * @param dateFromDate the fromDate to set.
     */
    public void setFromDate(Date dateFromDate) {
    	 if (dateFromDate != null) {

             this.fromDate = (Date) dateFromDate.clone();
         }
        
    }
    
    /**
     * Get the fromDate.
     * 
     * @return the fromDate.
     */
    public Date getFromDate() {
    	if (fromDate != null) {
            return (Date) fromDate.clone();
        }

        return null;
        
    }
    
    /**
     * Set the reason.
     * 
     * @param strReason the reason to set.
     */
    public void setReason(String strReason) {
    
        this.reason = strReason;
    }
    
    /**
     * Get the reason.
     * 
     * @return the reason.
     */
    public String getReason() {
    
        return reason;
    }
    
    /**
     * Set the completeClearance.
     * 
     * @param isCompleteClearance the completeClearance to set.
     */
    public void setCompleteClearance(boolean isCompleteClearance) {
    
        this.completeClearance = isCompleteClearance;
    }
    
    /**
     * Get the completeClearance.
     * 
     * @return the completeClearance.
     */
    public boolean isCompleteClearance() {
    
        return completeClearance;
    }
    
    /**
     * Set the activateDate.
     * 
     * @param dateActivateDate the activateDate to set.
     */
    public void setActivateDate(Date dateActivateDate) {
    	 if (dateActivateDate != null) {

             this.activateDate = (Date) dateActivateDate.clone();
         }
        
    }
    
    /**
     * Get the activateDate.
     * 
     * @return the activateDate.
     */
    public Date getActivateDate() {
    	if (activateDate != null) {
            return (Date) activateDate.clone();
        }

        return null;
       
    }
    
    /**
     * Set the noOfDays.
     * 
     * @param intNoOfDays the noOfDays to set.
     */
    
    public void setNoOfDays(int intNoOfDays) {
    
        this.noOfDays = intNoOfDays;
    }
    
    /**
     * Get the noOfDays.
     * 
     * @return the noOfDays.
     */
    public int getNoOfDays() {
    
        return noOfDays;
    }
    
    /**
     * return class Grade Description of the student.
     * 
     * @return the classGradeDescription
     */
    public String getClassGradeDescription() {
    
        return classGradeDescription;
    }
    
    /**
     * Setter method for setClassGradeDescription.
     * 
     * @param classGradeDescriptionObj the setClassGradeDescription to set
     */
    public void setClassGradeDescription(String classGradeDescriptionObj) {
    
        this.classGradeDescription = classGradeDescriptionObj;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return "StudentTemporaryLeave [studentTemporaryLeaveId=" + studentTemporaryLeaveId + ", student=" + student
                + ", toDate=" + toDate + ", fromDate=" + fromDate + ", reason=" + reason + " , completeClearance="
                + completeClearance + "]";
    }
    
}
