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
 * Collects user inputs to generate student performance report.
 *
 * @author Virtusa Corporation
 */

public class CRStudentPerformanceTemplate {

    /** String attribute for to GRADE_DESCRIPTION. */
    private static final String GRADE_DESCRIPTION = ", gradeDescription=";

    /** String attribute for to CLASS_DESCRIPTION. */
    private static final String CLASS_DESCRIPTION = "CRStudentPerformanceTemplate [classDescription=";

    /**
     * holds the name of the class.
     */
    private String classDescription;

    /**
     * holds the name of the term.
     */
    private String term;

    /**
     * @return term Returns the name of the term.
     */
    public String getTerm() {

        return term;
    }

    /**
     * @param termVal Sets the value for the name of the term.
     */
    public void setTerm(String termVal) {

        this.term = termVal;
    }

    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;

    /**
     * holds the name of the class.
     */
    private String classDescriptionLess;

    /**
     * @return classDescriptionLess Returns the name of the class.
     */
    public String getClassDescriptionLess() {

        return classDescriptionLess;
    }

    /**
     * @param classDescriptionLessRef Sets the value for the name of the class.
     */
    public void setClassDescriptionLess(String classDescriptionLessRef) {

        this.classDescriptionLess = classDescriptionLessRef;
    }

    /**
     * @return gradeDescriptionLess Returns the description of the grade.
     */
    public String getGradeDescriptionLess() {

        return gradeDescriptionLess;
    }

    /**
     * @param gradeDescriptionLessRef Sets the value for the description of the grade.
     */
    public void setGradeDescriptionLess(String gradeDescriptionLessRef) {

        this.gradeDescriptionLess = gradeDescriptionLessRef;
    }

    /**
     * @return gradeDescriptionGreater Returns the description of the grade.
     */
    public String getGradeDescriptionGreater() {

        return gradeDescriptionGreater;
    }

    /**
     * @param gradeDescriptionGreaterRef Sets the value for the description of the grade.
     */
    public void setGradeDescriptionGreater(String gradeDescriptionGreaterRef) {

        this.gradeDescriptionGreater = gradeDescriptionGreaterRef;
    }

    /**
     * holds the year description of the grade.
     */
    private String gradeDescriptionLess;

    /**
     * holds the name of the class.
     */
    private String classDescriptionGreater;

    /**
     * @return classDescriptionGreater Returns the name of the class.
     */
    public String getClassDescriptionGreater() {

        return classDescriptionGreater;
    }

    /**
     * @param classDescriptionGreaterRef Sets the value for the name of the class.
     */
    public void setClassDescriptionGreater(String classDescriptionGreaterRef) {

        this.classDescriptionGreater = classDescriptionGreaterRef;
    }

    /**
     * holds the year description of the grade.
     */
    private String gradeDescriptionGreater;

    /**
     * holds the value for less than condition for marks.
     */
    private float lessThan;

    /**
     * holds the value for greater than condition for marks.
     */
    private float greaterThan;

    /**
     * holds the less value for inBetween condition for marks.
     */
    private float inBetweenLessValue;

    /**
     * holds the greater value for inBetween condition for marks.
     */
    private float inBetweenGreaterValue;

    /**
     * @return classDescription Returns the name of the class.
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * @param classDes Sets the value for the name of the class.
     */
    public void setClassDescription(final String classDes) {

        this.classDescription = classDes;
    }

    /**
     * @return gradeDescription Returns the description of the grade.
     */
    public String getGradeDescription() {

        return gradeDescription;
    }

    /**
     * @param gradeDes Sets the value for the description of the grade.
     */
    public void setGradeDescription(final String gradeDes) {

        this.gradeDescription = gradeDes;
    }

    /**
     * @return lessThan Returns the value for less than condition for marks.
     */
    public float getLessThan() {

        return lessThan;
    }

    /**
     * @param lessThanVal Sets the value for less than condition for marks.
     */
    public void setLessThan(float lessThanVal) {

        this.lessThan = lessThanVal;
    }

    /**
     * @return greaterThan Returns the value for greater than condition for marks.
     */
    public float getGreaterThan() {

        return greaterThan;
    }

    /**
     * @param greaterThanVal Sets the value for greater than condition for marks.
     */
    public void setGreaterThan(float greaterThanVal) {

        this.greaterThan = greaterThanVal;
    }

    /**
     * @return inBetweenLessValue Returns the value for less value for inBetween condition for marks.
     */
    public float getInBetweenLessValue() {

        return inBetweenLessValue;
    }

    /**
     * @param inBetweenLessVal Sets the value for less value for inBetween condition for marks.
     */
    public void setInBetweenLessValue(float inBetweenLessVal) {

        this.inBetweenLessValue = inBetweenLessVal;
    }

    /**
     * @return inBetweenGreaterValue Returns the value for greater value for inBetween condition condition for
     *         marks.
     */
    public float getInBetweenGreaterValue() {

        return inBetweenGreaterValue;
    }

    /**
     * @param inBetweenGreaterVal Sets the value for greater value for inBetween condition for marks.
     */
    public void setInBetweenGreaterValue(float inBetweenGreaterVal) {

        this.inBetweenGreaterValue = inBetweenGreaterVal;
    }

    /**
     * Returns the details for the CRStudentPerformanceTemplate object.
     *
     * @return - the CRStudentPerformanceTemplate object details.
     */
    @Override
    public String toString() {

        return CLASS_DESCRIPTION + classDescription + GRADE_DESCRIPTION + gradeDescription;
    }

}
