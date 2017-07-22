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
 * StudentTermMark class entity that holds a single student subject mark per term.
 */
public class StudentTermMark extends AuditableBaseDomain implements Serializable {
    
    /** String attribute for to MARKS. */
    private static final String MARKS_VALUE = ", marks=";
    
    /** String attribute for to TERM_ID. */
    private static final String TERM_ID = ", termId=";
    
    /** String attribute for to MERIT_AWARD. */
    private static final String MERIT_AWARD = ", meritAward=";
    
    /** String attribute for to STUDENT_CLASS_INFO. */
    private static final String STUDENT_CLASS_INFO_ID = ", studentClassInfoId=";
    
    /** String attribute for to GRADE_SUBJECT_ID. */
    private static final String GRADE_SUBJECT_ID = ", gradeSubjectId=";
    
    /** String attribute for to STUDENT_TERM_MARK_ID. */
    private static final String STUDENT_TERM_MARK_ID = "StudentTermMark [studentTermMarkId=";
    
    /** The constant for String ", editMarkState=". */
    private static final String EDIT_MARK_STATE = ", editMarkState=";
    
    /**
     * Holds serialisation ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds studentTermMarkId.
     */
    private int studentTermMarkId;
    
    /**
     * Holds gradeSubject.
     */
    private int gradeSubjectId;
    
    /**
     * Holds studentClassInfo.
     */
    private int studentClassInfoId;
    
    /**
     * Holds meritAward.
     */
    private MeritAward meritAward;
    
    /**
     * Holds term id.
     */
    private int termId;
    
    /**
     * integer variable to hold term Id.
     */
    private float marks;
    
    /**
     * float variable to hold marks per subject.
     */
    private String comments;
    
    /**
     * Holds absence.
     */
    private boolean absent;
    
    /**
     * boolean variable to holds state of editMarks.
     */
    private boolean editMarkState;
    
    /**
     * Default constructor.
     */
    public StudentTermMark() {
    
    }
    
    /**
     * Overloaded constructor to accept all arguments.
     * 
     * @param intStudentTermMarkId {@link int}
     * @param intGradeSubject {@link GradeSubject}
     * @param intStudentClassInfo {@link StudentClassInfo}
     * @param objMeritAward {@link MeritAward}
     * @param intTerm {@link int}
     * @param boolAbsent type boolean
     * @param editState type boolean
     */
    public StudentTermMark(int intStudentTermMarkId, int intGradeSubject, int intStudentClassInfo,
            MeritAward objMeritAward, int intTerm, boolean boolAbsent, boolean editState) {
    
        this.studentTermMarkId = intStudentTermMarkId;
        this.gradeSubjectId = intGradeSubject;
        this.studentClassInfoId = intStudentClassInfo;
        this.meritAward = objMeritAward;
        this.termId = intTerm;
        this.absent = boolAbsent;
        this.editMarkState = editState;
    }
    
    /**
     * Overloaded constructor to accept just student term mark Id.
     * 
     * @param intStudentTermMarkId {@link int}
     */
    public StudentTermMark(int intStudentTermMarkId) {
    
        this.studentTermMarkId = intStudentTermMarkId;
        
    }
    
    /**
     * @return the marks
     */
    public float getMarks() {
    
        return marks;
    }
    
    /**
     * @param floatMarks the marks to set
     */
    public void setMarks(final float floatMarks) {
    
        this.marks = floatMarks;
    }
    
    /**
     * @return comments
     */
    public String getComments() {
    
        return this.comments;
    }
    
    /**
     * @param strComments the comments to set
     */
    public void setComments(final String strComments) {
    
        this.comments = strComments;
    }
    
    /**
     * @return the gradeSubject
     */
    public int getGradeSubjectId() {
    
        return this.gradeSubjectId;
    }
    
    /**
     * @param intGradeSubjectId the gradeSubject to set
     */
    public void setGradeSubjectId(int intGradeSubjectId) {
    
        this.gradeSubjectId = intGradeSubjectId;
    }
    
    /**
     * @return the studentClassInfo
     */
    public int getStudentClassInfoId() {
    
        return this.studentClassInfoId;
    }
    
    /**
     * @param intStudentClassInfoId the studentClassInfo to set
     */
    public void setStudentClassInfoId(int intStudentClassInfoId) {
    
        this.studentClassInfoId = intStudentClassInfoId;
    }
    
    /**
     * @return the meritAward
     */
    public MeritAward getMeritAward() {
    
        return this.meritAward;
    }
    
    /**
     * @param objMeritAward the meritAward to set
     */
    public void setMeritAward(MeritAward objMeritAward) {
    
        this.meritAward = objMeritAward;
    }
    
    /**
     * @return the term
     */
    public int getTermId() {
    
        return this.termId;
    }
    
    /**
     * @param intTermId the term to set
     */
    public void setTermId(int intTermId) {
    
        this.termId = intTermId;
    }
    
    /**
     * @return the studentTermMarkId
     */
    public int getStudentTermMarkId() {
    
        return this.studentTermMarkId;
    }
    
    /**
     * @param intStudentTermMarkId the studentTermMarkId to set
     */
    public void setStudentTermMarkId(int intStudentTermMarkId) {
    
        this.studentTermMarkId = intStudentTermMarkId;
    }
    
    /**
     * @return whether absent or not as boolean
     */
    public boolean isAbsent() {
    
        return this.absent;
    }
    
    /**
     * @param boolAbsent the boolean value to set
     */
    public void setAbsent(boolean boolAbsent) {
    
        this.absent = boolAbsent;
    }
    
    /**
     * @param editState is the boolean value to set
     */
    public void seteditMarkState(boolean editState) {
    
        this.editMarkState = editState;
    }
    
    /**
     * @return whether Marks edit or not as boolean
     */
    public boolean geteditMarkState() {
    
        return this.editMarkState;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
   @Override
    public String toString() {
    
        return STUDENT_TERM_MARK_ID + studentTermMarkId + GRADE_SUBJECT_ID + gradeSubjectId + STUDENT_CLASS_INFO_ID
                + studentClassInfoId + MERIT_AWARD + meritAward + TERM_ID + termId + MARKS_VALUE + marks
                + EDIT_MARK_STATE + editMarkState;
    }
    
   /**
    * {@inheritDoc}
    */
    @Override
    public String auditToString() {
    
        return "StudentTermMark [gradeSubjectId=" + gradeSubjectId + ", studentClassInfoId=" + studentClassInfoId
                + ", meritAward=" + meritAward + ", termId=" + termId + ", marks=" + marks + ", comments=" + comments
                + ", absent=" + absent + ", editMarkState=" + editMarkState + "]";
    }

}
