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
 * Domain object to map data for StaffProfessional.
 * 
 * @author Virtusa Corporation
 */
public class StaffProfessional extends BaseDomain implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "StaffProfessional staffProfessionalId=". */
    private static final String STAFF_PROFESSIONAL_STAFF_PROFESSIONAL_ID = "StaffProfessional staffProfessionalId=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /** The constant for String ", fromYear=". */
    private static final String FROM_YEAR = ", fromYear=";
    
    /** The constant for String ", toYear=". */
    private static final String TO_YEAR = ", toYear=";
    
    /** The constant for String ", experience=". */
    private static final String EXPERIENCE = ", experience=";
    
    /** The constant for String ", responsibilities=". */
    private static final String RESPONSIBILITIES = ", responsibilities=";
    
    /**
     * Holds the staffProfessionalId.
     */
    private int staffProfessionalId;
    
    /**
     * Holds the professionalQualification.
     */
    private ProfessionalQualification professionalQualification;
    
    /**
     * Holds the Staff.
     */
    private Staff staff;
    
    /**
     * Holds the description of the professionalQualification.
     */
    private String description;
    
    /**
     * Holds the start year of the professionalQualification.
     */
    private Date fromYear;
    
    /**
     * Holds the end year of the professionalQualification.
     */
    private Date toYear;
    
    /**
     * Holds the experience of the professionalQualification.
     */
    private String experience;
    
    /**
     * Holds the responsibilities of the professionalQualification.
     */
    private String responsibilities;
    
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
     * Get the date from year.
     * 
     * @return the fromYear.
     */
    public Date getFromYear() {

        if (fromYear != null) {
            return (Date) fromYear.clone();
        }
        return null;
    }
    
    /**
     * Set the date from year.
     * 
     * @param dateFromYear the fromYear to set.
     */
    public void setFromYear(final Date dateFromYear) {

        if (dateFromYear != null) {
            this.fromYear = (Date) dateFromYear.clone();
        }
    }
    
    /**
     * Get the date to year.
     * 
     * @return the toYear.
     */
    public Date getToYear() {

        if (toYear != null) {
            return (Date) toYear.clone();
        }
        return null;
        
    }
    
    /**
     * Set the date to year.
     * 
     * @param dateToYear the toYear to set.
     */
    public void setToYear(final Date dateToYear) {

        if (dateToYear != null) {
            this.toYear = (Date) dateToYear.clone();
        }
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
     * Get the Responsibilities.
     * 
     * @return the responsibilities.
     */
    public String getResponsibilities() {

        return responsibilities;
    }
    
    /**
     * Set the Responsibilities.
     * 
     * @param strResponsibilities the responsibilities to set.
     */
    public void setResponsibilities(final String strResponsibilities) {

        this.responsibilities = strResponsibilities;
    }
    
    /**
     * Get the StaffProfessionalId.
     * 
     * @return the staffProfessionalId.
     */
    public int getStaffProfessionalId() {

        return staffProfessionalId;
    }
    
    /**
     * Set the StaffProfessionalId.
     * 
     * @param intStaffProfessionalId the staffProfessionalId to set.
     */
    public void setStaffProfessionalId(int intStaffProfessionalId) {

        this.staffProfessionalId = intStaffProfessionalId;
    }
    
    /**
     * Get the Professional Qualification.
     * 
     * @return the professionalQualification.
     */
    public ProfessionalQualification getProfessionalQualification() {

        return professionalQualification;
    }
    
    /**
     * Set the Professional Qualification.
     * 
     * @param objProfessionalQualification the professionalQualification to set.
     */
    public void setProfessionalQualification(ProfessionalQualification objProfessionalQualification) {

        this.professionalQualification = objProfessionalQualification;
    }
    
    /**
     * Get the Staff.
     * 
     * @return the staff.
     */
    public Staff getStaff() {

        return staff;
    }
    
    /**
     * Set the Staff.
     * 
     * @param objStaff the staff to set.
     */
    public void setStaff(Staff objStaff) {

        this.staff = objStaff;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {

        return STAFF_PROFESSIONAL_STAFF_PROFESSIONAL_ID + staffProfessionalId + DESCRIPTION + description + FROM_YEAR
                + fromYear + TO_YEAR + toYear + EXPERIENCE + experience + RESPONSIBILITIES + responsibilities;
    }
    
}
