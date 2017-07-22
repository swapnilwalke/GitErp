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

/**
 * Description: StudentSearchCritiria uses as a DTO in student search.
 * 
 * @author Virtusa Corporation
 */
public class StudentSearchCritiria extends BaseDomain implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string prefectTypeId. */
    private static final String PREFECT_TYPE_ID = ", prefectTypeId=";
    
    /** key to hold string subjectId. */
    private static final String SUBJECT_ID = ", subjectId=";
    
    /** key to hold string sportId. */
    private static final String SPORT_ID = ", sportId=";
    
    /** key to hold string clazzId. */
    private static final String CLAZZ_ID = ", clazzId=";
    
    /** key to hold string startFrom. */
    private static final String START_FROM = ", startFrom=";
    
    /** key to hold string maxNumber. */
    private static final String MAX_NUMBER = ", maxNumber=";
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string grade. */
    private static final String GRADE = ", grade=";
    
    /** key to hold string admissionNumber. */
    private static final String ADMISSION_NUMBER = ", admissionNumber=";
    
    /** key to hold string lastName. */
    private static final String LAST_NAME = "lastName=";
    
    /**
     * last name of the student.
     */
    private String lastName;
    
    /**
     * Admission number of the student.
     */
    private String admissionNumber;
    
    /**
     * grade of the student.
     */
    private String grade;
    

	/**
     * studentStatusId of the student.
     */
	private int studentStatusId;
	

	/**
	 * @return the studentStatusId
	 */
	public int getStudentStatusId() {
		return studentStatusId;
	}

	/**
	 * @param studentStatusIdVal the studentStatusId to set
	 */
	public void setStudentStatusId(int studentStatusIdVal) {
		this.studentStatusId = studentStatusIdVal;
	}

	/**
     * year, grade belongs to which year.
     */
    private int year;
    
    /**
     * maximum number of records.
     */
    private int maxNumber;
    
    /**
     * starting row number.
     */
    private int startFrom;
    
    /** clazzId field to search the student. */
    private int clazzId;
    
    /** sportId field to search the student. */
    private int sportId;
    
    /** subjectId field to search the student. */
    private int subjectId;
    
    /** prefectTypeId field to search the student. */
    private int prefectTypeId;
    
    /** workingSegmentId field to search the student. */
    private int workingSegmentId;
    
    /**
     * Gets the Last Name of the Student.
     * 
     * @return the lastName.
     */
    public String getLastName() {
    
        return lastName;
    }
    
    /**
     * Sets the Last name of the student.
     * 
     * @param lstName to set the Last Name.
     */
    public void setLastName(String lstName) {
    
        this.lastName = lstName;
    }
    
    /**
     * Gets the Admission number of the Student.
     * 
     * @return the admissionNumber.
     */
    public String getAdmissionNumber() {
    
        return admissionNumber;
    }
    
    /**
     * Sets the admissionNumber.
     * 
     * @param admissionNo to set the Admission Number.
     */
    public void setAdmissionNumber(String admissionNo) {
    
        this.admissionNumber = admissionNo;
    }
    
    /**
     * Gets the Grade of the Student.
     * 
     * @return the grade.
     */
    public String getGrade() {
    
        return grade;
    }
    
    /**
     * Sets the Grade of the student.
     * 
     * @param stuGrade to set the Grade.
     */
    public void setGrade(String stuGrade) {
    
        this.grade = stuGrade;
    }
    
    /**
     * Gets the year.
     * 
     * @return the year.
     */
    public int getYear() {
    
        return year;
    }
    
    /**
     * Sets the year.
     * 
     * @param yearVal to set the year.
     */
    public void setYear(int yearVal) {
    
        this.year = yearVal;
    }
    
    /**
     * Gets the Maximum number.
     * 
     * @return the maxNumber.
     */
    public int getMaxNumber() {
    
        return maxNumber;
    }
    
    /**
     * Sets the Maximum number.
     * 
     * @param maximumNumber number of records to be displays.
     */
    public void setMaxNumber(int maximumNumber) {
    
        this.maxNumber = maximumNumber;
    }
    
    /**
     * Gets the Starting number.
     * 
     * @return the startFrom.
     */
    public int getStartFrom() {
    
        return startFrom;
    }
    
    /**
     * Sets the Starting number.
     * 
     * @param strtFrom starting row number for search.
     */
    public void setStartFrom(int strtFrom) {
    
        this.startFrom = strtFrom;
    }
    
    /**
     * Get the class grade id for the student to search.
     * 
     * @return the clazzId
     */
    public int getClazzId() {
    
        return clazzId;
    }
    
    /**
     * Set the class grade id for the student to search.
     * 
     * @param clazzIdValue the clazzId to set
     */
    public void setClazzId(int clazzIdValue) {
    
        this.clazzId = clazzIdValue;
    }
    
    /**
     * Get the sportId for the student to search.
     * 
     * @return the sportId
     */
    public int getSportId() {
    
        return sportId;
    }
    
    /**
     * Set the sportId for the student to search.
     * 
     * @param sportIdValue the sport to set
     */
    public void setSportId(int sportIdValue) {
    
        this.sportId = sportIdValue;
    }
    
    /**
     * Get the subject id for the student to search.
     * 
     * @return the subjectId
     */
    public int getSubjectId() {
    
        return subjectId;
    }
    
    /**
     * Set the subject id for the student to search.
     * 
     * @param subjectIdValue the subject to set
     */
    public void setSubjectId(int subjectIdValue) {
    
        this.subjectId = subjectIdValue;
    }
    
    /**
     * Get the prefectTypeId.
     * 
     * @return the prefectTypeId
     */
    public int getPrefectTypeId() {
    
        return prefectTypeId;
    }
    
    /**
     * Set the prefectTypeId.
     * 
     * @param prefectTypeIdValue the prefectTypeId to set
     */
    public void setPrefectTypeId(int prefectTypeIdValue) {
    
        this.prefectTypeId = prefectTypeIdValue;
    }
    
    /**
     * Get the workingSegmentId.
     * 
     * @return the workingSegmentId
     */
    public int getWorkingSegmentId() {
    
        return workingSegmentId;
    }

    /**
     * Set the workingSegmentId.
     * 
     * @param workingSegmentIdValue the workingSegmentId to set
     */
    public void setWorkingSegmentId(int workingSegmentIdValue) {
    
        this.workingSegmentId = workingSegmentIdValue;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return LAST_NAME + lastName + ADMISSION_NUMBER + admissionNumber + GRADE + grade + YEAR + year + MAX_NUMBER
                + maxNumber + START_FROM + startFrom + CLAZZ_ID + clazzId + SPORT_ID + sportId + SUBJECT_ID + subjectId
                + PREFECT_TYPE_ID + prefectTypeId;
    }
}