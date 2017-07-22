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
 * This class is to associates all the properties of EducationalQualification
 * domain object.
 *
 * @author Virtusa Corporation
 *
 */
public class EducationalQualification extends BaseDomain {

    /** String attribute for to MODIFIED_DATE_TIME. */
	private static final String MODIFIED_DATE_TIME = " modified date/time = ";

	/** String attribute for to DESCRIPTION_VALUE. */
    private static final String DESCRIPTION_VALUE = " description = ";

    /** String attribute for to EDUCATIONAL_QUALIFICATION_ID. */
    private static final String EDUCATIONAL_QUALIFICATION_ID = " educational qualification id = ";

    /**
	 * Holds serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents a unique id for EducationalQualification.
	 */
	private int educationalQualificationId;

	/**
	 * Represents description for EducationalQualification object.
	 */
	private String description;

    /**
     * Default constructor.
     */
    public EducationalQualification() {

    }

    /**
     * Overloaded constructor with parameter educationalQualificationId.
     *
     * @param intEducationalQualificationId type int
     */
    public EducationalQualification(int intEducationalQualificationId) {
    	this.educationalQualificationId = intEducationalQualificationId;
    }

	/**
	 * Get the educational qualification id of EducationalQualification object.
	 *
	 * @return the educationalQualificationId
	 */
	public int getEducationalQualificationId() {
		return educationalQualificationId;
	}

	/**
	 * Set the id for EducationalQualification.
	 *
	 * @param intEducationalQualificationId the educationalQualificationId to set
	 */
	public void setEducationalQualificationId(int intEducationalQualificationId) {
		this.educationalQualificationId = intEducationalQualificationId;
	}

	/**
	 * Get the description of EducationalQualification.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description to EducationalQualification.
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
	    return EDUCATIONAL_QUALIFICATION_ID
                + this.educationalQualificationId + DESCRIPTION_VALUE
                + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
	}
}
