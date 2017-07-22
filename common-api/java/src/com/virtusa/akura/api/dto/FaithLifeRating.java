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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class is to associates all the properties of Faith Life Rating domain object.
 * 
 * @author Virtusa Corporation
 */
public class FaithLifeRating extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** attribute for holding string year. */
    private static final String YEAR = ", year=";
    
    /** attribute for holding string grading. */
    private static final String GRADING = ", grading=";
    
    /** attribute for holding string faithLifeRating id. */
    private static final String FAITH_LIFE_RATING_ID = "faithLifeRatingId=";
    
    /**
     * Holds faithLifeRatingId.
     */
    private int faithLifeRatingId;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Represents the FaithLifeComment of the student FaithLifeRating.
     */
    private FaithLifeComment faithLifeComment;
    
    /**
     * Represents the faithLifeDescription of the student FaithLifeRating.
     */
    private String faithLifeDescription;
    
    /**
     * Holds faithLifeValue.
     */
    private FaithLifeGrading faithLifeGrading;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * Default constructor to create FaithLifeRating object.
     */
    public FaithLifeRating() {
    
    }
    
    /**
     * gets the Id of the FaithLifeRating object.
     * 
     * @return the faithLifeRatingId
     */
    public int getFaithLifeRatingId() {
    
        return faithLifeRatingId;
    }
    
    /**
     * sets the Id of the FaithLifeRating.
     * 
     * @param intFaithLifeRatingId the faithLifeRatingId to set
     */
    public void setFaithLifeRatingId(int intFaithLifeRatingId) {
    
        this.faithLifeRatingId = intFaithLifeRatingId;
    }
    
    /**
     * gets the Student of the FaithLifeRating object.
     * 
     * @return the student
     */
    @JsonIgnore
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * sets the student object of the FaithLifeRating.
     * 
     * @param studentObj the student to set
     */
    @JsonIgnore
    public void setStudent(Student studentObj) {
    
        this.student = studentObj;
    }
    
    /**
     * gets the faithLifeComment of the FaithLifeRating object.
     * 
     * @return the faithLifeComment
     */
    public FaithLifeComment getFaithLifeComment() {
    
        return faithLifeComment;
    }
    
    /**
     * gets the faithLifeDescription of the FaithLifeRating object.
     * 
     * @return the faithLifeDescription
     */
    public String getFaithLifeDescription() {
    
        return faithLifeDescription;
    }
    
    /**
     * sets the faithLifeDescription of the FaithLifeRating.
     * 
     * @param strFaithLifeDescription - String to set
     */
    public void setFaithLifeDescription(String strFaithLifeDescription) {
    
        this.faithLifeDescription = strFaithLifeDescription;
    }
    
    /**
     * sets the faithLifeComment of the FaithLifeRating.
     * 
     * @param faithLifeCommentObj the faithLifeComment to set
     */
    public void setFaithLifeComment(FaithLifeComment faithLifeCommentObj) {
    
        this.faithLifeComment = faithLifeCommentObj;
    }
    
    /**
     * gets the grading of the FaithLifeRating object.
     * 
     * @return the grading
     */
    public FaithLifeGrading getFaithLifeGrading() {
    
        return faithLifeGrading;
    }
    
    /**
     * sets the grading object of the FaithLifeRating.
     * 
     * @param faithLifeGradingRef the Grading to set
     */
    
    public void setFaithLifeGrading(FaithLifeGrading faithLifeGradingRef) {
    
        this.faithLifeGrading = faithLifeGradingRef;
    }
    
    /**
     * gets the year of the FaithLifeRating object.
     * 
     * @return the year
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
        
    }
    
    /**
     * sets the year of the FaithLifeRating.
     * 
     * @param dateYear the year to set
     */
    public void setYear(Date dateYear) {
    
        if (dateYear != null) {
            this.year = (Date) dateYear.clone();
        }
        
    }
    
    
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return FAITH_LIFE_RATING_ID + faithLifeRatingId +  YEAR + year;
    }
    
}
