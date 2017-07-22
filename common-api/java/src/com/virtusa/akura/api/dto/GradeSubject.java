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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all properties of Grade Subject domain object.
 *
 * @author Virtusa Corporation
 */
public class GradeSubject extends BaseDomain implements java.io.Serializable {


    /** attribute for holding string. */
    private static final String SUBJECT_CODE = ", subjectCode=";

    /** attribute for holding string. */
    private static final String GRADE = ", grade=";

    /** attribute for holding string. */
    private static final String SUBJECT = ", subject=";

    /** attribute for holding string. */
    private static final String GRADE_SUBJECT_ID = "gradeSubjectId=";
    
    /** attribute for holding string. */
    private static final String MAXIMUM_MARKS = " maximumMark= ";
    

    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;

    /**
     * property gradeSubjectId type int.
     */
    private int gradeSubjectId;

    /**
     * property subject {@link Subject}.
     */
    private Subject subject;

    /**
     * property grade {@link Grade}.
     */
    private Grade grade;

    /**
     * property subjectCode type String.
     */
    private String subjectCode;
    
    /** The property to check optional subject. */
    private boolean isOptionalSubject;
    
    /** The property to maximum mark. */
    private int maximumMark;

    /**
     * Default constructor.
     */
    public GradeSubject() {

    }

    /**
     * Constructor with one parameters.
     *
     * @param intGradeSubjectId type int
     */
    public GradeSubject(int intGradeSubjectId) {

        this.gradeSubjectId = intGradeSubjectId;

    }

    /**
     * Constructor with parameters.
     *
     * @param intGradeSubjectId type int
     * @param objSubject {@link Subject}
     * @param objGrade {@link Grade}
     * @param strSubjectCode type String
     * @param dtModifiedTime type Date
     */
    public GradeSubject(int intGradeSubjectId, Subject objSubject, Grade objGrade, String strSubjectCode,
            Date dtModifiedTime) {

        this.gradeSubjectId = intGradeSubjectId;
        this.subject = objSubject;
        this.grade = objGrade;
        this.subjectCode = strSubjectCode;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {

        return subject;
    }

    /**
     * @param objSubject the subject to set
     */
    public void setSubject(Subject objSubject) {

        this.subject = objSubject;
    }

    /**
     * @return the grade
     */
    @JsonIgnore
    public Grade getGrade() {

        return grade;
    }

    /**
     * @param objGrade the grade to set
     */
    @JsonIgnore
    public void setGrade(Grade objGrade) {

        this.grade = objGrade;
    }

    /**
     * @return the subjectCode
     */
    public String getSubjectCode() {

        return subjectCode;
    }

    /**      
     * @param strSubjectCode the subjectCode to set
     */
    public void setSubjectCode(String strSubjectCode) {

        this.subjectCode = strSubjectCode;
    }

    /**
     * @return the gradeSubjectId
     */
    public int getGradeSubjectId() {

        return gradeSubjectId;
    }

    /**
     * @param intGradeSubjectId the gradeSubjectId to set
     */
    public void setGradeSubjectId(int intGradeSubjectId) {

        this.gradeSubjectId = intGradeSubjectId;
    }


    
    /**
     * Gets the checks if is optional subject.
     *
     * @return the isOptionalSubject
     */
    public boolean getIsOptionalSubject() {
    
        return isOptionalSubject;
    }

    
    /**
     * Sets the optional subject.
     *
     * @param isOptionalSubjectValue the isOptionalSubject to set
     */
    public void setIsOptionalSubject(boolean isOptionalSubjectValue) {
    
        this.isOptionalSubject = isOptionalSubjectValue;
    }
    
    /**
     * Gets the maximum mark of grade subject.
     * 
     *  @return the maximumMark
     * */
    public int getMaximumMark(){
        return maximumMark;           
    }
    
    /** 
     * Sets the maximum_Mark for grade Subject.
     * 
     * @param maximumMarks to maximumMark for grade subject
     */
    public void setMaximumMark(int maximumMarks){
        this.maximumMark = maximumMarks;
    }
    
    
    /**
     * Returns a string representation of the object.
     *
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return GRADE_SUBJECT_ID + gradeSubjectId + SUBJECT + subject + GRADE + grade
                + SUBJECT_CODE + subjectCode + MAXIMUM_MARKS + maximumMark ;
    }

}
