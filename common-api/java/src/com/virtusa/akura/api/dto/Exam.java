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
 * This class is to associates properties of Exam.
 * 
 * @author Virtusa Corporation
 */
public class Exam extends BaseDomain implements Serializable {
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = ", description=";
    
    /** attribute for holding string. */
    private static final String EXAM_ID = "examId=";
    
    /** The constant for String ", mark=". */
    private static final String MARK = ", mark=";
    
    /** The Constant for String "GradeId=". */
    private static final String GRADE_ID = "gradeId";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds exam id.
     */
    
    private int examId;
    
    /** Represents the key of the grade. */
    private int gradeId;
    
    /** Holds description. */
    private String description;
    
    /**
     * boolean to hold if the mark is mark.
     */
    private boolean mark;
    
    /** Default constructor. */
    public Exam() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intExamId type int
     * @param strDescription type string
     * @param checkMark type boolean
     * @param intgradeId type intgradeId
     */
    public Exam(int intExamId, String strDescription, boolean checkMark, int intgradeId) {
    
        this.examId = intExamId;
        this.description = strDescription;
        this.mark = checkMark;
        this.gradeId = intgradeId;
    }
    
    /**
     * Get the Exam.
     * 
     * @return the TypeId
     */
    public int getExamId() {
    
        return examId;
    }
    
    /**
     * Sets the exams.
     * 
     * @param intExamId the examId to set
     */
    public void setExamId(int intExamId) {
    
        this.examId = intExamId;
    }
    
    /**
     * Get the description of the Exam.
     * 
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Set the Description of the Exam.
     * 
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Get Mark as default Mark Type .
     * 
     * @return the mark.
     */
    public boolean getMark() {    
        return mark;
    }
    
    /**
     * Sets Mark .
     * 
     * @param checkMark if mark then true.
     */
    public void setMark(boolean checkMark) {
    
        this.mark = checkMark;
    }
    
    /**
     * Returns the key of the grade.
     * 
     * @return - the key of the grade.
     */
    public int getGradeId() {
    
        return gradeId;
    }
    
    /**
     * Sets the key of the grade.
     * 
     * @param intGradeId - key of the grade.
     */
    public void setGradeId(final int intGradeId) {
    
        this.gradeId = intGradeId;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return EXAM_ID + examId + DESCRIPTION + description + MARK + mark + GRADE_ID + gradeId;
    }
    
}
