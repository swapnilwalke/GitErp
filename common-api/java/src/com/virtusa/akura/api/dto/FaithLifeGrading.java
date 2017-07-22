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
public class FaithLifeGrading extends BaseDomain {
    
    /** attribute for holding string. */
    private static final String GRADE_ACRONYM = ", gradeAcronym=";
    
    /** attribute for holding string. */
    private static final String VALUE = ", value=";
    
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
    private int value;
    
    /**
     * property gradeAcronym type String.
     */
    private String description;
    
    /**
     * Default constructor.
     */
    public FaithLifeGrading() {
    
    }
    
    /**
     * Constructor with parameter gradingId.
     * 
     * @param intGradingId type integer
     */
    public FaithLifeGrading(int intGradingId) {
    
        this.gradingId = intGradingId;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * @param gradeAcronymValue the description to set
     */
    public void setDescription(String gradeAcronymValue) {
    
        this.description = gradeAcronymValue;
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
     * @return the value
     */
    
    public int getValue() {
    
        return value;
    }
    
    /**
     * @param valueRef the value to set
     */
    public void setValue(int valueRef) {
    
        this.value = valueRef;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return GRADING_ID + gradingId + VALUE + value + GRADE_ACRONYM;
    }
}
