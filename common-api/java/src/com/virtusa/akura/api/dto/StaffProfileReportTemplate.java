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

import java.util.Arrays;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * The StaffProfileReportTemplate class.
 * 
 * @author Virtusa Corporation
 */

public class StaffProfileReportTemplate extends BaseDomain implements Cloneable{
    
    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds the staffId of the Staff. */
    private int staffId;
    
    /** Holds the staffRegNo of the Staff. */
    private String staffRegNo;
    
    /** Holds the national ID of the Staff. */
    private String nationalID;
    
    /** Holds the full name of the Staff. */
    private String fullName;
    
    /** Holds the name with initials of the Staff. */
    private String nameWithIntials;
    
    /** Holds the section of the Staff. */
    private String section;
    
    /** Holds the address of the Staff. */
    private String address;
    
    /** Holds the city of the Staff. */
    private String city;
    
    /** Holds the residence number of the Staff. */
    private String residenceNo;
    
    /** Holds the mobile number of the Staff. */
    private String mobileNo;
    
    /** Holds the email of the Staff. */
    private String email;
    
    /** Holds the country of the Staff. */
    private String country;
    
    /** Holds the race of the Staff. */
    private String race;
    
    /** Holds the religion of the Staff. */
    private String religion;
    
    /** Holds the medium of the staff. */
    private String civilStatus;
    
    /** Holds the date of birth of the Staff. */
    private Date dateOfBirth;
    
    /** Holds the gender of the Staff. */
    private String gender;
    
    /** Holds the firstAppointmet Date of the Staff. */
    private Date firstAppointmetDate;
    
    /** Holds the hire date of the Staff. */
    private Date dateOfHire;
    
    /** Holds the appointmentNature of the Staff. */
    private String appointmentNature;
    
    /** Holds the appointmentClassification of the Staff. */
    private String appointmentClassification;
    
    /** Holds the medium of the staff. */
    private String studyMedium;
    
    /** Holds the category of the Staff. */
    private String staffCategory;
    
    /** Holds the educationalQualificationId of the Staff. */
    private String educationalQualification;
    
    /** Holds the professionalQualificationId of the Staff. */
    private String professionalQualification;
    
    /** Holds the departure date of the Staff. */
    private String departureDate;
    
    /** Holds the staffServiceCategory of the Staff. */
    private String staffServiceCategory;
    
    /** Holds the staffClass of the Staff. */
    private String staffClass;
    
    /** Holds the staffGrade of the Staff. */
    private String staffGrade;
    
    /** Holds the basic salary of the Staff. */
    private Float basicSalary;
    
    /** byte attribute for holding photo. */
    private byte[] photo;
    
    /** byte attribute for holding the multipartfile photo. */
    private MultipartFile mPhoto;
    
    /**
     * @return staffId
     */
    public int getStaffId() {
    
        return staffId;
    }
    
    /**
     * @param staffIdRef - staffId
     */
    public void setStaffId(int staffIdRef) {
    
        this.staffId = staffIdRef;
    }
    
    /**
     * @return staffRegNo
     */
    public String getStaffRegNo() {
    
        return staffRegNo;
    }
    
    /**
     * @param staffRegNoRef staffRegNo
     */
    public void setStaffRegNo(String staffRegNoRef) {
    
        this.staffRegNo = staffRegNoRef;
    }
    
    /**
     * @return fullName
     */
    public String getFullName() {
    
        return fullName;
    }
    
    /**
     * @param fullNameRef - fullName
     */
    public void setFullName(String fullNameRef) {
    
        this.fullName = fullNameRef;
    }
    
    /**
     * @return nationalID
     */
    public String getNationalID() {
    
        return nationalID;
    }
    
    /**
     * @param nationalIDRef nationalID
     */
    public void setNationalID(String nationalIDRef) {
    
        this.nationalID = nationalIDRef;
    }
    
    /**
     * @return nameWithIntials
     */
    public String getNameWithIntials() {
    
        return nameWithIntials;
    }
    
    /**
     * @param nameWithIntialsRef - nameWithIntials
     */
    public void setNameWithIntials(String nameWithIntialsRef) {
    
        this.nameWithIntials = nameWithIntialsRef;
    }
    
    /**
     * @return section
     */
    public String getSection() {
    
        return section;
    }
    
    /**
     * @param sectionRef - section
     */
    public void setSection(String sectionRef) {
    
        this.section = sectionRef;
    }
    
    /**
     * @return address
     */
    public String getAddress() {
    
        return address;
    }
    
    /**
     * @param addressRef - address
     */
    public void setAddress(String addressRef) {
    
        this.address = addressRef;
    }
    
    /**
     * @return city
     */
    public String getCity() {
    
        return city;
    }
    
    /**
     * @param cityRef - city
     */
    public void setCity(String cityRef) {
    
        this.city = cityRef;
    }
    
    /**
     * @return residenceNo
     */
    public String getResidenceNo() {
    
        return residenceNo;
    }
    
    /**
     * @param residenceNoRef - residenceNo
     */
    public void setResidenceNo(String residenceNoRef) {
    
        this.residenceNo = residenceNoRef;
    }
    
    /**
     * @return mobileNo
     */
    public String getMobileNo() {
    
        return mobileNo;
    }
    
    /**
     * @param mobileNoRef - mobileNo
     */
    public void setMobileNo(String mobileNoRef) {
    
        this.mobileNo = mobileNoRef;
    }
    
    /**
     * @return email
     */
    public String getEmail() {
    
        return email;
    }
    
    /**
     * @param emailRef - email
     */
    public void setEmail(String emailRef) {
    
        this.email = emailRef;
    }
    
    /**
     * @return country
     */
    public String getCountry() {
    
        return country;
    }
    
    /**
     * @param countryRef - country
     */
    public void setCountry(String countryRef) {
    
        this.country = countryRef;
    }
    
    /**
     * @return race
     */
    public String getRace() {
    
        return race;
    }
    
    /**
     * @param raceRef - race
     */
    public void setRace(String raceRef) {
    
        this.race = raceRef;
    }
    
    /**
     * @return religion
     */
    public String getReligion() {
    
        return religion;
    }
    
    /**
     * @param religionRef - religion
     */
    public void setReligion(String religionRef) {
    
        this.religion = religionRef;
    }
    
    /**
     * @return civilStatus
     */
    public String getCivilStatus() {
    
        return civilStatus;
    }
    
    /**
     * @param civilStatusRef -civilStatus
     */
    public void setCivilStatus(String civilStatusRef) {
    
        this.civilStatus = civilStatusRef;
    }
    
    /**
     * @return dateOfBirth
     */
    public Date getDateOfBirth() {
    
        return dateOfBirth;
    }
    
    /**
     * @param dateOfBirthRef - dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirthRef) {
    
        this.dateOfBirth = dateOfBirthRef;
    }
    
    /**
     * @return gender
     */
    public String getGender() {
    
        return gender;
    }
    
    /**
     * @param genderRef - gender
     */
    public void setGender(String genderRef) {
    
        this.gender = genderRef;
    }
    
    /**
     * @return firstAppointmetDate
     */
    public Date getFirstAppointmetDate() {
    
        return firstAppointmetDate;
    }
    
    /**
     * @param firstAppointmetDateRef - firstAppointmetDate
     */
    public void setFirstAppointmetDate(Date firstAppointmetDateRef) {
    
        this.firstAppointmetDate = firstAppointmetDateRef;
    }
    
    /**
     * @return dateOfHire
     */
    public Date getDateOfHire() {
    
        return dateOfHire;
    }
    
    /**
     * @param dateOfHireRef - dateOfHire
     */
    public void setDateOfHire(Date dateOfHireRef) {
    
        this.dateOfHire = dateOfHireRef;
    }
    
    /**
     * @return appointmentNature
     */
    public String getAppointmentNature() {
    
        return appointmentNature;
    }
    
    /**
     * @param appointmentNatureRef - appointmentNature
     */
    public void setAppointmentNature(String appointmentNatureRef) {
    
        this.appointmentNature = appointmentNatureRef;
    }
    
    /**
     * @return appointmentClassification
     */
    public String getAppointmentClassification() {
    
        return appointmentClassification;
    }
    
    /**
     * @param appointmentClassificationRef - appointmentClassification
     */
    public void setAppointmentClassification(String appointmentClassificationRef) {
    
        this.appointmentClassification = appointmentClassificationRef;
    }
    
    /**
     * @return studyMedium
     */
    public String getStudyMedium() {
    
        return studyMedium;
    }
    
    /**
     * @param studyMediumRef - studyMedium
     */
    public void setStudyMedium(String studyMediumRef) {
    
        this.studyMedium = studyMediumRef;
    }
    
    /**
     * @return staffCategory
     */
    public String getStaffCategory() {
    
        return staffCategory;
    }
    
    /**
     * @param staffCategoryRef - staffCategory
     */
    public void setStaffCategory(String staffCategoryRef) {
    
        this.staffCategory = staffCategoryRef;
    }
    
    /**
     * @return educationalQualification
     */
    public String getEducationalQualification() {
    
        return educationalQualification;
    }
    
    /**
     * @param educationalQualificationRef - educationalQualification
     */
    public void setEducationalQualification(String educationalQualificationRef) {
    
        this.educationalQualification = educationalQualificationRef;
    }
    
    /**
     * @return professionalQualification
     */
    public String getProfessionalQualification() {
    
        return professionalQualification;
    }
    
    /**
     * @param professionalQualificationRef - professionalQualification
     */
    public void setProfessionalQualification(String professionalQualificationRef) {
    
        this.professionalQualification = professionalQualificationRef;
    }
    
    /**
     * @return staffServiceCategory
     */
    public String getStaffServiceCategory() {
    
        return staffServiceCategory;
    }
    
    /**
     * @param staffServiceCategoryRef - staffServiceCategory
     */
    public void setStaffServiceCategory(String staffServiceCategoryRef) {
    
        this.staffServiceCategory = staffServiceCategoryRef;
    }
    
    /**
     * @return staffClass
     */
    public String getStaffClass() {
    
        return staffClass;
    }
    
    /**
     * @param staffClassRef - staffClass
     */
    public void setStaffClass(String staffClassRef) {
    
        this.staffClass = staffClassRef;
    }
    
    /**
     * @return staffGrade
     */
    public String getStaffGrade() {
    
        return staffGrade;
    }
    
    /**
     * @param staffGradeRef - staffGrade
     */
    public void setStaffGrade(String staffGradeRef) {
    
        this.staffGrade = staffGradeRef;
    }
    
    /**
     * @return basicSalary
     */
    public Float getBasicSalary() {
    
        return basicSalary;
    }
    
    /**
     * @param basicSalaryRef - basicSalary
     */
    public void setBasicSalary(Float basicSalaryRef) {
    
        this.basicSalary = basicSalaryRef;
    }
    
    /**
     * set the byte array which represents the photo of the student.
     * 
     * @return the photo
     */
    public byte[] getPhoto() {
    
        if (photo != null) {
            return (byte[]) photo.clone();
        }
        return null;
    }
    
    /**
     * set the byte array which represents the photo of the student.
     * 
     * @param bPhoto the photo to set
     */
    public void setPhoto(byte[] bPhoto) {
    
        if (bPhoto != null) {
            this.photo = (byte[]) bPhoto.clone();
        }
    }
    
    /**
     * Returns an instance of the multipartfile.
     * 
     * @return - an instance of multipartfile
     */
    public MultipartFile getMPhoto() {
    
        return mPhoto;
    }
    
    /**
     * Sets the multipartfile of the student.
     * 
     * @param mMPhoto - multipartfile of the student
     */
    public void setMPhoto(MultipartFile mMPhoto) {
    
        this.mPhoto = mMPhoto;
    }
    
    /**
     * @return departureDate
     */
    public String getDepartureDate() {
        
        return departureDate;
    }
    
    /**
     * @param departureDate
     */
    public void setDepartureDate(String departureDate) {
    
        this.departureDate = departureDate;
    }
    
}
