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

import java.io.Serializable;
import java.util.Date;

/**
 * Domain object to map data for StaffEducation.
 * 
 * @author Virtusa Corporation
 */
public class StaffEducation extends BaseDomain implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "StaffEducation staffEducationId=". */
    private static final String STAFF_EDUCATION_STAFF_EDUCATION_ID = "StaffEducation staffEducationId=";
    
    /** The constant for String ", educationalQualification=". */
    private static final String EDUCATIONAL_QUALIFICATION = ", educationalQualification=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /** The constant for String ", year=". */
    private static final String YEAR = ", year=";
    
    /** The constant for String ", status=". */
    private static final String STATUS = ", status=";
    
    /** The constant for String ", experience=". */
    private static final String EXPERIENCE = ", experience=";
    
    /**
     * Holds the staffEducationId.
     */
    private int staffEducationId;
    
    /**
     * Holds the Staff.
     */
    private Staff staff;
    
    /**
     * Holds the EducationalQualification.
     */
    private EducationalQualification educationalQualification;
    
    /**
     * Holds the description of the StaffEducation.
     */
    private String description;
    
    /**
     * Holds the year of the StaffEducation.
     */
    private Date year;
    
    /**
     * Holds the status of the StaffEducation.
     */
    private Byte status;
    
    /**
     * Holds the experience of the StaffEducation.
     */
    private String experience;
    
    /**
     * Get the description.
     * 
     * @return the description.
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the description.
     * 
     * @param strDescription the description to set.
     */
    public void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Get the year.
     * 
     * @return the year.
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * Set the year.
     * 
     * @param dateYear the year to set.
     */
    public void setYear(final Date dateYear) {
    
        if (dateYear != null) {
            this.year = (Date) dateYear.clone();
        }
    }
    
    /**
     * Get the Status.
     * 
     * @return the status.
     */
    public Byte getStatus() {
    
        return status;
    }
    
    /**
     * Set the status.
     * 
     * @param intStatus the status to set.
     */
    public void setStatus(final Byte intStatus) {
    
        this.status = intStatus;
    }
    
    /**
     * Get the Experience.
     * 
     * @return the experience.
     */
    public String getExperience() {
    
        return experience;
    }
    
    /**
     * Set the Experience.
     * 
     * @param strExperience the experience to set.
     */
    public void setExperience(final String strExperience) {
    
        this.experience = strExperience;
    }
    
    /**
     * Get the StaffEducationId.
     * 
     * @return the staffEducationId.
     */
    public int getStaffEducationId() {
    
        return staffEducationId;
    }
    
    /**
     * Set the StaffEducationId.
     * 
     * @param intStaffEducationId the staffEducationId to set.
     */
    public void setStaffEducationId(int intStaffEducationId) {
    
        this.staffEducationId = intStaffEducationId;
    }
    
    /**
     * Get the staff member.
     * 
     * @return the staff member.
     */
    public Staff getStaff() {
    
        return staff;
    }
    
    /**
     * Set the Staff member.
     * 
     * @param objStaff the staff to set.
     */
    public void setStaff(Staff objStaff) {
    
        this.staff = objStaff;
    }
    
    /**
     * Get the educational qualifications.
     * 
     * @return the educationalQualification object.
     */
    public EducationalQualification getEducationalQualification() {
    
        return educationalQualification;
    }
    
    /**
     * Set the educational qualifications.
     * 
     * @param objEducationalQualification the educationalQualification to set.
     */
    public void setEducationalQualification(EducationalQualification objEducationalQualification) {
    
        this.educationalQualification = objEducationalQualification;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STAFF_EDUCATION_STAFF_EDUCATION_ID + staffEducationId + EDUCATIONAL_QUALIFICATION
                + educationalQualification + DESCRIPTION + description + YEAR + year + STATUS + status + EXPERIENCE
                + experience;
    }
    
}
