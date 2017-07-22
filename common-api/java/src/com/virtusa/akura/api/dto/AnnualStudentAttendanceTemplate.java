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
 * The Class AnnualStudentAttendanceTemplate.
 * 
 * @author Virtusa Corporation
 */
public class AnnualStudentAttendanceTemplate implements Serializable{

	/**
     * Holds serial version id.
     */
	private static final long serialVersionUID = 3127090224158626213L;

	/** studentId of the student. */
	private int studentId;

	/** represents the admissionNo of the student. */
	private String admissionNo;

	/** represents the fullName of the student. */
	private String fullName;
	
	/** represents the Name With Initials of the student. */
	private String nameWithInitials;

	/** current year. */
	private String year;

	/** count of present attendance of current year. */
	private int presentCount;

	/** The class grade id. */
	private int classGradeId;

	/** The class grade name. */
	private String className;
	
	/** The percentage of Annual attendance. */
	private String attendancePercentage;

	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * @param studentIdVal the studentId to set
	 */
	public void setStudentId(int studentIdVal) {
		this.studentId = studentIdVal;
	}

	/**
	 * @return the admissionNo
	 */
	public String getAdmissionNo() {
		return admissionNo;
	}

	/**
	 * @param admissionNoVal the admissionNo to set
	 */
	public void setAdmissionNo(String admissionNoVal) {
		this.admissionNo = admissionNoVal;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullNameVal the fullName to set
	 */
	public void setFullName(String fullNameVal) {
		this.fullName = fullNameVal;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param yearVal the year to set
	 */
	public void setYear(String yearVal) {
		this.year = yearVal;
	}

	/**
	 * @return the presentCount
	 */
	public int getPresentCount() {
		return presentCount;
	}

	/**
	 * @param presentCountVal the presentCount to set
	 */
	public void setPresentCount(int presentCountVal) {
		this.presentCount = presentCountVal;
	}

	/**
	 * @return the attendancePercentage
	 */
	public String getAttendancePercentage() {
		return attendancePercentage;
	}

	/**
	 * @param attendPercentageValue the attendancePercentage to set
	 */
	public void setAttendancePercentage(String attendPercentageValue) {
		this.attendancePercentage = attendPercentageValue;
	}

	/**
	 * @return the classGradeId
	 */
	public int getClassGradeId() {
		return classGradeId;
	}

	/**
	 * @param classGradeIdValue
	 *            the classGradeId to set
	 */
	public void setClassGradeId(int classGradeIdValue) {
		this.classGradeId = classGradeIdValue;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param classNameValue
	 *            the className to set
	 */
	public void setClassName(String classNameValue) {
		this.className = classNameValue;
	}
	
	/**
	 * @return the Name with initials of the student.
	 */
	public String getNameWithInitials() {
		return nameWithInitials;
	}

	/**
	 * 
	 * @param nameWithInitial - Name with initials to set.
	 */
	public void setNameWithInitials(String nameWithInitial) {
		this.nameWithInitials = nameWithInitial;
	}

}
