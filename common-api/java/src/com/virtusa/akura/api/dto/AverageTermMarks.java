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
 * Domain object to map data for student discipline actions.
 * 
 * @author Virtusa Corporation
 */

public class AverageTermMarks extends BaseDomain implements Serializable{
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 8074837892865681737L;
    
    /**
     * descriptive text for grade.
     */
    private String gradeDescription;
    
    /**
     * descriptive text for class.
     */
    private String classDescription;
    
    /**
     * descriptive text for subject.
     */
    private String subject;
    
    /**
     * descriptive text for term.
     */
    private String term;
    
    /**
     * Date type year.
     */
    private Date year;
    
    /**
     * integer for totMarks.
     */
    private int totMarks;
    
    /** Constructs a new AverageTermMarks object. */
    public AverageTermMarks() {

    } 
    
    /**
     * gets the GradeDescription.
     * 
     * @return GradeDescription in String.
     */
    public String getGradeDescription() {

        return gradeDescription;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setGradeDescription(String gradeDescription) {

        this.gradeDescription = gradeDescription;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    public String getClassDescription() {

        return classDescription;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setClassDescription(String classDescription) {

        this.classDescription = classDescription;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    public String getSubject() {

        return subject;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setSubject(String subject) {

        this.subject = subject;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    public String getTerm() {

        return term;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setTerm(String term) {

        this.term = term;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    public Date getYear() {

        return year;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setYear(Date year) {

        this.year = year;
    }
    
    /**
     * gets the Id of the sport object.
     * 
     * @return id of the sport in integer.
     */
    public int getTotMarks() {

        return totMarks;
    }
    
    /**
     * sets the Id of the sport.
     * 
     * @param intId the sportId in integer.
     */
    public void setTotMarks(int totMarks) {

        this.totMarks = totMarks;
    }
    
}
