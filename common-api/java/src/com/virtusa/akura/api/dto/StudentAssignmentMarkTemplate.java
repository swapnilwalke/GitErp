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
 * The Class StudentAssignmentMark.
 */
public class StudentAssignmentMarkTemplate implements Serializable {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The assignment id. */
    private String assignment;
    
    /** The assignment id. */
    private String subject;
    
    /** The grading id. */
    private String gradingAcronym;
    
    /** The marks. */
    private Float marks;
    
    /** The year. */
    private Date year;
    
    /**
     * Gets the year.
     * 
     * @return the year
     */
    public Date getYear() {

        return year;
    }
    
    /**
     * Sets the year.
     * 
     * @param dtYear the year to set
     */
    public void setYear(Date dtYear) {

        this.year = dtYear;
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
    public void setMarks(final Float flMarks) {

        this.marks = flMarks;
    }
    
    /**
     * @return the assignment
     */
    public String getAssignment() {

        return assignment;
    }
    
    /**
     * Sets the assignment.
     * 
     * @param strAssignment the assignment to set
     */
    public void setAssignment(String strAssignment) {

        this.assignment = strAssignment;
    }
    
    /**
     * Gets the subject.
     * 
     * @return the subject
     */
    public String getSubject() {

        return subject;
    }
    
    /**
     * Sets the subject.
     * 
     * @param strSubject the subject to set
     */
    public void setSubject(String strSubject) {

        this.subject = strSubject;
    }
    
    /**
     * Gets the grading acronym.
     * 
     * @return the gradingAcronym
     */
    public String getGradingAcronym() {

        return gradingAcronym;
    }
    
    /**
     * Sets the grading acronym.
     * 
     * @param strGradingAcronym the gradingAcronym to set
     */
    public void setGradingAcronym(String strGradingAcronym) {

        this.gradingAcronym = strGradingAcronym;
    }
    
}
