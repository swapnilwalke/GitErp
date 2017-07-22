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
 * This class represents all the properties of a term marks for student summary report report.
 * 
 * @author Virtusa Corporation
 */
public class StudentTermMarksTemplate implements Serializable {
    
    /**
     * Default Serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string term. */
    private static final String TERM = ", term=";
    
    /** key to hold string subjectName. */
    private static final String SUBJECT_NAME = ", subjectName=";
    
    /** key to hold string marks. */
    private static final String MARKS = "marks=";
    
    /**
     * Represents the marks.
     */
    private float marks;
    
    /**
     * Represents the name of a Subject.
     */
    private String subjectName;
    
    /**
     * Represents the term or the subTerm of the School.
     */
    private String term;
    
    /**
     * Returns the marks of the student for a term or a subTerm.
     * 
     * @return - marks of a student object
     */
    public float getMarks() {
    
        return marks;
    }
    
    /**
     * Sets the marks of a student object.
     * 
     * @param flMarks - marks of the student
     */
    public void setMarks(float flMarks) {
    
        this.marks = flMarks;
    }
    
    /**
     * Returns the name of the subject.
     * 
     * @return - the subject name
     */
    public String getSubjectName() {
    
        return subjectName;
    }
    
    /**
     * Sets the subject name.
     * 
     * @param strSubjectName - name of the subject
     */
    public void setSubjectName(String strSubjectName) {
    
        this.subjectName = strSubjectName;
    }
    
    /**
     * Returns a term or a subTerm of the school.
     * 
     * @return - a Term or a subTerm of the school
     */
    public String getTerm() {
    
        return term;
    }
    
    /**
     * Sets the term or a subTerm of the school.
     * 
     * @param strTerm - the term or the SubTerm of the School
     */
    public void setTerm(String strTerm) {
    
        this.term = strTerm;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return MARKS + marks + SUBJECT_NAME + subjectName + TERM + term;
    }
    
}
