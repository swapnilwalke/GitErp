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
 * This class is to associates properties of ClassGrade.
 *
 * @author Virtusa Corporation
 *
 */
public class ClassGrade extends BaseDomain {

    /** String attribute for grade. */
    private static final String GRADE = ", grade=";

    /** String attribute for school class. */
    private static final String SCHOOL_CLASS = ", schoolClass=";

    /** String attribute for description. */
    private static final String DESCRIPTION_VALUE = ", description=";

    /** String attribute for class grade id. */
    private static final String CLASS_GRADE_ID = "ClassGrade [classGradeId=";

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * property classGradeId type int.
	 */
	private int classGradeId;

	/**
	 * property description type String.
	 */
	private String description;

	/**
	 * property schoolClass {@link SchoolClass}.
	 */
    private SchoolClass schoolClass;

    /**
	 * property grade {@link Grade}.
	 */
    private Grade grade;

    /**
     * Default Constructor.
     */
    public ClassGrade() {
    }

	/**
	 * Constructor with parameters.
	 *
	 * @param intClassGradeId type int
	 * @param objSchoolClass {@link SchoolClass}
	 * @param objGrade {@link Grade}
	 */
    public ClassGrade(int intClassGradeId, SchoolClass objSchoolClass, Grade objGrade) {
        this.classGradeId = intClassGradeId;
        this.schoolClass = objSchoolClass;
        this.grade = objGrade;
        }

	/**
	 * @return the schoolClass
	 */
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}

	/**
	 * @param objSchoolClass the schoolClass to set
	 */
	public void setSchoolClass(SchoolClass objSchoolClass) {
		this.schoolClass = objSchoolClass;
	}

	/**
	 * @return the grade
	 */
	public Grade getGrade() {
		return grade;
	}

	/**
	 * @param objGrade the grade to set
	 */
	public void setGrade(Grade objGrade) {
		this.grade = objGrade;
	}

	/**
	 * @return the classGradeId
	 */
	public int getClassGradeId() {
		return classGradeId;
	}

	/**
	 * @param intClassGradeId the classGradeId to set
	 */
	public void setClassGradeId(int intClassGradeId) {
		this.classGradeId = intClassGradeId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param descriptionValue the description to set
	 */
	public void setDescription(String descriptionValue) {
		this.description = descriptionValue;
	}

	/**
     * Returns the details for the ClassGrade object.
     * @return - the ClassGrade object details.
     */
    @Override
    public String toString() {

        return CLASS_GRADE_ID + classGradeId + DESCRIPTION_VALUE + description + SCHOOL_CLASS
                + schoolClass.getClassId() + GRADE + grade.getGradeId() ;
    }

}


