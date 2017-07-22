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

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Virtusa Corporation
 */

public class Staff extends BaseDomain implements Serializable {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "Stream streamId=". */
    private static final String STAFF_FULL_NAME = "Staff fullName=";
    
    /** Holds the id of the Staff. */
    private int staffId;
    
    /**
     * Represents an instance of multipartFile.
     */
    private MultipartFile multipartFile;
    
    /** Holds the educationalQualificationId of the Staff. */
    private int educationalQualificationId;
    
    /** Holds the professionalQualificationId of the Staff. */
    private int professionalQualificationId;
    
    /** Holds the category of the Staff. */
    private StaffCategory staffCategory;
    
    /** Holds the section of the Staff. */
    private Integer section;
    
    /** Holds the section of the Staff. */
    private Integer sectionId;
    
    /** Holds the race of the Staff. */
    private Integer race;
    
    /** Holds the staffServiceCategory of the Staff. */
    private Integer staffServiceCategory;
    
    /** Holds the city of the Staff. */
    private Integer cityId;
    
    /** Holding countryId. */
    private Integer countryId;
    
    /** Holds the temporary city of the Staff. */
    private Integer tempCityId;
    
    /** Holds the religion of the Staff. */
    private Integer religionId;
    
    /** Holds the registration number of the Staff. */
    private String registrationNo;
    
    /** Holds the firstAppointmet Date of the Staff. */
    private Date firstAppointmetDate;  
    
    /** Holds the appointmentNature of the Staff. */
    private Integer appointmentNature;
    
    /** Holds the appointmentClassification of the Staff. */
    private Integer appointmentClassification;   

    /** Holds the hire date of the Staff. */
    private Date dateOfHire;
    
    /** Holds the departure date of the Staff. */
    private Date dateOfDeparture;
    
    /** Holds the gender of the Staff. */
    private char gender;
    
    /** Holds the staffClass of the Staff. */
    private String staffClass;
    
    /** Holds the staffGrade of the Staff. */
    private String staffGrade; 

    /** Holds the basic salary of the Staff. */
    private float basicSalary;
    
    /** Holds the salary scale of the Staff. */
    private String salaryScale;
    
    /** Holds the history of the Staff. */
    private String history;
    
    /** Holds the other info of the Staff. */
    private String otherInfo;
    
    /** Holds the full name of the Staff. */
    private String fullName;
    
    /** Holds the national ID of the Staff. */
    private String nationalID;
    
    /** Holds the name with initials of the Staff. */
    private String nameWithIntials;
    
    /** Holds the last name of the Staff. */
    private String lastName;
    
    /** Holds the date of birth of the Staff. */
    private Date dateOfBirth;
    
    /** Holds the residence number of the Staff. */
    private String residenceNo;
    
    /** Holds the office number of the Staff. */
    private String officeNo;
    
    /** Holds the address of the Staff. */
    private String address;
    
    /** Holds the temporary address of the Staff. */
    private String tempAddress;
    
    /** Holds the mobile number of the Staff. */
    private String mobileNo;
    
    /** Holds the email of the Staff. */
    private String email;
    
    /** Holds the profile image of the Staff Member. */
    private byte[] photo;
    
    /** Holds the departure status of the Staff. */
    private int staffStatus;
    
    /** Holds the subjectTeachers of the Staff. */
    private Set<SubjectTeacher> subjectTeachers = new HashSet<SubjectTeacher>(0);
    
    /** Holds the sectionHeads of the Staff. */
    private Set<SectionHead> sectionHeads = new HashSet<SectionHead>(0);
    
    /** Holds the classTeachers of the Staff. */
    private Set<ClassTeacher> classTeachers = new HashSet<ClassTeacher>(0);
    
    /** Holds the staffProfessionals of the Staff. */
    private Set<StaffProfessional> staffProfessionals = new HashSet<StaffProfessional>(0);
    
    /** Holds the staffEducationals of the Staff. */
    private Set<StaffEducation> staffEducations = new HashSet<StaffEducation>(0);
    
    /** Holds the core subject of the staff. */
    private Subject coreSubject;
    
    /** Holds the medium of the staff. */
    private StudyMedium studyMedium;  
    
    /** Holds the interest of the Staff. */
    private String interest;

    /** Holds the medium of the staff. */
    private Integer civilStatus;
    
    /** Holds a residence number of the Staff to inform any emergency. */
    private String emergencyContactResidenceNo;
    
    

	/** Holds a mobile number of the Staff to inform any emergency. */
    private String  emergencyContactMobileNo;
    
    /** Holds a insurance policy details of the Staff . */
    private String insurancePolicyDetails;
    
    /** Holds a emergency contact name of the Staff . */
    private String emergencyContactName;
    
    /**
     * Default constructor.
     */
    public Staff() {

    }
    
    /**
     * Constructor to create Staff object when give staffID.
     * 
     * @param intStaffId unique ID
     */
    public Staff(int intStaffId) {

        this.staffId = intStaffId;
    }
    
    /**
     * Get the Staff id.
     * 
     * @return the staffId.
     */
    public int getStaffId() {

        return staffId;
    }
    
    /**
     * Set the Staff id.
     * 
     * @param intStaffId the staffID to set.
     */
    public void setStaffId(final int intStaffId) {

        this.staffId = intStaffId;
    }
    
    /**
     * Get the registration no.
     * 
     * @return the registrationNo.
     */
    public String getRegistrationNo() {

        return registrationNo;
    }
    
    /**
     * Set the registration no.
     * 
     * @param strRegistrationNo the registrationNO to set.
     */
    public void setRegistrationNo(final String strRegistrationNo) {

        this.registrationNo = strRegistrationNo;
    }
    
    /**
     * Get the Date of hire.
     * 
     * @return the dateOfHire.
     */
    public Date getDateOfHire() {

        if (dateOfHire != null) {
            return (Date) dateOfHire.clone();
        }
        return null;
    }
    
    /**
     * Set the Date of hire.
     * 
     * @param dateDateOfHire the dateOfHire to set.
     */
    public void setDateOfHire(final Date dateDateOfHire) {

        if (dateDateOfHire != null) {
            this.dateOfHire = (Date) dateDateOfHire.clone();
        }
    }
    
    /**
     * Returns an instance of the multipartFile.
     * 
     * @return - an instance of multipartFile.
     */
    public MultipartFile getMultipartFile() {

        return multipartFile;
    }
    
    /**
     * Sets the multipartFile of the student.
     * 
     * @param fileMultipartFile - multipartFile of the student.
     */
    public void setMultipartFile(MultipartFile fileMultipartFile) {

        this.multipartFile = fileMultipartFile;
    }
    
    /**
     * Get the DateOfDepature.
     * 
     * @return the dateOfDepature.
     */
    public Date getDateOfDepature() {

        if (dateOfDeparture != null) {
            return (Date) dateOfDeparture.clone();
        }
        return null;
    }
    
    /**
     * Set the DateOfDepature.
     * 
     * @param dateDateOfDeparture the dateOfDepature to set.
     */
    public void setDateOfDepature(final Date dateDateOfDeparture) {

        if (dateDateOfDeparture != null) {
            this.dateOfDeparture = (Date) dateDateOfDeparture.clone();
        } else {
            this.dateOfDeparture = dateDateOfDeparture;
        }
    }
    
    /**
     * Get the Gender.
     * 
     * @return the gender.
     */
    public char getGender() {

        return gender;
    }
    
    /**
     * Set the Gender.
     * 
     * @param strGender the gender to set.
     */
    public void setGender(final char strGender) {

        this.gender = strGender;
    }
    
    /**
     * Get the Basic Salary.
     * 
     * @return the basicSalary.
     */
    public float getBasicSalary() {

        return basicSalary;
    }
    
    /**
     * Set the Basic Salary.
     * 
     * @param floatbasicSalary the basicSalary to set.
     */
    public void setBasicSalary(float floatbasicSalary) {

        this.basicSalary = floatbasicSalary;
    }
    
    /**
     * Get the Salary Scale.
     * 
     * @return the salaryScale.
     */
    public String getSalaryScale() {

        return salaryScale;
    }
    
    /**
     * Set the Salary Scale.
     * 
     * @param strSalaryScale the salaryScale to set.
     */
    public void setSalaryScale(final String strSalaryScale) {

        this.salaryScale = strSalaryScale;
    }
    
    /**
     * Get the History.
     * 
     * @return the history.
     */
    public String getHistory() {

        return history;
    }
    
    /**
     * Set the History.
     * 
     * @param strHistory the history to set.
     */
    public void setHistory(final String strHistory) {

        this.history = strHistory;
    }
    
    /**
     * Get the Other info.
     * 
     * @return the otherInfo.
     */
    public String getOtherInfo() {

        return otherInfo;
    }
    
    /**
     * Set the Other info.
     * 
     * @param strOtherInfo the otherInfo to set.
     */
    public void setOtherInfo(final String strOtherInfo) {

        this.otherInfo = strOtherInfo;
    }
    
    /**
     * Get the full name.
     * 
     * @return the fullName.
     */
    public String getFullName() {

        return fullName;
    }
    
    /**
     * Set the full name.
     * 
     * @param strFullName the fullName to set.
     */
    public void setFullName(final String strFullName) {

        this.fullName = strFullName;
    }
    
    /**
     * Get the national id.
     * 
     * @return the nationalID.
     */
    public String getNationalID() {

        return nationalID;
    }
    
    /**
     * Set the national id.
     * 
     * @param strNationalID the nationalID to set.
     */
    public void setNationalID(final String strNationalID) {

        this.nationalID = strNationalID;
    }
    
    /**
     * Get the name with initials.
     * 
     * @return the nameWithIntials.
     */
    public String getNameWithIntials() {

        return nameWithIntials;
    }
    
    /**
     * Set the name with initials.
     * 
     * @param strNameWithIntials the nameWithIntials to set.
     */
    public void setNameWithIntials(final String strNameWithIntials) {

        this.nameWithIntials = strNameWithIntials;
    }
    
    /**
     * Get the last name.
     * 
     * @return the lastName.
     */
    public String getLastName() {

        return lastName;
    }
    
    /**
     * Set the last name.
     * 
     * @param strLastName the lastName to set.
     */
    public void setLastName(final String strLastName) {

        this.lastName = strLastName;
    }
    
    /**
     * Get the Date of birth.
     * 
     * @return the dateOfBirth.
     */
    public Date getDateOfBirth() {

        if (dateOfBirth != null) {
            return (Date) dateOfBirth.clone();
        }
        return null;
    }
    
    /**
     * Set the Date of birth.
     * 
     * @param dateDateOfBirth the dateOfBirth to set.
     */
    public void setDateOfBirth(final Date dateDateOfBirth) {

        if (dateDateOfBirth != null) {
            this.dateOfBirth = (Date) dateDateOfBirth.clone();
        }
    }
    
    /**
     * Get the Residence No.
     * 
     * @return the residenceNo.
     */
    public String getResidenceNo() {

        return residenceNo;
    }
    
    /**
     * Set the Residence No.
     * 
     * @param intResidenceNo the residenceNo to set.
     */
    public void setResidenceNo(final String intResidenceNo) {

        this.residenceNo = intResidenceNo;
    }
    
    /**
     * Get the Office No.
     * 
     * @return the officeNo.
     */
    public String getOfficeNo() {

        return officeNo;
    }
    
    /**
     * Set the Office No.
     * 
     * @param intOfficeNo the officeNo to set.
     */
    public void setOfficeNo(final String intOfficeNo) {

        this.officeNo = intOfficeNo;
    }
    
    /**
     * Get the Address.
     * 
     * @return the address.
     */
    public String getAddress() {

        return address;
    }
    
    /**
     * Set the Address.
     * 
     * @param strAddress the address to set.
     */
    public void setAddress(final String strAddress) {

        this.address = strAddress;
    }
    
    /**
     * Get the Mobile NO.
     * 
     * @return the mobileNo.
     */
    public String getMobileNo() {

        return mobileNo;
    }
    
    /**
     * Set the Mobile NO.
     * 
     * @param intMobileNo the mobileNo to set.
     */
    public void setMobileNo(final String intMobileNo) {

        this.mobileNo = intMobileNo;
    }
    
    /**
     * Get the Email.
     * 
     * @return the email.
     */
    public String getEmail() {

        return email;
    }
    
    /**
     * Set the Email.
     * 
     * @param strEmail the email to set.
     */
    public void setEmail(final String strEmail) {

        this.email = strEmail;
    }
    
    /**
     * Get the Date Of departure.
     * 
     * @return the dateOfDeparture.
     */
    public Date getDateOfDeparture() {

        if (dateOfDeparture != null) {
            return (Date) dateOfDeparture.clone();
        }
        return null;
    }
    
    /**
     * Set the Date Of departure.
     * 
     * @param departureDate the dateOfDeparture to set.
     */
    public void setDateOfDeparture(Date departureDate) {

        if (departureDate != null) {
            this.dateOfDeparture = (Date) departureDate.clone();
        }
    }
    
    /**
     * Get the SubjectTeacher.
     * 
     * @return the subjectTeachers.
     */
    public Set<SubjectTeacher> getSubjectTeachers() {

        return subjectTeachers;
    }
    
    /**
     * Set the SubjectTeacher.
     * 
     * @param collSubjectTeachers the subjectTeachers to set.
     */
    public void setSubjectTeachers(Set<SubjectTeacher> collSubjectTeachers) {

        this.subjectTeachers = collSubjectTeachers;
    }
    
    /**
     * Get the SectionHead.
     * 
     * @return the sectionHeads.
     */
    public Set<SectionHead> getSectionHeads() {

        return sectionHeads;
    }
    
    /**
     * Set the SectionHead.
     * 
     * @param collSectionHeads the sectionHeads to set.
     */
    public void setSectionHeads(Set<SectionHead> collSectionHeads) {

        this.sectionHeads = collSectionHeads;
    }
    
    /**
     * Get the ClassTeacher.
     * 
     * @return the classTeachers.
     */
    public Set<ClassTeacher> getClassTeachers() {

        return classTeachers;
    }
    
    /**
     * Set the ClassTeacher.
     * 
     * @param collClassTeachers the classTeachers to set.
     */
    public void setClassTeachers(Set<ClassTeacher> collClassTeachers) {

        this.classTeachers = collClassTeachers;
    }
    
    /**
     * Get the StaffProfessional.
     * 
     * @return the staffProfessionals.
     */
    public Set<StaffProfessional> getStaffProfessionals() {

        return staffProfessionals;
    }
    
    /**
     * Set the StaffProfessional.
     * 
     * @param collStaffProfessionals the staffProfessionals to set.
     */
    public void setStaffProfessionals(Set<StaffProfessional> collStaffProfessionals) {

        this.staffProfessionals = collStaffProfessionals;
    }
    
    /**
     * Get the StaffEducation.
     * 
     * @return the staffEducations.
     */
    public Set<StaffEducation> getStaffEducations() {

        return staffEducations;
    }
    
    /**
     * Set the StaffEducation.
     * 
     * @param collStaffEducations the staffEducations to set.
     */
    public void setStaffEducations(Set<StaffEducation> collStaffEducations) {

        this.staffEducations = collStaffEducations;
    }
    
    /**
     * Get the religion id for staff.
     * 
     * @return the religionId.
     */
    public Integer getReligionId() {

        return religionId;
    }
    
    /**
     * Set the religion id for staff.
     * 
     * @param religionIdValue the religionId to set.
     */
    public void setReligionId(Integer religionIdValue) {

        this.religionId = religionIdValue;
    }
    
    /**
     * Get the educational qualification id for staff.
     * 
     * @return the educationalQualificationId.
     */
    public int getEducationalQualificationId() {

        return educationalQualificationId;
    }
    
    /**
     * Set the educational qualification id for staff.
     * 
     * @param educationalQualificationIdValue the educationalQualificationId to set.
     */
    public void setEducationalQualificationId(int educationalQualificationIdValue) {

        this.educationalQualificationId = educationalQualificationIdValue;
    }
    
    /**
     * Get the professional qualification id for staff.
     * 
     * @return the professionalQualificationId.
     */
    public int getProfessionalQualificationId() {

        return professionalQualificationId;
    }
    
    /**
     * Set the professional qualification id for staff.
     * 
     * @param professionalQualificationIdValue the professionalQualificationId to set.
     */
    public void setProfessionalQualificationId(int professionalQualificationIdValue) {

        this.professionalQualificationId = professionalQualificationIdValue;
    }
    
    /**
     * Get the city id for staff.
     * 
     * @return the cityId.
     */
    public Integer getCityId() {

        return cityId;
    }
    
    /**
     * Set the city id for staff.
     * 
     * @param cityIdValue the cityId to set.
     */
    public void setCityId(Integer cityIdValue) {

        this.cityId = cityIdValue;
    }
    
    /**
     * Get TempCity id.
     * 
     * @return the tempCityId.
     */
    public Integer getTempCityId() {

        return tempCityId;
    }
    
    /**
     * Set TempCity id.
     * 
     * @param tempCityIdValue the tempCityId to set.
     */
    public void setTempCityId(Integer tempCityIdValue) {

        this.tempCityId = tempCityIdValue;
    }
    
    /**
     * Get TempAddress address.
     * 
     * @return the tempAddress.
     */
    public String getTempAddress() {

        return tempAddress;
    }
    
    /**
     * Set TempAddress address.
     * 
     * @param tempAddressValue the tempAddress to set.
     */
    public void setTempAddress(String tempAddressValue) {

        this.tempAddress = tempAddressValue;
    }
    
    /**
     * Get the Staff Category.
     * 
     * @return the staffCategory.
     */
    public StaffCategory getStaffCategory() {

        return staffCategory;
    }
    
    /**
     * Set the Staff Category.
     * 
     * @param staffCategoryValue the staffCategory to set.
     */
    public void setStaffCategory(StaffCategory staffCategoryValue) {

        this.staffCategory = staffCategoryValue;
    }
    
    /**
     * Get the Photo as a byte array.
     * 
     * @return the photo.
     */
    public byte[] getPhoto() {

        if (photo != null) {
            return (byte[]) photo.clone();
        }
        return null;
    }
    
    /**
     * Set the Photo as a byte array.
     * 
     * @param photoByteArray the photo to set.
     */
    public void setPhoto(byte[] photoByteArray) {

        if (photoByteArray != null) {
            this.photo = (byte[]) photoByteArray.clone();
        }
    }
    
    /**
     * Get the core subject of the staff.
     * 
     * @return core subject of the staff
     */
    public Subject getCoreSubject() {

        return coreSubject;
    }
    
    /**
     * Set the core subject.
     * 
     * @param coreSubjectValue core subject of the staff.
     */
    public void setCoreSubject(Subject coreSubjectValue) {

        this.coreSubject = coreSubjectValue;
    }
    
    /**
     * Get the study medium.
     * 
     * @return study medium.
     */
    public StudyMedium getStudyMedium() {

        return studyMedium;
    }
    
    /**
     * Set the study medium.
     * 
     * @param studyMediumValue StudyMedium object.
     */
    public void setStudyMedium(StudyMedium studyMediumValue) {

        this.studyMedium = studyMediumValue;
    }
    
    /**
     * Get the section.
     * 
     * @return section.
     */
    public Integer getSection() {

        return section;
    }
    
    /**
     * Set the section.
     * 
     * @param sectionObj section object.
     */
    public void setSection(Integer sectionObj) {

        this.section = sectionObj;
    }
    
    /**
     * Get the race.
     * 
     * @return race.
     */
    public Integer getRace() {

        return race;
    }
    
    /**
     * Set the race.
     * 
     * @param raceObj race object.
     */
    public void setRace(Integer raceObj) {

        this.race = raceObj;
    }
    
    /**
     * Get the civilStatus.
     * 
     * @return civilStatus.
     */
    public Integer getCivilStatus() {
        
        return civilStatus;
    }

    /**
     * Set the civilStatus.
     * 
     * @param civilStatusObj civilStatus object.
     */
    public void setCivilStatus(Integer civilStatusObj) {
    
        this.civilStatus = civilStatusObj;
    }
    
    /**
     * Gets the firstAppointmetDate.
     * 
     * @return firstAppointmetDate.
     */
    public Date getFirstAppointmetDate() {
        
        return firstAppointmetDate;
    }

    /**
     * Sets the firstAppointmetDate.
     * 
     * @param firstAppointmetDateObj firstAppointmetDate object.
     */
    public void setFirstAppointmetDate(Date firstAppointmetDateObj) {
    
        this.firstAppointmetDate = firstAppointmetDateObj;
    }
    
    /**
     * Get the appointmentNature.
     * 
     * @return appointmentNature.
     */
    public Integer getAppointmentNature() {
        
        return appointmentNature;
    }

    /**
     * Set the appointmentNature.
     * 
     * @param appointmentNatureObj appointmentNature object.
     */
    public void setAppointmentNature(Integer appointmentNatureObj) {
    
        this.appointmentNature = appointmentNatureObj;
    }
    
    /**
     * Get the appointmentClassification.
     * 
     * @return appointmentClassification.
     */
    public Integer getAppointmentClassification() {
        
        return appointmentClassification;
    }

    /**
     * Set the appointmentClassification.
     * 
     * @param appointmentClassificationObj appointmentClassification object.
     */
    public void setAppointmentClassification(Integer appointmentClassificationObj) {
    
        this.appointmentClassification = appointmentClassificationObj;
    }
    
    /**
     * Get the staffServiceCategory.
     * 
     * @return staffServiceCategory.
     */
    public Integer getStaffServiceCategory() {
        
        return staffServiceCategory;
    }

    /**
     * Set the staffServiceCategory.
     * 
     * @param staffServiceCategoryObj staffServiceCategory object.
     */    
    public void setStaffServiceCategory(Integer staffServiceCategoryObj) {
    
        this.staffServiceCategory = staffServiceCategoryObj;
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
     * Get the staffClass.
     * 
     * @return staffClass.
     */
    public String getStaffClass() {
        
        return staffClass;
    }

    /**
     * Set the staffClass.
     * 
     * @param strStaffClass staffClass object.
     */
    public void setStaffClass(String strStaffClass) {
    
        this.staffClass = strStaffClass;
    }

    /**
     * Get the staffGrade.
     * 
     * @return staffGrade.
     */
    public String getStaffGrade() {
    
        return staffGrade;
    }

    /**
     * Set the staffGrade.
     * 
     * @param strStaffGrade staffGrade object.
     */
    public void setStaffGrade(String strStaffGrade) {
    
        this.staffGrade = strStaffGrade;
    }
    
    /**
     * Get the staffState.
     * 
     * @return staffState - int.
     */
    public int getStaffStatus() {
    
        return staffStatus;
    }

    /**
     * Set the staffState.
     * 
     * @param staffState - int.
     */
    public void setStaffStatus(int staffState) {
    
        this.staffStatus = staffState;
    }

    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {

        return STAFF_FULL_NAME + fullName;
    }

    public void setSectionId(Integer sectionId) {

        this.sectionId = sectionId;
    }

    public Integer getSectionId() {

        return sectionId;
    }
    /**
     * Get the interest.
     * 
     * @return  interest.
     */
    public String getInterest() {
		return interest;
	}

    public void setInterest(String interest) {
		this.interest = interest;
	}

    /**
     * Get the insurancePolicyDetails.
     * 
     * @return  insurancePolicyDetails.
     */

	public String getInsurancePolicyDetails() {
		return insurancePolicyDetails;
	}
	/**
     * Set the insurancePolicyDetails.
     * 
     * @param strInsurancePolicyDetails the emergencyContactName to set.
     */
	public void setInsurancePolicyDetails(String strInsurancePolicyDetails) {
		this.insurancePolicyDetails = strInsurancePolicyDetails;
	}
	 /**
     * Get the emergencyContactName.
     * 
     * @return  emergencyContactName.
     */
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	/**
     * Set the emergencyContactName.
     * 
     * @param strEmergencyContactName the emergencyContactName to set.
     */
	public void setEmergencyContactName(String strEmergencyContactName) {
		this.emergencyContactName = strEmergencyContactName;
	}
	 /**
     * Get the emergencyContactResidenceNo.
     * 
     * @return  emergencyContactResidenceNo.
     */
	public String getEmergencyContactResidenceNo() {
		return emergencyContactResidenceNo;
	}
	
	/**
     * Set the emergencyContactResidenceNo.
     * 
     * @param strEmergencyContactResidenceNo the emergencyContactResidenceNo to set.
     */
	public void setEmergencyContactResidenceNo(String strEmergencyContactResidenceNo) {
		this.emergencyContactResidenceNo = strEmergencyContactResidenceNo;
	}


    /**
     * Get the emergencyContactMobileNo.
     * 
     * @return emergencyContactMobileNo.
     */
	public String getEmergencyContactMobileNo() {
		return emergencyContactMobileNo;
	}
	
	  /**
     * Set the emergencyContactMobileNo.
     * 
     * @param strEmergencyContactMobileNo the emergencyContactMobileNo to set.
     */
	public void setEmergencyContactMobileNo(String strEmergencyContactMobileNo) {
		this.emergencyContactMobileNo = strEmergencyContactMobileNo;
	}
    
}
