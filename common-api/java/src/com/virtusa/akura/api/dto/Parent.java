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

/**
 * This class represents all properties of Parent domain object.
 * 
 * @author Virtusa Corporation
 */

public class Parent extends BaseDomain implements Serializable {
    
    /** attribute for holding string modified date and time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** attribute for holding string working segment id. */
    private static final String WORKING_SEGMENT_ID = " working segment id = ";
    
    /** attribute for holding string office name. */
    private static final String OFFICE_NAME = " office name = ";
    
    /** attribute for holding string temorary city id. */
    private static final String TEMORARY_CITY_ID = " temorary city id = ";
    
    /** attribute for holding string alumni id. */
    private static final String ALUMNI_ID = " alumni id = ";
    
    /** attribute for holding string religion id. */
    private static final String RELIGION_ID = " religion id = ";
    
    /** attribute for holding string relationship. */
    private static final String RELATIONSHIP = " relationship = ";
    
    /** attribute for holding string permanet address. */
    private static final String PERMANET_ADDRESS = " permanet address = ";
    
    /** attribute for holding string working segment id. */
    private static final String NATIONAL_ID_NO = " national id no = ";
    
    /** attribute for holding string last name. */
    private static final String LAST_NAME = " last name = ";
    
    /** attribute for holding string name with initials. */
    private static final String NAME_WITH_INITIALS = " name with initials = ";
    
    /** attribute for holding string full name. */
    private static final String FULL_NAME = " full name = ";
    
    /** attribute for holding string is past pupil. */
    private static final String IS_PAST_PUPIL = " is past pupil = ";
    
    /** attribute for holding string is teacher. */
    private static final String IS_TEACHER = " is teacher = ";
    
    /** attribute for holding string city id. */
    private static final String CITY_ID = "city id = ";
    
    /** attribute for holding string parent id. */
    private static final String PARENT_ID = "parent id = ";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = -3080722625779604115L;
    
    /** Represents the id for a Parent. */
    private int parentId;
    
    /** Represents the city of the parent. */
    private Integer cityId;
    
    /** Represents the Employment Status of the parent. */
    private Integer employmentStatusId;
    
    /** Represents the religion id of the parent. */
    private Integer religionId;
    
    /** Represents the alumni number of the parent if parent is a past pupil. */
    private String alumniId;
    
    /** Represents the temporary city id of the parent. */
    private Integer tempCityId;
    
    /** Represents the office city id of the parent. */
    private Integer officeCityId;
    
    /** Represents the workingSegment Id of the parent. */
    private Integer workingSegmentId;
    
    /** Flag to check if parent is a teacher of the school. */
    private boolean teacher;
    
    /** Flag to check if parent is a past pupil of the school. */
    private boolean pastPupil;
    
    /** Represents the teacher registration Number if parent is a teacher of the school. */
    private String teacherRegNo;
    
    /** Represents the full name of the parent. */
    private String fullName;
    
    /** Represents the name with initials of the parent. */
    private String nameWithInitials;
    
    /** Represents the last name of the parent. */
    private String lastName;
    
    /** Represents the national Id Number of the parent. */
    private String nationalIdNo;
    
    /** Represents the address of the parent. */
    private String address;
    
    /** Represents the mobile number of the parent. */
    private String mobileNo;
    
    /** Represents the residence phone number of the parent. */
    private String residenceNo;
    
    /** Represents the office phone number of the parent. */
    private String officeNo;
    
    /** Represents the relationship of the parent. */
    private String relationship = "F";
    
    /** Represents the email address of the parent. */
    private String email;
    
    /** Represents the income level of the parent. */
    private String incomeLevel;
    
    /** Represents the temporary address of the parent. */
    private String tempAddress;
    
    /** Represents the office name of the parent. */
    private String officeName;
    
    /** Represents the office address of the parent. */
    private String officeAddress;
    
    /** Represents the office fax number of the parent. */
    private String officeFaxNo;
    
    /** Represents the office email address of the parent. */
    private String officeEmail;
    
    /** Represents the designation of the parent. */
    private String designation;
    
    

    /**
     * Constructs a new Parent object.
     */
    public Parent() {
    
    }
    
    /**
     * Constructs a new parent object with the specified parent id.
     * 
     * @param intParentId - the parent id.
     */
    public Parent(final int intParentId) {
    
        this.parentId = intParentId;
    }
    
    /**
     * Returns the id for this Parent object.
     * 
     * @return the id of the Parent object
     */
    public final int getParentId() {
    
        return parentId;
    }
    
    /**
     * Sets the id for this Parent object with the given key.
     * 
     * @param intParentId - id of the Parent.
     */
    public final void setParentId(final int intParentId) {
    
        this.parentId = intParentId;
    }
    
    /**
     * Returns the boolean value if parent is teacher for this Parent object.
     * 
     * @return true, if the parent is a teacher of the school or false otherwise.
     */
    public boolean isTeacher() {
    
        return teacher;
    }
    
    /**
     * Sets the boolean value for the teacher of Parent object with the given value.
     * 
     * @param isTeacher - boolean value for the teacher of the Parent.
     */
    public final void setTeacher(final boolean isTeacher) {
    
        this.teacher = isTeacher;
    }
    
    /**
     * Returns the boolean value if parent is past Pupil for this Parent object.
     * 
     * @return true, if the parent is a past pupil of the school or false otherwise.
     */
    public boolean isPastPupil() {
    
        return pastPupil;
    }
    
    /**
     * Sets the boolean value for the past pupil of Parent object with the given value.
     * 
     * @param isPastPupil - boolean value for the past pupil of the Parent.
     */
    public final void setPastPupil(final boolean isPastPupil) {
    
        this.pastPupil = isPastPupil;
    }
    
    /**
     * Returns the full name for this Parent object.
     * 
     * @return the full name of the Parent object.
     */
    public final String getFullName() {
    
        return fullName;
    }
    
    /**
     * Sets the full name for this Parent object with the given information.
     * 
     * @param strFullName - full name of the Parent.
     */
    public final void setFullName(final String strFullName) {
    
        this.fullName = strFullName;
    }
    
    /**
     * Returns the name with initials for this Parent object.
     * 
     * @return the name with initials of the Parent object.
     */
    public final String getNameWithInitials() {
    
        return nameWithInitials;
    }
    
    /**
     * Sets the name with initials for this Parent object with the given information.
     * 
     * @param strNameInitials - name with initials of the Parent.
     */
    public final void setNameWithInitials(final String strNameInitials) {
    
        this.nameWithInitials = strNameInitials;
    }
    
    /**
     * Returns the last name for this Parent object.
     * 
     * @return the last name of the Parent object.
     */
    public final String getLastName() {
    
        return lastName;
    }
    
    /**
     * Sets the last name for this Parent object with the given information.
     * 
     * @param strLastName - last name of the Parent.
     */
    public final void setLastName(final String strLastName) {
    
        this.lastName = strLastName;
    }
    
    /**
     * Returns the national ID number for this Parent object.
     * 
     * @return the national ID number of the Parent object.
     */
    public final String getNationalIdNo() {
    
        return nationalIdNo;
    }
    
    /**
     * Sets the national ID number for this Parent object with the given information.
     * 
     * @param strIdNo - national ID number of the Parent.
     */
    public final void setNationalIdNo(final String strIdNo) {
    
        this.nationalIdNo = strIdNo;
    }
    
    /**
     * Returns the permanent address for this Parent object.
     * 
     * @return the permanent address of the Parent object.
     */
    public final String getAddress() {
    
        return address;
    }
    
    /**
     * Sets the permanent address for this Parent object with the given information.
     * 
     * @param strAddress - permanent address of the Parent.
     */
    public final void setAddress(final String strAddress) {
    
        this.address = strAddress;
    }
    
    /**
     * Returns the mobile number for this Parent object.
     * 
     * @return the mobile number of the Parent object.
     */
    public final String getMobileNo() {
    
        return mobileNo;
    }
    
    /**
     * Sets the mobile number for this Parent object with the given information.
     * 
     * @param strMobileNo - mobile number of the Parent.
     */
    public final void setMobileNo(final String strMobileNo) {
    
        this.mobileNo = strMobileNo;
    }
    
    /**
     * Returns the residence number for this Parent object.
     * 
     * @return the residence number of the Parent object.
     */
    public final String getResidenceNo() {
    
        return residenceNo;
    }
    
    /**
     * Sets the residence number for this Parent object with the given information.
     * 
     * @param strResidenceNo - residence number of the Parent.
     */
    public final void setResidenceNo(final String strResidenceNo) {
    
        this.residenceNo = strResidenceNo;
    }
    
    /**
     * Returns the office number for this Parent object.
     * 
     * @return the office number of the Parent object.
     */
    public final String getOfficeNo() {
    
        return officeNo;
    }
    
    /**
     * Sets the office number for this Parent object with the given information.
     * 
     * @param strOfficeNo - office number of the Parent.
     */
    public final void setOfficeNo(final String strOfficeNo) {
    
        this.officeNo = strOfficeNo;
    }
    
    /**
     * Returns the relationship for this Parent object.
     * 
     * @return the relationship of the Parent object.
     */
    public final String getRelationship() {
    
        return relationship;
    }
    
    /**
     * Sets the relationship for this Parent object with the given information.
     * 
     * @param strRelationship - relationship of the Parent.
     */
    public final void setRelationship(final String strRelationship) {
    
        this.relationship = strRelationship;
    }
    
    /**
     * Returns the email address for this Parent object.
     * 
     * @return the email address of the Parent object.
     */
    public final String getEmail() {
    
        return email;
    }
    
    /**
     * Sets the email address for this Parent object with the given information.
     * 
     * @param strEmail - email address of the Parent.
     */
    public final void setEmail(final String strEmail) {
    
        this.email = strEmail;
    }
    
    /**
     * Returns the teacher registration number for this Parent object.
     * if isTeacher is true. else return null
     * 
     * @return the teacher registration number of the Parent object.
     */
    public final String getTeacherRegNo() {
        if(isTeacher()){
            return teacherRegNo;
        }else{
            return null;
        }         
    }
    
    /**
     * Sets the teacher registration number for this Parent object with the given information.
     * 
     * @param regNo - teacher registration number of the Parent.
     */
    public final void setTeacherRegNo(final String regNo) {
    
        this.teacherRegNo = regNo;
    }

    /**
     * Returns the city id for this Parent object.
     * 
     * @return the city id of the Parent object.
     */
    public Integer getCityId() {
    
        return cityId;
    }
    
    /**
     * Sets the city id for this Parent object with the given information.
     * 
     * @param intCityId - city id of the Parent.
     */
    public void setCityId(final Integer intCityId) {
    
        this.cityId = intCityId;
    }
    
    /**
     * Returns the religion id for this Parent object.
     * 
     * @return the religion id of the Parent object.
     */
    public final Integer getReligionId() {
    
        return religionId;
    }
    
    /**
     * Sets the religion id for this Parent object with the given information.
     * 
     * @param intReligionId - religion id of the Parent.
     */
    public final void setReligionId(final Integer intReligionId) {
    
        this.religionId = intReligionId;
    }
    
    /**
     * Returns the alumni id for this Parent object.
     * if isPastPupil true else return null.
     * 
     * @return the alumni id of the Parent object.
     */
    public final String getAlumniId() {
        if(pastPupil){
            return alumniId;
        }else{
            return null;
        }
        
    }
    
    /**
     * Sets the alumni id for this Parent object with the given information.
     * 
     * @param intAluminiId - alumni id of the Parent.
     */
    public final void setAlumniId(final String intAluminiId) {
    
        this.alumniId = intAluminiId;
    }
    
    /**
     * Returns the income level for this Parent object.
     * 
     * @return the income level of the Parent object.
     */
    public final String getIncomeLevel() {
    
        return incomeLevel;
    }
    
    /**
     * Sets the income level for this Parent object with the given information.
     * 
     * @param strIncomeLevel - income level of the Parent.
     */
    public final void setIncomeLevel(final String strIncomeLevel) {
    
        this.incomeLevel = strIncomeLevel;
    }
    
    /**
     * Returns the temporary address for this Parent object.
     * 
     * @return the temporary address of the Parent object.
     */
    public final String getTempAddress() {
    
        return tempAddress;
    }
    
    /**
     * Sets the temporary address for this Parent object with the given information.
     * 
     * @param strTempAddress - temporary address of the Parent.
     */
    public final void setTempAddress(final String strTempAddress) {
    
        this.tempAddress = strTempAddress;
    }
    
    /**
     * Returns the temporary city id for this Parent object.
     * 
     * @return the temporary city id of the Parent object.
     */
    public final Integer getTempCityId() {
    
        return tempCityId;
    }
    
    /**
     * Sets the temporary city id for this Parent object with the given information.
     * 
     * @param intTempCityId - temporary city id of the Parent.
     */
    public final void setTempCityId(final Integer intTempCityId) {
    
        this.tempCityId = intTempCityId;
    }
    
    /**
     * Returns the office name for this Parent object.
     * 
     * @return the office name of the Parent object.
     */
    public final String getOfficeName() {
    
        return officeName;
    }
    
    /**
     * Sets the office name for this Parent object with the given information.
     * 
     * @param strOfficeName - The office name of the Parent.
     */
    public final void setOfficeName(final String strOfficeName) {
    
        this.officeName = strOfficeName;
    }
    
    /**
     * Returns the office address for this Parent object.
     * 
     * @return the office address of the Parent object.
     */
    public final String getOfficeAddress() {
    
        return officeAddress;
    }
    
    /**
     * Sets the office address for this Parent object with the given information.
     * 
     * @param strOfficeAddress - The office address of the Parent.
     */
    public final void setOfficeAddress(final String strOfficeAddress) {
    
        this.officeAddress = strOfficeAddress;
    }
    
    /**
     * Returns the office city id for this Parent object.
     * 
     * @return the office city id of the Parent object.
     */
    public final Integer getOfficeCityId() {
    
        return officeCityId;
    }
    
    /**
     * Sets the office city id for this Parent object with the given information.
     * 
     * @param intOfficeCityId - The office city id of the Parent.
     */
    public final void setOfficeCityId(final Integer intOfficeCityId) {
    
        this.officeCityId = intOfficeCityId;
    }
    
    /**
     * Returns the office fax number for this Parent object.
     * 
     * @return the office fax number of the Parent object.
     */
    public final String getOfficeFaxNo() {
    
        return officeFaxNo;
    }
    
    /**
     * Sets the office fax number for this Parent object with the given information.
     * 
     * @param strOfficeFaxNo - The office fax number of the Parent.
     */
    public final void setOfficeFaxNo(final String strOfficeFaxNo) {
    
        this.officeFaxNo = strOfficeFaxNo;
    }
    
    /**
     * Returns the office email address for this Parent object.
     * 
     * @return the office email address of the Parent object.
     */
    public final String getOfficeEmail() {
    
        return officeEmail;
    }
    
    /**
     * Sets the office email address for this Parent object with the given information.
     * 
     * @param strOfficeEmail - The office email address of the Parent.
     */
    public final void setOfficeEmail(final String strOfficeEmail) {
    
        this.officeEmail = strOfficeEmail;
    }
    
    /**
     * Returns the designation for this Parent object.
     * 
     * @return the designation of the Parent object.
     */
    public final String getDesignation() {
    
        return designation;
    }
    
    /**
     * Sets the designation for this Parent object with the given information.
     * 
     * @param desig - The designation of the Parent.
     */
    public final void setDesignation(final String desig) {
    
        this.designation = desig;
    }
    
    /**
     * Returns the working segment id for this Parent object.
     * 
     * @return the working segment id of the Parent object.
     */
    public final Integer getWorkingSegmentId() {
    
        return workingSegmentId;
    }
    
    /**
     * Sets the working segment id for this Parent object with the given information.
     * 
     * @param segmentId - The working segment id of the Parent.
     */
    public final void setWorkingSegmentId(final Integer segmentId) {
    
        this.workingSegmentId = segmentId;
    }
    
    
    
    /**
     * Returns the employmentStatusId for this Parent object.
     * 
     * @return the employmentStatusId of the Parent object.
     */
    public Integer getEmploymentStatusId() {
    
        return employmentStatusId;
    }

    /**
     * Sets the employmentStatusId for this Parent object with the given information.
     * 
     * @param employmentStatusIdRef - integer.
     */
    public void setEmploymentStatusId(Integer employmentStatusIdRef) {
    
        this.employmentStatusId = employmentStatusIdRef;
    }

    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return PARENT_ID + this.parentId + CITY_ID + this.cityId + IS_TEACHER + this.teacher + IS_PAST_PUPIL
                + this.pastPupil + FULL_NAME + this.fullName + NAME_WITH_INITIALS + this.nameWithInitials + LAST_NAME
                + this.lastName + NATIONAL_ID_NO + this.nationalIdNo + PERMANET_ADDRESS + this.address + RELATIONSHIP
                + this.relationship + RELIGION_ID + this.religionId + ALUMNI_ID + this.alumniId + TEMORARY_CITY_ID
                + this.tempCityId + OFFICE_NAME + " office city id = " + this.officeCityId + WORKING_SEGMENT_ID
                + this.workingSegmentId + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
