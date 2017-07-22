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
 * @author Virtusa Corporation
 */
public class BestStudentAttendanceTemplate extends BaseDomain implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** attribute of the academic days. */
    private int academicDays;

    /** attribute of the student admission number. */
    private String studentID;

    /** attribute of the student name. */
    private String studentName;

    /** attribute of the student present days. */
    private int presentDays;

    /** attribute of the current year. */
    private int currentYear;

    /** attribute of the grade id. */
    private int gradeId;

    /** attribute of the class id. */
    private int classId;

    /** attribute of the grade description. */
    private String gradeDescription;

    /** attribute of the class description. */
    private String classDescription;

    /** attribute of the month. */
    private String monthDescription;

    /** attribute of the month id. */
    private int monthId;

    /** attribute of the year. */
    private int year;
    
    /** byte attribute for holding photo. */
    private byte[] photo;
    
    /** attribute of the imagePath. */
    private String imagePath;

    /**
     * @return the grade
     */

    public int getGradeId() {

        return gradeId;
    }

    /**
     * Sets the year.
     *
     * @param gradeIdRef the year to set
     */
    public void setGradeId(int gradeIdRef) {

        this.gradeId = gradeIdRef;
    }

    /**
     * @return the class
     */

    public int getClassId() {

        return classId;
    }

    /**
     * Sets the year.
     *
     * @param classIdRef the class to set
     */
    public void setClassId(int classIdRef) {

        this.classId = classIdRef;
    }

    /**
     * @return the year
     */
    public int getCurrentYear() {

        return currentYear;
    }

    /**
     * Sets the year.
     *
     * @param currentYearRef the year to set
     */
    public void setCurrentYear(int currentYearRef) {

        this.currentYear = currentYearRef;
    }

    /**
     * @return the present days
     */
    public int getPresentDays() {

        return presentDays;
    }

    /**
     * Sets the present days.
     *
     * @param presentDaysRef the present days to set
     */
    public void setPresentDays(int presentDaysRef) {

        this.presentDays = presentDaysRef;
    }

    /**
     * @return the student name
     */
    public String getStudentName() {

        return studentName;
    }

    /**
     * Sets the student name.
     *
     * @param studentNameRef the student name to set
     */
    public void setStudentName(String studentNameRef) {

        this.studentName = studentNameRef;
    }

    /**
     * @return the class name
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * Sets the class.
     *
     * @param classDescriptionRef the class to set
     */
    public void setClassDescription(String classDescriptionRef) {

        this.classDescription = classDescriptionRef;
    }

    /**
     * @return the student admission number
     */
    public String getStudentID() {

        return studentID;
    }

    /**
     * Sets the student id.
     *
     * @param studentIDRef the student id to set
     */
    public void setStudentID(String studentIDRef) {

        this.studentID = studentIDRef;
    }

    /**
     * @return the month id
     */
    public int getMonthId() {

        return monthId;
    }

    /**
     * Sets the month id.
     *
     * @param monthIdRef the month to set
     */
    public void setMonthId(int monthIdRef) {

        this.monthId = monthIdRef;
    }

    /**
     * @return the month description
     */
    public String getMonthDescription() {

        return monthDescription;
    }

    /**
     * Sets the month name.
     *
     * @param monthDescriptionRef the month description to set
     */
    public void setMonthDescription(String monthDescriptionRef) {

        this.monthDescription = monthDescriptionRef;
    }

    /**
     * @return the grade description
     */
    public String getGradeDescription() {

        return gradeDescription;
    }

    /**
     * Sets the grade name.
     *
     * @param gradeDescriptionRef the grade to set
     */
    public void setGradeDescription(String gradeDescriptionRef) {

        this.gradeDescription = gradeDescriptionRef;
    }

    /**
     * @return the year
     */
    public int getYear() {

        return year;
    }

    /**
     * Sets the year.
     *
     * @param yearRef the year to set
     */
    public void setYear(int yearRef) {

        this.year = yearRef;
    }

    /**
     * @return the academic days.
     */
    public int getAcademicDays() {

        return academicDays;
    }

    /**
     * Sets the academic days.
     *
     * @param academicDaysRef the academic days to set
     */
    public void setAcademicDays(int academicDaysRef) {

        this.academicDays = academicDaysRef;
    }

	/**
	 * @return the photo
	 */
	public byte[] getPhoto() {
		if (photo != null) {
			return (byte[]) photo.clone();
		}
		return null;
	}

	/**
	 * @param bPhoto
	 *            the photo to set
	 */
	public void setPhoto(byte[] bPhoto) {
		if (bPhoto != null) {
			this.photo = (byte[]) bPhoto.clone();
		}
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePathRef the imagePath to set
	 */
	public void setImagePath(String imagePathRef) {
		this.imagePath = imagePathRef;
	}

	
}
