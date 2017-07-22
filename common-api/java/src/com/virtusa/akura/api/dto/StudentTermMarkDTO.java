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
import java.util.Date;

import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * The DTO class to holds the data for Student Marks view.
 *
 * @author Virtusa Corporation
 */
public class StudentTermMarkDTO extends BaseDomain implements Serializable {

    /** String attribute for to MARKS. */
    private static final String MARKS_VALUE = ", marks=";

    /** String attribute for to TERM_ID. */
    private static final String TERM_ID = ", termId=";

    /** Holds serialisation ID. */
    private static final long serialVersionUID = 1L;

    /** Holds the marks. */
    private float marks;

    /** Holds the term description. */
    private String term;

    /** Holds the subject description. */
    private String subject;

    /** Holds the student id. */
    private int studentId;

    /** Holds the year. */
    private Date year;

    /** Holds the student term mark id. */
    private int studentTermMarkId;

    /** Holds the absent. */
    private boolean absent;
    
    /** Holds the admissionNo. */
    private String admissionNo;
    
    /** Holds the nameWithInitials. */
    private String nameWithInitials;
    
    /** Holds the classGradeId. */
    private int classGradeId;
    
    /** Holds the studentClassInfoId. */
    private int studentClassInfoId;
    
    /** Holds the termId. */
    private int termId;

    /** Holds the gradeSubjectId. */
    private int gradeSubjectId;
    
    /** Holds the language id.*/
    private int languageId;
    
    /** Represents the edit mark status for the term. */
    private boolean editMarkState;
    
    /**
     * Retrieves the edit mark status for the term.
     * 
     * @return - the edit mark status for the term.
     */
    public boolean getEditMarkState() {
    
        return editMarkState;
    }
   
    /**
     * Sets the mark status of the edit completion.
     * 
     * @param bEditMarkState - the edit mark status for the term.
     */
    public void setEditMarkState(boolean bEditMarkState) {
    
        this.editMarkState = bEditMarkState;
    }

    /**
     * Gets the absent status.
     *
     * @return the absent status.
     */
    public boolean isAbsent() {

        return absent;
    }

    /**
     * Default constructor.
     */
    public StudentTermMarkDTO() {

    }

    /**
     * Sets the absent status.
     *
     * @param isabsent the absent status
     */
    public void setAbsent(boolean isabsent) {

        this.absent = isabsent;
    }

    /**
     * Gets the term marks id.
     *
     * @return the teram mark id.
     */
    public int getStudentTermMarkId() {

        return studentTermMarkId;
    }

    /**
     * Sets the term mark id.
     *
     * @param id the term mark id.
     */
    public void setStudentTermMarkId(int id) {

        this.studentTermMarkId = id;
    }

    /**
     * Gets the students id.
     *
     * @return student id.
     */
    public int getStudentId() {

        return studentId;
    }

    /**
     * Sets the student id.
     *
     * @param id the student id.
     */
    public void setStudentId(int id) {

        this.studentId = id;
    }

    /**
     * Gets the year.
     *
     * @return the year.
     */
    public Date getYear() {

        if (year != null) {
            return (Date) year.clone();
        }

        return null;
    }

    /**
     * Sets the year.
     *
     * @param yearValue the year.
     */
    public void setYear(Date yearValue) {

        if (yearValue != null) {

            this.year = (Date) yearValue.clone();
        }
    }

    /**
     * Gets the marks.
     *
     * @return the marks.
     */
    public float getMarks() {

        return marks;
    }

    /**
     * Sets the marks.
     *
     * @param marksVlaue the marks.
     */
    public void setMarks(float marksVlaue) {

        this.marks = marksVlaue;
    }

    /**
     * Gets the term.
     *
     * @return the term.
     */
    public String getTerm() {

        return term;
    }

    /**
     * Sets the term.
     *
     * @param termValue the term.
     */
    public void setTerm(String termValue) {

        this.term = termValue;
    }

    /**
     * Gets the subject.
     *
     * @return the subject.
     */
    public String getSubject() {

        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subjectValue the subject.
     */
    public void setSubject(String subjectValue) {

        this.subject = subjectValue;
    }
    
    /**
     * Gets the admission no.
     * 
     * @return admission no
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }

    /**
     * Sets the admission no.
     * 
     * @param vAdmissionNo admission no
     */
    public void setAdmissionNo(String vAdmissionNo) {
    
        this.admissionNo = vAdmissionNo;
    }

    /**
     * Getter method for nameWithInitials.
     * 
     * @return name with initials
     */
    public String getNameWithInitials() {
    
        return nameWithInitials;
    }

    /**
     * Setter method for nameWithInitials.
     * 
     * @param vNameWithInitials name with initials
     */
    public void setNameWithInitials(String vNameWithInitials) {
    
        this.nameWithInitials = vNameWithInitials;
    }
    
    /**
     * Getter method for student class info id.
     * 
     * @return student class info id
     */
    public int getStudentClassInfoId() {
    
        return studentClassInfoId;
    }

    /**
     * Setter method for student class info id.
     * 
     * @param vStudentClassInfoId student class info id
     */
    public void setStudentClassInfoId(int vStudentClassInfoId) {
    
        this.studentClassInfoId = vStudentClassInfoId;
    }
    
    /**
     * Getter method for class grade id.
     * 
     * @return class grade id
     */
    public int getClassGradeId() {
    
        return classGradeId;
    }
   
    /**
     * Setter method for class grade id.
     * 
     * @param vClassGradeId class grade id
     */
    public void setClassGradeId(int vClassGradeId) {
    
        this.classGradeId = vClassGradeId;
    }

    /**
     * Getter method for term id.
     * 
     * @return term id
     */
    public int getTermId() {
    
        return termId;
    }

    /**
     * Setter method for term id.
     * 
     * @param vTermId term id 
     */
    public void setTermId(int vTermId) {
    
        this.termId = vTermId;
    }
    
    /**
     * Getter method for grade subject id.
     * 
     * @return grade subject id.
     */
    public int getGradeSubjectId() {
    
        return gradeSubjectId;
    }
    
    /**
     * Setter method for gradeSubjectId.
     *  
     * @param vGradeSubjectId grade subject id
     */
    public void setGradeSubjectId(int vGradeSubjectId) {
    
        this.gradeSubjectId = vGradeSubjectId;
    }

    /**
     * Getter method for language id.
     * 
     * @return language id.
     */
    public int getLanguageId() {
    
        return languageId;
    }

    /**
     * Setter method for languageId.
     *  
     * @param vLanguageId language id
     */
    public void setLanguageId(int vLanguageId) {
    
        this.languageId = vLanguageId;
    }

    /**
     * The Constructor of the class.
     *
     * @param marksVlaue the marks.
     * @param termValue the term.
     * @param subjectValues the subject.
     * @param studentIdVal the student id.
     * @param yearVal the year.
     * @param studentTermMarkIdVal student mark id.
     * @param isabsent the absent status.
     */
    public StudentTermMarkDTO(float marksVlaue, String termValue, String subjectValues, int studentIdVal, Date yearVal,
            int studentTermMarkIdVal, boolean isabsent) {

        super();
        this.marks = marksVlaue;
        this.term = termValue;
        this.subject = subjectValues;
        this.studentId = studentIdVal;
        if (yearVal != null) {
            this.year = (Date) yearVal.clone();
        }
        this.studentTermMarkId = studentTermMarkIdVal;
        this.absent = isabsent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return AkuraConstant.SUBJECT + subject + TERM_ID + term + MARKS_VALUE + marks;
    }

}
