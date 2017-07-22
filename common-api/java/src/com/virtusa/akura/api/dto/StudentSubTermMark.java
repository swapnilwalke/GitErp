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

/**
 * @author Virtusa Corporation
 */

public class StudentSubTermMark extends AuditableBaseDomain implements Serializable {
    
    /**
     * set serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string absent. */
    private static final String ABSENT = ", absent=";
    
    /** key to hold string gradingID. */
    private static final String GRADING_ID = ", gradingID=";
    
    /** key to hold string subtermID. */
    private static final String SUBTERM_ID = ", subtermID=";
    
    /** key to hold string stuTermMarkID. */
    private static final String STU_TERM_MARK_ID = ", stuTermMarkID=";
    
    /** key to hold string stuSubTermMarkId. */
    private static final String STU_SUB_TERM_MARK_ID = "stuSubTermMarkId=";
    
    /** The constant for String ", editMarkState=". */
    private static final String EDIT_MARK_STATE = ", editMarkState=";
    
    /**
     * Holds studentSubTermMark.
     */
    private int stuSubTermMarkId;
    
    /**
     * Holds gradeSubject.
     */
    private int stuTermMarkID;
    
    /**
     * Holds sub term.
     */
    private int subtermID;
    
    /**
     * Holds grading.
     */
    private Integer gradingID;
    
    /**
     * Boolean to track absentee.
     */
    private boolean absent;
    
    /** The marks. */
    private Float marks;
    
    /**
     * boolean variable to holds state of editMarks.
     */
    private boolean editMarkState;
    
    /**
     * Default constructor.
     */
    public StudentSubTermMark() {
    
    }
    
    /**
     * @param intStuSubTermID - set StudentSubTermMarkId.
     * @param intStuTermMark - set StudentTermMark.
     * @param intSubTerm - set subTerm.
     * @param intGradingID - set Grading.
     * @param boolAbsent - Check absent.
     * @param dateModifiedTime - set StudentSubTermMarkId.
     */
    public StudentSubTermMark(int intStuSubTermID, int intStuTermMark, int intSubTerm, int intGradingID,
            boolean boolAbsent, Date dateModifiedTime) {
    
        this.stuSubTermMarkId = intStuSubTermID;
        this.stuTermMarkID = intStuTermMark;
        this.subtermID = intSubTerm;
        this.gradingID = intGradingID;
        this.absent = boolAbsent;
    }
    
    /**
     * @return the studentSubTermMarkId
     */
    public int getStuSubTermMarkId() {
    
        return stuSubTermMarkId;
    }
    
    /**
     * @param intStudentSubTermMarkId the studentSubTermMarkId to set
     */
    public void setStuSubTermMarkId(int intStudentSubTermMarkId) {
    
        this.stuSubTermMarkId = intStudentSubTermMarkId;
    }
    
    /**
     * @return the studentTermMark
     */
    public int getStuTermMarkID() {
    
        return stuTermMarkID;
    }
    
    /**
     * @param intStudentTermMark the studentTermMark to set
     */
    public void setStuTermMarkID(int intStudentTermMark) {
    
        this.stuTermMarkID = intStudentTermMark;
    }
    
    /**
     * @return the subterm
     */
    public int getSubtermID() {
    
        return subtermID;
    }
    
    /**
     * @param intSubterm the subterm to set
     */
    public void setSubtermID(int intSubterm) {
    
        this.subtermID = intSubterm;
    }
    
    /**
     * @return the grading
     */
    public Integer getGradingID() {
    
        return gradingID;
    }
    
    /**
     * @param intGradingID the grading to set
     */
    public void setGradingID(Integer intGradingID) {
    
        this.gradingID = intGradingID;
    }
    
    /**
     * @return the absent
     */
    public boolean isAbsent() {
    
        return absent;
    }
    
    /**
     * @param boolAbsent the absent to set
     */
    public void setAbsent(boolean boolAbsent) {
    
        this.absent = boolAbsent;
    }
    
    /**
     * Gets the marks.
     * 
     * @return the marks
     */
    public Float getMarks() {
    
        return marks;
    }
    
    /**
     * Sets the marks.
     * 
     * @param flMarks the marks to set
     */
    public void setMarks(Float flMarks) {
    
        this.marks = flMarks;
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
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STU_SUB_TERM_MARK_ID + stuSubTermMarkId + STU_TERM_MARK_ID + stuTermMarkID + SUBTERM_ID + subtermID
                + GRADING_ID + gradingID + ABSENT + absent + EDIT_MARK_STATE + editMarkState;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {
    
        return "StudentSubTermMark [stuTermMarkID=" + stuTermMarkID + ", subtermID=" + subtermID + ", gradingID="
                + gradingID + ", absent=" + absent + ", marks=" + marks + ", editMarkState=" + editMarkState + "]";
    }
    
}
