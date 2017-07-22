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
 * This class is to associates all the properties of ProfessionalQualification domain object.
 *
 * @author Virtusa Corporation
 */
public class ProfessionalQualification extends BaseDomain {

    /**
     * Holds the serial version id.
     */
    private static final long serialVersionUID = 1L;

    /** The constant for String "professional qualification id = ". */
    private static final String PROFESSIONAL_QUALIFICATION_ID = " professional qualification id = ";

    /** The constant for String " description ". */
    private static final String DESCRIPTION = " description ";

    /** The constant for String " modified date/time ". */
    private static final String MODIFIED_DATE_TIME = " modified date/time ";

    /**
     * Represents unique id for ProfessionalQualification object.
     */
    private int professionalQualificationId;

    /**
     * Represents description of ProfessionalQualification.
     */
    private String description;

    /**
     * Default constructor.
     */
    public ProfessionalQualification() {

    }

    /**
     * Overloaded constructor with parameter professionalQualificationId.
     *
     * @param intProfessionalQualificationId type int
     */
    public ProfessionalQualification(int intProfessionalQualificationId) {

        this.professionalQualificationId = intProfessionalQualificationId;
    }

    /**
     * Get the professional qualification id of ProfessionalQualification.
     *
     * @return the professionalQualificationId
     */
    public int getProfessionalQualificationId() {

        return professionalQualificationId;
    }

    /**
     * Set the professional qualification id for ProfessionalQualification.
     *
     * @param intProfessionalQualificationId the professionalQualificationId to set
     */
    public void setProfessionalQualificationId(int intProfessionalQualificationId) {

        this.professionalQualificationId = intProfessionalQualificationId;
    }

    /**
     * Get the description of professional qualification.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Set the description to professional qualification.
     *
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }

    /**
     * Returns a String that represents this Object.
     *
     * @return - the current object details.
     */
    public String toString() {

        return PROFESSIONAL_QUALIFICATION_ID + this.professionalQualificationId + DESCRIPTION + this.description
                + MODIFIED_DATE_TIME + this.getModifiedTime();
    }

}
