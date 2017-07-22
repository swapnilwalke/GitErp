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
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.codec.Base64;
import org.springframework.web.multipart.MultipartFile;

/**
 * Student Domain Class which holds the properties of the student.
 * 
 * @author Virtusa Corporation
 */
public class Student extends AuditableBaseDomain implements Serializable {
    
    /** auto-serial no. */
    private static final long serialVersionUID = 1L;
    
    /** Holding nationality. */
    private Integer nationalityId;
    
    /** Holding bloodGroup. */
    private Integer bloodGroupId;
    
    /** Holding methodOfTravel 1. */
    private Integer travelId;
    
    /** Holding methodOfTravel 2. */
    private Integer travelId2;
    
    /** Holding methodOfTravel 3. */
    private Integer travelId3;
    
    /** Holding house. */
    private Integer houseId;
    
    /** Holding religion. */
    private Integer religionId;
    
    /** Holding race. */
    private Integer raceId;
    
    /** Holding city. */
    private Integer cityId;
    
    /** Holding countryId. */
    private Integer countryId;
    
    /** Holding language. */
    private Integer languageId;
    
    /** unique identifier which represents the primary key of Student. */
    private int studentId;
    
    /** temporaryCity of the student. */
    private Integer temporaryCityId;
    
    /** represents the last modified user. */
    private int modifiedUserId;
    
    /** gender of the student. */
    private char gender;
    
    /** flag is to represent whether student is an OldBoy. */
    private Boolean isOldBoy;
    
    /** byte attribute for holding photo. */
    private byte[] photo;
    
    /** String attribute for holding photo to pass to json. */
    private String photoToMobile;
    
    /** byte attribute for holding the multipartfile photo. */
    private MultipartFile mPhoto;
    
    /** represents the admission Date . */
    private Date admissionDate;
    
    /** represents the first day of the school. */
    private Date firstSchoolDay;
    
    /** dateOfBirth of the student. */
    private Date dateOfBirth;
    
    /** the start date of the previous school of the student. */
    private Date previousSchoolFromDate;
    
    /** the last date of the previous school of the student. */
    private Date previousSchoolToDate;
    
    /** represents the admissionNo of the student. */
    private String admissionNo;
    
    /** represents the fullName of the student. */
    private String fullName;
    
    /** nameWtInitials of the student. */
    private String nameWtInitials;
    
    /** last Name of the student. */
    private String lastName;
    
    /** nationalIdNo of the student. */
    private String nationalIdNo;
    
    /** permanentAddress of the student. */
    private String address;
    
    /** temporaryAddress of the Student. */
    private String temporaryAddress;
    
    /** mobileNo of the student. */
    private String mobileNo;
    
    /** residenceNo of the student. */
    private String residenceNo;
    
    /** email address of the student. */
    private String email;
    
    /** previous school attended by the student. */
    private String previousSchoolName;
    
    /** passed grade of the student in the previous school. */
    private String previousSchoolPassedGrade;
    
    /** studied grade of the student in the previous school. */
    private String previousSchoolStudiedGrade;
    
    /** study medium of the student in the previous school. */
    private Integer previousSchoolStudyMediumId;
    
    /** the reason for leaving the previous school. */
    private String previousSchoolReasonForLeave;
    
    /** holds the emergencyContactFullName. */
    private String emergencyContactFullName;
    
    /** holds the emergencyContactNameWtInitials. */
    private String emergencyContactNameWtInitials;
    
    /** holds the emergencyContactLastName. */
    private String emergencyContactLastName;
    
    /** holds the emergencyContactRelationship. */
    private String emergencyContactRelationship;
    
    /** holds the emergencyContactResidenceNo. */
    private String emergencyContactResidenceNo;
    
    /** holds the emergencyContactMobileNo. */
    private String emergencyContactMobileNo;
    
    /** holds the emergencyContactOfficeNo. */
    private String emergencyContactOfficeNo;
    
    /** holds the emergencyContactEmailAddress. */
    private String emergencyContactEmailAddress;
    
    /** studentDisability relates to the student. */
    private StudentDisability studentDisability;
    
    /** parish of the student. */
    private String parish;
    
    /** Flag to check if parent is a past pupil of the school. */
    private boolean hasSibling;
    
    /** siblingAdmitionNo of the student. */
    private String siblingAdmitionNo;
    
    /** Holding housesForCaptain. */
    private Set<House> housesForCaptain = new HashSet<House>(0);
    
    /** Holding studentDisciplines. */
    private Set<StudentDiscipline> studentDisciplines = new HashSet<StudentDiscipline>(0);
    
    /** Holding studentSports. */
    private Set<StudentSport> studentSports = new HashSet<StudentSport>(0);
    
    /** Holding studentPrefects. */
    private Set<StudentPrefect> studentPrefects = new HashSet<StudentPrefect>(0);
    
    /** Holding studentParents. */
    private Set<StudentParent> studentParents = new HashSet<StudentParent>(0);
    
    /** Holding studentDisabilities. */
    private Set<StudentDisability> studentDisabilities = new HashSet<StudentDisability>(0);
    
    /** Holding achievements. */
    private Set<Achievement> achievements = new HashSet<Achievement>(0);
    
    /** Holding housesForViceCaptain. */
    private Set<House> housesForViceCaptain = new HashSet<House>(0);
    
    /** Holding studentScholarships. */
    private Set<StudentScholarship> studentScholarships = new HashSet<StudentScholarship>(0);
    
    /** Holding studentClassInfos. */
    private Set<StudentClassInfo> studentClassInfos = new HashSet<StudentClassInfo>(0);
    
    /** Holding studentClubSocieties. */
    private Set<StudentClubSociety> studentClubSocieties = new HashSet<StudentClubSociety>(0);
    
    /**
     * Represents FaithLifeRatings for a Student object.
     */
    private Set<FaithLifeRating> faithLifeRating;
    
    /** Holds the date of departure for PastStudent object. */
    private Date dateOfDeparture;
    
    /** Holds the statusId for PastStudent object. */
    private Integer statusId;
    
    
    

	/**
     * return status ID of the student.
     * 
     * @return the statusId
     */
    public Integer getStatusId() {

        return statusId;
    }
    
    /**
     * set the status ID of the student.
     * 
     * @param statusIdVal - the status id of the student
     */
    public void setStatusId(Integer statusIdVal) {

        this.statusId = statusIdVal;
    }
    
    /**
     * return dateOfDeparture of the student.
     * 
     * @return the dateOfDeparture
     */
    public Date getDateOfDeparture() {

        return dateOfDeparture;
    }
    
    /**
     * Setter method for dateOfDeparture.
     * 
     * @param dateOfDepartureRef the dateOfDeparture to set
     */
    public void setDateOfDeparture(Date dateOfDepartureRef) {

        this.dateOfDeparture = dateOfDepartureRef;
    }
    
    /**
     * Default constructor for the student class.
     */
    public Student() {

    }
    
    /**
     * Constructor with the primary key for the student class.
     * 
     * @param studId - Integer type parameter
     */
    public Student(int studId) {

        this.studentId = studId;
    }
    
    /**
     * return studentId of the student.
     * 
     * @return the studentId
     */
    public int getStudentId() {

        return studentId;
    }
    
    /**
     * Setter method for studentId.
     * 
     * @param studentIdVal the studentId to set
     */
    public void setStudentId(int studentIdVal) {

        this.studentId = studentIdVal;
    }
    
    /**
     * Returns an instance of the multipartfile.
     * 
     * @return - an instance of multipartfile
     */
    @JsonIgnore
    public MultipartFile getMPhoto() {

        return mPhoto;
    }
    
    /**
     * Sets the multipartfile of the student.
     * 
     * @param mMPhoto - multipartfile of the student
     */
    @JsonIgnore
    public void setMPhoto(MultipartFile mMPhoto) {

        this.mPhoto = mMPhoto;
    }
    
    /**
     * return modifiedUserId of the student.
     * 
     * @return the modifiedUserId
     */
    public int getModifiedUserId() {

        return modifiedUserId;
    }
    
    /**
     * Setter method for modifiedUserId.
     * 
     * @param modifiedUserIdVal the modifiedUserId to set
     */
    public void setModifiedUserId(int modifiedUserIdVal) {

        this.modifiedUserId = modifiedUserIdVal;
    }
    
    /**
     * return admissionNo of the student.
     * 
     * @return the admissionNo
     */
    public String getAdmissionNo() {

        return admissionNo;
    }
    
    /**
     * set the admissionNo of the student.
     * 
     * @param admissionNoVal the admissionNo to set
     */
    public void setAdmissionNo(String admissionNoVal) {

        this.admissionNo = admissionNoVal;
    }
    
    /**
     * return admissionDate of the student.
     * 
     * @return the admissionDate
     */
    public Date getAdmissionDate() {

        if (admissionDate != null) {
            return (Date) admissionDate.clone();
        }
        
        return null;
    }
    
    /**
     * set the admissionDate.
     * 
     * @param admissionDateVal the admissionDate to set
     */
    public void setAdmissionDate(Date admissionDateVal) {

        if (admissionDateVal != null) {
            
            this.admissionDate = (Date) admissionDateVal.clone();
        }
    }
    
    /**
     * return the firstSchoolDay of the student.
     * 
     * @return the firstSchoolDay
     */
    public Date getFirstSchoolDay() {

        return firstSchoolDay;
        
    }
    
    /**
     * Set firstSchoolDay of the student.
     * 
     * @param firstSchoolDayVal the firstSchoolDay to set
     */
    public void setFirstSchoolDay(Date firstSchoolDayVal) {

        this.firstSchoolDay = firstSchoolDayVal;
        
    }
    
    /**
     * return the fullName of the student.
     * 
     * @return the fullName
     */
    public String getFullName() {

        return fullName;
    }
    
    /**
     * set the fullName of the student.
     * 
     * @param fullNameVal the fullName to set
     */
    public void setFullName(String fullNameVal) {

        this.fullName = fullNameVal;
    }
    
    /**
     * return nameWtInitials of the student.
     * 
     * @return the nameWtInitials
     */
    public String getNameWtInitials() {

        return nameWtInitials;
    }
    
    /**
     * set the nameWtInitials of the student.
     * 
     * @param nameWtInitialsVal the nameWtInitials to set
     */
    public void setNameWtInitials(String nameWtInitialsVal) {

        this.nameWtInitials = nameWtInitialsVal;
    }
    
    /**
     * return the lastName of the student.
     * 
     * @return the lastName
     */
    public String getLastName() {

        return lastName;
    }
    
    /**
     * set the lastName of the student.
     * 
     * @param lastNameVal the lastName to set
     */
    public void setLastName(String lastNameVal) {

        this.lastName = lastNameVal;
    }
    
    /**
     * return the nationalIdNo of the student.
     * 
     * @return the nationalIdNo
     */
    public String getNationalIdNo() {

        return nationalIdNo;
    }
    
    /**
     * set the nationalIdNo of the student.
     * 
     * @param nationalIdNoVal the nationalIdNo to set
     */
    public void setNationalIdNo(String nationalIdNoVal) {

        this.nationalIdNo = nationalIdNoVal;
    }
    
    /**
     * return the mobileNo of the student.
     * 
     * @return the mobileNo
     */
    public String getMobileNo() {

        return mobileNo;
    }
    
    /**
     * set the mobileNo of the student.
     * 
     * @param mobileNoVal the mobileNo to set
     */
    public void setMobileNo(String mobileNoVal) {

        this.mobileNo = mobileNoVal;
    }
    
    /**
     * return the residenceNo of the student.
     * 
     * @return the residenceNo
     */
    public String getResidenceNo() {

        return residenceNo;
    }
    
    /**
     * set the residenceNo of the student.
     * 
     * @param residenceNoVal the residenceNo to set
     */
    public void setResidenceNo(String residenceNoVal) {

        this.residenceNo = residenceNoVal;
    }
    
    /**
     * return the email address of the student.
     * 
     * @return the email
     */
    public String getEmail() {

        return email;
    }
    
    /**
     * set the email address of the student.
     * 
     * @param emailVal the email to set
     */
    public void setEmail(String emailVal) {

        this.email = emailVal;
    }
    
    /**
     * return the gender of the student.
     * 
     * @return the gender
     */
    public char getGender() {

        return gender;
    }
    
    /**
     * set the gender for the student.
     * 
     * @param genderVal the gender to set
     */
    public void setGender(char genderVal) {

        this.gender = genderVal;
    }
    
    /**
     * return the House for Captain for the student.
     * 
     * @return the housesForCaptain
     */
    @JsonIgnore
    public Set<House> getHousesForCaptain() {

        return housesForCaptain;
    }
    
    /**
     * set the House for captain for the student.
     * 
     * @param collHousesForCaptain the housesForCaptain to set
     */
    @JsonIgnore
    public void setHousesForCaptain(Set<House> collHousesForCaptain) {

        this.housesForCaptain = collHousesForCaptain;
    }
    
    /**
     * return student disciplines for the student.
     * 
     * @return the studentDisciplines
     */
    @JsonIgnore
    public Set<StudentDiscipline> getStudentDisciplines() {

        return studentDisciplines;
    }
    
    /**
     * set student disciplines of the student.
     * 
     * @param collStudentDisciplines the studentDisciplines to set
     */
     @JsonIgnore
    public void setStudentDisciplines(Set<StudentDiscipline> collStudentDisciplines) {

        this.studentDisciplines = collStudentDisciplines;
    }
    
    /**
     * return the student sport for the student.
     * 
     * @return the studentSports
     */
    @JsonIgnore
    public Set<StudentSport> getStudentSports() {

        return studentSports;
    }
    
    /**
     * set the student sport for the student.
     * 
     * @param collStudentSports the studentSports to set
     */
    @JsonIgnore
    public void setStudentSports(Set<StudentSport> collStudentSports) {

        this.studentSports = collStudentSports;
    }
    
    /**
     * return the student prefects.
     * 
     * @return the studentPrefects
     */
    @JsonIgnore
    public Set<StudentPrefect> getStudentPrefects() {

        return studentPrefects;
    }
    
    /**
     * set the student prefects for the student.
     * 
     * @param collStudentPrefects the studentPrefects to set
     */
    @JsonIgnore
    public void setStudentPrefects(Set<StudentPrefect> collStudentPrefects) {

        this.studentPrefects = collStudentPrefects;
    }
    
    /**
     * return the parents of the student.
     * 
     * @return the studentParents
     */
    
    public Set<StudentParent> getStudentParents() {

        return studentParents;
    }
    
    /**
     * set the parents of the student.
     * 
     * @param collStudentParents the studentParents to set
     */
   
    public void setStudentParents(Set<StudentParent> collStudentParents) {

        this.studentParents = collStudentParents;
    }
    
    /**
     * return the student disability of the student.
     * 
     * @return the studentDisabilities
     */
    
    public Set<StudentDisability> getStudentDisabilities() {

        return studentDisabilities;
    }
    
    /**
     * set the student disabilities of the student.
     * 
     * @param collStudentDisabilities the studentDisabilities to set
     */
    
    public void setStudentDisabilities(Set<StudentDisability> collStudentDisabilities) {

        this.studentDisabilities = collStudentDisabilities;
    }
    
    /**
     * set the achievements for the student.
     * 
     * @return the achievements
     */
    @JsonIgnore
    public Set<Achievement> getAchievements() {

        return achievements;
    }
    
    /**
     * return the achievement of the student.
     * 
     * @param collAchievements the achievements to set
     */
    @JsonIgnore
    public void setAchievements(Set<Achievement> collAchievements) {

        this.achievements = collAchievements;
    }
    
    /**
     * return the houses for the vice captain.
     * 
     * @return the housesForViceCaptain
     */
    @JsonIgnore
    public Set<House> getHousesForViceCaptain() {

        return housesForViceCaptain;
    }
    
    /**
     * set the houses for the vice captain.
     * 
     * @param collHousesForViceCaptain the housesForViceCaptain to set
     */
    @JsonIgnore
    public void setHousesForViceCaptain(Set<House> collHousesForViceCaptain) {

        this.housesForViceCaptain = collHousesForViceCaptain;
    }
    
    /**
     * return studentScholarships.
     * 
     * @return the studentScholarships
     */
    @JsonIgnore
    public Set<StudentScholarship> getStudentScholarships() {

        return studentScholarships;
    }
    
    /**
     * set the scholarships of the student.
     * 
     * @param collStudentScholarships the studentScholarships to set
     */
    @JsonIgnore
    public void setStudentScholarships(Set<StudentScholarship> collStudentScholarships) {

        this.studentScholarships = collStudentScholarships;
    }
    
    /**
     * return the class info of the student.
     * 
     * @return the studentClassInfos
     */
    
    public Set<StudentClassInfo> getStudentClassInfos() {

        return studentClassInfos;
    }
    
    /**
     * set Class Info of the student.
     * 
     * @param collStudentClassInfos the studentClassInfos to set
     */
    
    public void setStudentClassInfos(Set<StudentClassInfo> collStudentClassInfos) {

        this.studentClassInfos = collStudentClassInfos;
    }
    
    /**
     * return club societies of the student.
     * 
     * @return the studentClubSocieties
     */
    @JsonIgnore
    public Set<StudentClubSociety> getStudentClubSocieties() {

        return studentClubSocieties;
    }
    
    /**
     * set the club societies of the student.
     * 
     * @param collStudentClubSocieties the studentClubSocieties to set
     */
    @JsonIgnore
    public void setStudentClubSocieties(Set<StudentClubSociety> collStudentClubSocieties) {

        this.studentClubSocieties = collStudentClubSocieties;
    }
    
    /**
     * set the byte array which represents the photo of the student.
     * 
     * @return the photo
     */
    @JsonIgnore
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
    @JsonIgnore
    public void setPhoto(byte[] bPhoto) {

        if (bPhoto != null) {
            this.photo = (byte[]) bPhoto.clone();
        }
    }
    
    /**
     * get the String which represents the photoToMobile of the student.
     * 
     * @return String
     */
    public String getPhotoToMobile() {

        if (photo != null) {
            return new String(Base64.encode((byte[]) photo.clone()));
        }
        return null;
    }
    
    /**
     * set String which represents the photoToMobile of the student.
     * 
     * @param bPhoto - String
     */
    public void setPhotoToMobile(String rPhoto) {

        if (rPhoto != null) {
            this.photoToMobile = rPhoto;
        }
    }
    
    /**
     * return the dateOfBirth of the student.
     * 
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {

        if (dateOfBirth != null) {
            
            return (Date) dateOfBirth.clone();
        }
        
        return null;
    }
    
    /**
     * set the dateOfBirth of the student.
     * 
     * @param dateOfBirthVal the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirthVal) {

        if (dateOfBirthVal != null) {
            
            this.dateOfBirth = (Date) dateOfBirthVal.clone();
        }
        
    }
    
    /**
     * return the nationalityId of the student.
     * 
     * @return the nationalityId
     */
    public Integer getNationalityId() {

        return nationalityId;
    }
    
    /**
     * set the nationalityId of the student.
     * 
     * @param nationalityIdVal the nationalityId to set
     */
    public void setNationalityId(Integer nationalityIdVal) {

        this.nationalityId = nationalityIdVal;
    }
    
    /**
     * return the bloodGroupId of the student.
     * 
     * @return the bloodGroupId
     */
    public Integer getBloodGroupId() {

        return bloodGroupId;
    }
    
    /**
     * set the bloodGroupId of the student.
     * 
     * @param bloodGroupIdVal the bloodGroupId to set
     */
    public void setBloodGroupId(Integer bloodGroupIdVal) {

        this.bloodGroupId = bloodGroupIdVal;
    }
    
    /**
     * return the travelId of the student.
     * 
     * @return the travelId
     */
    public Integer getTravelId() {

        return travelId;
    }
    
    /**
     * set the travelId of the student.
     * 
     * @param travelIdVal the travelId to set
     */
    public void setTravelId(Integer travelIdVal) {

        this.travelId = travelIdVal;
    }
    
    /**
     * Gets the travel id2.
     * 
     * @return the travelId2
     */
    public Integer getTravelId2() {

        return travelId2;
    }
    
    /**
     * Sets the travel id2.
     * 
     * @param intTravelId2 the new travel id2
     */
    public void setTravelId2(Integer intTravelId2) {

        this.travelId2 = intTravelId2;
    }
    
    /**
     * Gets the travel id3.
     * 
     * @return the travelId3
     */
    public Integer getTravelId3() {

        return travelId3;
    }
    
    /**
     * Sets the travel id3.
     * 
     * @param intTravelId3 the new travel id3
     */
    public void setTravelId3(Integer intTravelId3) {

        this.travelId3 = intTravelId3;
    }
    
    /**
     * return the houseId of the student.
     * 
     * @return the houseId
     */
    public Integer getHouseId() {

        return houseId;
    }
    
    /**
     * set the houseId of the student.
     * 
     * @param houseIdVal the houseId to set
     */
    public void setHouseId(Integer houseIdVal) {

        this.houseId = houseIdVal;
    }
    
    /**
     * return the religionId of the student.
     * 
     * @return the religionId
     */
    public Integer getReligionId() {

        return religionId;
    }
    
    /**
     * set the religionId.
     * 
     * @param religionIdVal the religionId to set
     */
    public void setReligionId(Integer religionIdVal) {

        this.religionId = religionIdVal;
    }
    
    /**
     * return the cityId of the student.
     * 
     * @return the cityId
     */
    public Integer getCityId() {

        return cityId;
    }
    
    /**
     * set the cityId.
     * 
     * @param cityIdVal the cityId to set
     */
    public void setCityId(Integer cityIdVal) {

        this.cityId = cityIdVal;
    }
    
    /**
     * Get the countryId of the student.
     * 
     * @return the countryId.
     */
    public Integer getCountryId() {

        return countryId;
    }
    
    /**
     * Set the countryId of the student.
     * 
     * @param countryIdVal -the countryId to be set.
     */
    public void setCountryId(Integer countryIdVal) {

        this.countryId = countryIdVal;
    }
    
    /**
     * return the languageId of the student.
     * 
     * @return the languageId
     */
    public Integer getLanguageId() {

        return languageId;
    }
    
    /**
     * set the languageId.
     * 
     * @param languageIdVal the languageId to set
     */
    public void setLanguageId(Integer languageIdVal) {

        this.languageId = languageIdVal;
    }
    
    /**
     * return the temporaryAddress of the student.
     * 
     * @return the temporaryAddress
     */
    public String getTemporaryAddress() {

        return temporaryAddress;
    }
    
    /**
     * set the temporaryAddress.
     * 
     * @param temporaryAddressVal the temporaryAddress to set
     */
    public void setTemporaryAddress(String temporaryAddressVal) {

        this.temporaryAddress = temporaryAddressVal;
    }
    
    /**
     * return the isOldBoy of the student.
     * 
     * @return the isOldBoy
     */
    public Boolean getIsOldBoy() {

        return isOldBoy;
    }
    
    /**
     * set the isOldBoy flag of the student.
     * 
     * @param isOldBoyVal the status to set
     */
    public void setIsOldBoy(Boolean isOldBoyVal) {

        this.isOldBoy = isOldBoyVal;
    }
    
    /**
     * return the temporaryCityId of the student.
     * 
     * @return the temporaryCityId
     */
    public Integer getTemporaryCityId() {

        return temporaryCityId;
    }
    
    /**
     * set the temporaryCityId.
     * 
     * @param temporaryCityIdVal the temporaryCityId to set
     */
    public void setTemporaryCityId(Integer temporaryCityIdVal) {

        this.temporaryCityId = temporaryCityIdVal;
    }
    
    /**
     * return the address of the student.
     * 
     * @return the address
     */
    public String getAddress() {

        return address;
    }
    
    /**
     * set the address.
     * 
     * @param addressVal the address to set
     */
    public void setAddress(String addressVal) {

        this.address = addressVal;
    }
    
    /**
     * return the previousSchoolName of the student.
     * 
     * @return the previousSchoolName
     */
    public String getPreviousSchoolName() {

        return previousSchoolName;
    }
    
    /**
     * set the previousSchoolName.
     * 
     * @param previousSchoolNameVal the previousSchoolName to set
     */
    public void setPreviousSchoolName(String previousSchoolNameVal) {

        this.previousSchoolName = previousSchoolNameVal;
    }
    
    /**
     * return the previousSchoolPassedGrade of the student.
     * 
     * @return the previousSchoolPassedGrade
     */
    public String getPreviousSchoolPassedGrade() {

        return previousSchoolPassedGrade;
    }
    
    /**
     * set the previousSchoolPassedGrade of the student.
     * 
     * @param previousSchoolPassedGradeVal the previousSchoolPassedGrade to set
     */
    public void setPreviousSchoolPassedGrade(String previousSchoolPassedGradeVal) {

        this.previousSchoolPassedGrade = previousSchoolPassedGradeVal;
    }
    
    /**
     * return the previousSchoolStudiedGrade of the student.
     * 
     * @return the previousSchoolStudiedGrade
     */
    public String getPreviousSchoolStudiedGrade() {

        return previousSchoolStudiedGrade;
    }
    
    /**
     * set the previousSchoolStudiedGrade.
     * 
     * @param previousSchoolStudiedGradeVal the previousSchoolStudiedGrade to set
     */
    public void setPreviousSchoolStudiedGrade(String previousSchoolStudiedGradeVal) {

        this.previousSchoolStudiedGrade = previousSchoolStudiedGradeVal;
    }
    
    /**
     * return the previousSchoolStudyMediumId of the student.
     * 
     * @return the previousSchoolStudyMediumId
     */
    public Integer getPreviousSchoolStudyMediumId() {

        return previousSchoolStudyMediumId;
    }
    
    /**
     * set the previousSchoolStudyMediumId.
     * 
     * @param previousSchoolStudyMediumIdVal the previousSchoolStudyMediumId to set
     */
    public void setPreviousSchoolStudyMediumId(Integer previousSchoolStudyMediumIdVal) {

        this.previousSchoolStudyMediumId = previousSchoolStudyMediumIdVal;
    }
    
    /**
     * return the previousSchoolFromDate of the student.
     * 
     * @return the previousSchoolFromDate
     */
    public Date getPreviousSchoolFromDate() {

        if (previousSchoolFromDate != null) {
            
            return (Date) previousSchoolFromDate.clone();
        }
        
        return null;
    }
    
    /**
     * set the previousSchoolFromDate.
     * 
     * @param previousSchoolFromDateVal the previousSchoolFromDate to set
     */
    public void setPreviousSchoolFromDate(Date previousSchoolFromDateVal) {

        if (previousSchoolFromDateVal != null) {
            
            this.previousSchoolFromDate = (Date) previousSchoolFromDateVal.clone();
            
        }
    }
    
    /**
     * return the previousSchoolToDate of the student.
     * 
     * @return the previousSchoolToDate
     */
    public Date getPreviousSchoolToDate() {

        if (previousSchoolToDate != null) {
            return (Date) previousSchoolToDate.clone();
            
        }
        
        return null;
    }
    
    /**
     * set the previousSchoolToDate.
     * 
     * @param previousSchoolToDateVal the previousSchoolToDate to set
     */
    public void setPreviousSchoolToDate(Date previousSchoolToDateVal) {

        if (previousSchoolToDateVal != null) {
            this.previousSchoolToDate = (Date) previousSchoolToDateVal.clone();
            
        }
    }
    
    /**
     * return the previousSchoolReasonForLeave of the student.
     * 
     * @return the previousSchoolReasonForLeave
     */
    public String getPreviousSchoolReasonForLeave() {

        return previousSchoolReasonForLeave;
    }
    
    /**
     * set the previousSchoolReasonForLeave.
     * 
     * @param previousSchoolReasonForLeaveVal the previousSchoolReasonForLeave to set
     */
    public void setPreviousSchoolReasonForLeave(String previousSchoolReasonForLeaveVal) {

        this.previousSchoolReasonForLeave = previousSchoolReasonForLeaveVal;
    }
    
    /**
     * return the emergencyContactFullName of the student.
     * 
     * @return the emergencyContactFullName
     */
    public String getEmergencyContactFullName() {

        return emergencyContactFullName;
    }
    
    /**
     * set the emergencyContactFullName.
     * 
     * @param emergencyContactFullNameVal the emergencyContactFullName to set
     */
    public void setEmergencyContactFullName(String emergencyContactFullNameVal) {

        this.emergencyContactFullName = emergencyContactFullNameVal;
    }
    
    /**
     * return the emergencyContactNameWtInitials of the student.
     * 
     * @return the emergencyContactNameWtInitials
     */
    public String getEmergencyContactNameWtInitials() {

        return emergencyContactNameWtInitials;
    }
    
    /**
     * set the emergencyContactNameWtInitials.
     * 
     * @param emergencyContactNameWtInitialsVal the emergencyContactNameWtInitials to set
     */
    public void setEmergencyContactNameWtInitials(String emergencyContactNameWtInitialsVal) {

        this.emergencyContactNameWtInitials = emergencyContactNameWtInitialsVal;
    }
    
    /**
     * return the emergencyContactLastName of the student.
     * 
     * @return the emergencyContactLastName
     */
    public String getEmergencyContactLastName() {

        return emergencyContactLastName;
    }
    
    /**
     * set the emergencyContactLastName.
     * 
     * @param emergencyContactLastNameVal the emergencyContactLastName to set
     */
    public void setEmergencyContactLastName(String emergencyContactLastNameVal) {

        this.emergencyContactLastName = emergencyContactLastNameVal;
    }
    
    /**
     * return the emergencyContactRelationship of the student.
     * 
     * @return the emergencyContactRelationship
     */
    public String getEmergencyContactRelationship() {

        return emergencyContactRelationship;
    }
    
    /**
     * set the emergencyContactRelationship.
     * 
     * @param emergencyContactRelationshipVal the emergencyContactRelationship to set
     */
    public void setEmergencyContactRelationship(String emergencyContactRelationshipVal) {

        this.emergencyContactRelationship = emergencyContactRelationshipVal;
    }
    
    /**
     * return the emergencyContactResidenceNo of the student.
     * 
     * @return the emergencyContactResidenceNo
     */
    public String getEmergencyContactResidenceNo() {

        return emergencyContactResidenceNo;
    }
    
    /**
     * set the emergencyContactResidenceNo.
     * 
     * @param emergencyContactResidenceNoVal the emergencyContactResidenceNo to set
     */
    public void setEmergencyContactResidenceNo(String emergencyContactResidenceNoVal) {

        this.emergencyContactResidenceNo = emergencyContactResidenceNoVal;
    }
    
    /**
     * return the emergencyContactMobileNo of the student.
     * 
     * @return the emergencyContactMobileNo
     */
    public String getEmergencyContactMobileNo() {

        return emergencyContactMobileNo;
    }
    
    /**
     * set the emergencyContactMobileNo of the student.
     * 
     * @param emergencyContactMobileNoVal the emergencyContactMobileNo to set
     */
    public void setEmergencyContactMobileNo(String emergencyContactMobileNoVal) {

        this.emergencyContactMobileNo = emergencyContactMobileNoVal;
    }
    
    /**
     * return the emergencyContactOfficeNo of the student.
     * 
     * @return the emergencyContactOfficeNo
     */
    public String getEmergencyContactOfficeNo() {

        return emergencyContactOfficeNo;
    }
    
    /**
     * set the emergencyContactOfficeNo.
     * 
     * @param emergencyContactOfficeNoVal the emergencyContactOfficeNo to set
     */
    public void setEmergencyContactOfficeNo(String emergencyContactOfficeNoVal) {

        this.emergencyContactOfficeNo = emergencyContactOfficeNoVal;
    }
    
    /**
     * return the emergencyContactEmailAddress of the student.
     * 
     * @return the emergencyContactEmailAddress
     */
    public String getEmergencyContactEmailAddress() {

        return emergencyContactEmailAddress;
    }
    
    /**
     * set the emergencyContactEmailAddress.
     * 
     * @param emergencyContactEmailAddressVal the emergencyContactEmailAddress to set
     */
    public void setEmergencyContactEmailAddress(String emergencyContactEmailAddressVal) {

        this.emergencyContactEmailAddress = emergencyContactEmailAddressVal;
    }
    
    /**
     * return the studentDisability of the student.
     * 
     * @return the studentDisability
     */
    public StudentDisability getStudentDisability() {

        return studentDisability;
    }
    
    /**
     * set the studentDisability.
     * 
     * @param studentDisabilityValue the studentDisabilityValue to set
     */
    public void setStudentDisability(StudentDisability studentDisabilityValue) {

        this.studentDisability = studentDisabilityValue;
    }
    
    /**
     * Returns FaithLifeRatings for a Student object.
     * 
     * @return - the FaithLifeRatings for a Student object.
     */
    
    public Set<FaithLifeRating> getFaithLifeRating() {

        return faithLifeRating;
    }
    
    /**
     * Sets FaithLifeRatings for a Student object.
     * 
     * @param fFaithLifeRating - the FaithLifeRatings for a Student object.
     */
    
    public void setFaithLifeRating(Set<FaithLifeRating> fFaithLifeRating) {

        this.faithLifeRating = fFaithLifeRating;
    }
    
    /**
     * Returns parish for a Student object.
     * 
     * @return - parish for a Student object.
     */
    public String getParish() {

        return parish;
    }
    
    /**
     * Sets Parish for a Student object.
     * 
     * @param parishRef of type string.
     */
    public void setParish(String parishRef) {

        this.parish = parishRef;
    }
    
    /**
     * Returns raceId for a Student object.
     * 
     * @return - raceId for a Student object.
     */
    public Integer getRaceId() {

        return raceId;
    }
    
    /**
     * Sets raceId for a Student object.
     * 
     * @param raceIdRef of type Integer.
     */
    public void setRaceId(Integer raceIdRef) {

        this.raceId = raceIdRef;
    }
    
    /**
     * Returns the boolean value if student has sibling.
     * 
     * @return true, if the student has sibling of the school or false otherwise.
     */
    public boolean isHasSibling() {

        return hasSibling;
    }
    
    /**
     * Sets the boolean value for has Sibling.
     * 
     * @param hasSiblingRef - boolean value for the has sibling.
     */
    public void setHasSibling(boolean hasSiblingRef) {

        this.hasSibling = hasSiblingRef;
    }
    
    /**
     * Returns siblingAdmitionNo for a Student object.
     * 
     * @return - siblingAdmitionNo for a Student object.
     */
    public String getSiblingAdmitionNo() {

        return siblingAdmitionNo;
    }
    
    /**
     * Sets siblingAdmitionNo for a Student object.
     * 
     * @param siblingAdmitionNoRef of type string.
     */
    public void setSiblingAdmitionNo(String siblingAdmitionNoRef) {

        this.siblingAdmitionNo = siblingAdmitionNoRef;
    }
    

    /**
     * overridden toString method to represent the student object.
     * 
     * @return String representation of the student.
     */
    @Override
    public String toString() {

        return "Student [studentId=" + studentId + ", admissionNo=" + admissionNo + ", fullName=" + fullName
                + ", firstSchoolDay=" + firstSchoolDay + "]";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {

        return "Student [studentId=" + studentId + ", admissionNo=" + admissionNo + ", fullName=" + fullName + "]";
    }
    
}
