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

/**
 * The Class StudentSubTermMarkDTO.
 * 
 * @author Virtusa Corporation.
 */
public class StudentSubTermMarkDTO extends BaseDomain {
    
    /** Serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds the absent. */
    private boolean absent;
    
    /** Holds the marks. */
    private Float marks;
    
    /** Holds the studentSubTermMarkId. */
    private int studentSubTermMarkId;
    
    /** Holds the student id. */
    private int studentId;
    
    /** Holds the grading id. */
    private Integer gradingId;
    
    /** Holds the term description. */
    private String term;
    
    /** Holds the sub term description. */
    private String subTerm;
    
    /** Holds the subject description. */
    private String subject;
    
    /** Holds the year. */
    private Date year;
    
    /**
     * Checks if is absent.
     * 
     * @return the absent
     */
    public boolean isAbsent() {

        return absent;
    }
    
    /**
     * Sets the absent.
     * 
     * @param boolAbsent the absent to set
     */
    public void setAbsent(final boolean boolAbsent) {

        this.absent = boolAbsent;
    }
    
    /**
     * Gets the student id.
     * 
     * @return the studentId
     */
    public int getStudentId() {

        return studentId;
    }
    
    /**
     * Sets the student id.
     * 
     * @param intStudentId the studentId to set
     */
    public void setStudentId(final int intStudentId) {

        this.studentId = intStudentId;
    }
    
    /**
     * @return the gradingId
     */
    public Integer getGradingId() {

        return gradingId;
    }
    
    /**
     * Sets the grading id.
     * 
     * @param intGradingId the gradingId to set
     */
    public void setGradingId(Integer intGradingId) {

        this.gradingId = intGradingId;
    }
    
    /**
     * Gets the term.
     * 
     * @return the term
     */
    public String getTerm() {

        return term;
    }
    
    /**
     * Sets the term.
     * 
     * @param strTerm the term to set
     */
    public void setTerm(String strTerm) {

        this.term = strTerm;
    }
    
    /**
     * @return the subTerm
     */
    public String getSubTerm() {

        return subTerm;
    }
    
    /**
     * Sets the sub term.
     * 
     * @param strSubTerm the subTerm to set
     */
    public void setSubTerm(String strSubTerm) {

        this.subTerm = strSubTerm;
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
     * @param datYear the year to set
     */
    public void setYear(Date datYear) {

        this.year = datYear;
    }
    
    /**
     * Gets the student sub term mark id.
     * 
     * @return the studentSubTermMarkId
     */
    public int getStudentSubTermMarkId() {

        return studentSubTermMarkId;
    }
    
    /**
     * Sets the student sub term mark id.
     * 
     * @param intStudentSubTermMarkId the studentSubTermMarkId to set
     */
    public void setStudentSubTermMarkId(final int intStudentSubTermMarkId) {

        this.studentSubTermMarkId = intStudentSubTermMarkId;
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
    
}
