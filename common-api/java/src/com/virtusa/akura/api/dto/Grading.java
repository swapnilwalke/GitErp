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


/**
 * This class represents all properties of Grading domain object.
 * 
 * @author Virtusa Corporation
 */
public class Grading extends BaseDomain {
    
    /** attribute for holding string. */
    private static final String GRADE_ACRONYM = ", gradeAcronym=";
    
    /** attribute for holding string. */
    private static final String DESCRIPTION = ", description=";
    
    /** attribute for holding string. */
    private static final String GRADING_ID = "gradingId=";
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property gradingId type integer.
     */
    private int gradingId;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * property gradeAcronym type String.
     */
    private String gradeAcronym;
    
    /**
     * Default constructor.
     */
    public Grading() {
    
    }
    
    /**
     * Constructor with parameter gradingId.
     * 
     * @param intGradingId type integer
     */
    public Grading(int intGradingId) {
    
        this.gradingId = intGradingId;
    }
    
    /**
     * @return the gradeAcronym
     */
    public String getGradeAcronym() {
    
        return gradeAcronym;
    }
    
    /**
     * @param gradeAcronymValue the gradeAcronym to set
     */
    public void setGradeAcronym(String gradeAcronymValue) {
    
        this.gradeAcronym = gradeAcronymValue;
    }
    
    /**
     * @return the gradingId
     */
    public int getGradingId() {
    
        return gradingId;
    }
    
    /**
     * @param intGradingId the gradingId to set
     */
    public void setGradingId(int intGradingId) {
    
        this.gradingId = intGradingId;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return GRADING_ID + gradingId + DESCRIPTION + description + GRADE_ACRONYM;
    }
}
