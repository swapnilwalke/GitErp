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
 * This class represents all the properties of a school domain object.
 *
 * @author Virtusa Corporation
 */
public class School extends BaseDomain implements Serializable {

    /**
     * Default Serial id.
     */
    private static final long serialVersionUID = 1L;

    /** The constant for String "school id = ". */
    private static final String SCHOOL_ID = "school id = ";

    /** The constant for String " name = ". */
    private static final String NAME = " name = ";

    /** The constant for String " modified date/time". */
    private static final String MODIFIED_DATE_TIME = " modified date/time";

    /**
     * Represents the id of a School.
     */
    private int schoolId;

    /**
     * Represents an id of the principal for a School.
     */
    private int principalId;

    /**
     * Represents an id of the vice principal for a School.
     */
    private Integer vicePrincipalId;

    /**
     * Represents the number of Staff for a School.
     */
    private int noOfStaff;

    /**
     * Represents the number of Students for a School.
     */
    private int noOfStudents;

    /**
     * Represents the primary and the secondary information of a School.
     */
    private String primaryAndSecondarySchoolInfo;

    /**
     * Represents the facilities of a School.
     */
    private String facilities;

    /**
     * Represents the email address of a School.
     */
    private String email;

    /**
     * Represents the name of a School.
     */
    private String name;

    /**
     * Represents the address of a School.
     */
    private String address;

    /**
     * Represents the contact number of a School.
     */
    private String contactNo;

    /**
     * Represents the fax number of a School.
     */
    private String faxNo;

    /**
     * Represents the web site of a School.
     */
    private String website;

    /**
     * Represents the started date of a School.
     */
    private Date startedDate;

    /**
     * Represents the province of a School.
     */
    private Province province;

    /**
     * Represents the district of a School.
     */
    private District district;
    
    
    /**
     * Represents the country of a School.
     */
    private Country country;

    /**
     * Constructs a new School object.
     */
    public School() {

    }

    /**
     * Returns the id for this School object.
     *
     * @return - the id of the School.
     */
    public int getSchoolId() {

        return schoolId;
    }

    /**
     * Sets the id for this School with a given key.
     *
     * @param intSchoolId - the id of the School.
     */
    public void setSchoolId(int intSchoolId) {

        this.schoolId = intSchoolId;
    }

    /**
     * Returns the id of the principal for this School object.
     *
     * @return - the id of the Principal for this School
     */
    public int getPrincipalId() {

        return principalId;
    }

    /**
     * Sets the id of the Principal for this School object.
     *
     * @param intPrincipalId - the id of the principal for this School object
     */
    public void setPrincipalId(int intPrincipalId) {

        this.principalId = intPrincipalId;
    }

    /**
     * Returns the id of the vice principal, wrapped in Integer, for this School object.
     *
     * @return - the id of the vice principal for the School as Integer.
     */
    public Integer getVicePrincipalId() {

        return vicePrincipalId;
    }

    /**
     * Sets the id of the vice principal for this School object.
     *
     * @param integerVicePrincipalId - the id of the vicePrincipal for this School object
     */
    public void setVicePrincipalId(Integer integerVicePrincipalId) {

        this.vicePrincipalId = integerVicePrincipalId;
    }

    /**
     * Returns the number of Staff for this School object.
     *
     * @return - the number of Staff of the School.
     */
    public int getNoOfStaff() {

        return noOfStaff;
    }

    /**
     * Sets the number of Staff for this School.
     *
     * @param strNoOfStaff - the number of the Staff for the School.
     */
    public void setNoOfStaff(int strNoOfStaff) {

        this.noOfStaff = strNoOfStaff;
    }

    /**
     * Returns the primary and the secondary school information for this School object.
     *
     * @return - the primary and the secondary school information of the School.
     */
    public String getPrimaryAndSecondarySchoolInfo() {

        return primaryAndSecondarySchoolInfo;
    }

    /**
     * Sets the primary and the secondary school information for this School.
     *
     * @param strPrimaryAndSecondarySchoolInfo - the primary and the secondary school information of the
     *        School.
     */
    public void setPrimaryAndSecondarySchoolInfo(String strPrimaryAndSecondarySchoolInfo) {

        this.primaryAndSecondarySchoolInfo = strPrimaryAndSecondarySchoolInfo;
    }

    /**
     * Returns the number of Students for this School object.
     *
     * @return - the number of Students of the School.
     */
    public int getNoOfStudents() {

        return noOfStudents;
    }

    /**
     * Sets the number of Students for this School.
     *
     * @param strNoOfStudents - the number of Students of the School.
     */
    public void setNoOfStudents(int strNoOfStudents) {

        this.noOfStudents = strNoOfStudents;
    }

    /**
     * Returns the description about the facilities for this School object.
     *
     * @return - the description about the facilities of the School.
     */
    public String getFacilities() {

        return facilities;
    }

    /**
     * Sets the facilities for this School.
     *
     * @param strFacilities - the facilities of the School.
     */
    public void setFacilities(String strFacilities) {

        this.facilities = strFacilities;
    }

    /**
     * Returns the email address for this School object.
     *
     * @return - the email address of the School.
     */
    public String getEmail() {

        return email;
    }

    /**
     * Sets the email address for this School.
     *
     * @param strEmail - the email address of the School.
     */
    public void setEmail(String strEmail) {

        this.email = strEmail;
    }

    /**
     * Returns the name for this School object.
     *
     * @return - the name of the School.
     */
    public final String getName() {

        return name;
    }

    /**
     * Sets the name of the School.
     *
     * @param strName - the name of the school.
     */
    public final void setName(final String strName) {

        this.name = strName;
    }

    /**
     * Returns the contact number for this School object.
     *
     * @return - the contact number of the School.
     */
    public final String getContactNo() {

        return contactNo;
    }

    /**
     * Sets the contact number of this School object.
     *
     * @param strContactNo - the contact number of the School.
     */
    public final void setContactNo(final String strContactNo) {

        this.contactNo = strContactNo;
    }

    /**
     * Returns the fax number for this School object.
     *
     * @return - the fax number of the School.
     */
    public final String getFaxNo() {

        return faxNo;
    }

    /**
     * Sets the fax number of this school object.
     *
     * @param strFaxNo - the fax number of the School.
     */
    public final void setFaxNo(final String strFaxNo) {

        this.faxNo = strFaxNo;
    }

    /**
     * Returns the web site for this School object.
     *
     * @return - the web site of the School.
     */
    public final String getWebsite() {

        return website;
    }

    /**
     * Sets the web site of the School.
     *
     * @param strWebsite - the web site of the School.
     */
    public final void setWebsite(final String strWebsite) {

        this.website = strWebsite;
    }

    /**
     * Returns the started date for this School object.
     *
     * @return - the started date of the School.
     */
    public final Date getStartedDate() {

        if (startedDate != null) {

            return (Date) startedDate.clone();
        }

        return null;
    }

    /**
     * Sets the started date of the School.
     *
     * @param dateStartedDate - started date of the School.
     */
    public final void setStartedDate(final Date dateStartedDate) {

        if (dateStartedDate != null) {

            this.startedDate = (Date) dateStartedDate.clone();
        }
    }

    /**
     * Returns the province of the School.
     *
     * @return - the province of this School.
     */
    public final Province getProvince() {

        return province;
    }

    /**
     * Sets the province of a School.
     *
     * @param proProvince - province of this School object.
     */
    public final void setProvince(final Province proProvince) {

        this.province = proProvince;
    }

    /**
     * Returns the district of this School object.
     *
     * @return - the district of the School.
     */
    public final District getDistrict() {

        return district;
    }

    /**
     * Sets the district of the School.
     *
     * @param disDistrict - district of this School.
     */
    public final void setDistrict(final District disDistrict) {

        this.district = disDistrict;
    }

    /**
     * Returns the address for this School object.
     *
     * @return - the address of the School.
     */
    public final String getAddress() {

        return address;
    }

    /**
     * Sets the address of the School.
     *
     * @param strAddress - address of the School.
     */
    public final void setAddress(final String strAddress) {

        this.address = strAddress;
    }

    
    /**
     * Get the country of the school.
     * @return {@link Country}
     */
    public Country getCountry() {
		return country;
	}

    /**
     * Set the country of the school.
     * @param countryVal of the school.
     */
	public void setCountry(Country countryVal) {
		this.country = countryVal;
	}

	/**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {
        return SCHOOL_ID + this.schoolId + NAME + this.name + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
