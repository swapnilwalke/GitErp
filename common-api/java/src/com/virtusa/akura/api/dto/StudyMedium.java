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
 * This class is to associates all the properties of StudyMedium domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudyMedium extends BaseDomain implements Serializable {
    
    /** The Constant to "]" use in toString(). */
    private static final String STRING = "]";
    
    /** The Constant STUDY_MEDIUM_NAME use in toString(). */
    private static final String STUDY_MEDIUM_NAME = ", studyMediumName=";
    
    /** The Constant STUDY_MEDIUM_STUDY_MEDIUM_ID use in toString(). */
    private static final String STUDY_MEDIUM_STUDY_MEDIUM_ID = "StudyMedium [studyMediumId=";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds study medium Id.
     */
    private Integer studyMediumId;
    
    /**
     * Holds study medium Id.
     */
    private String studyMediumName;
    
    /**
     * Get the study medium id.
     * 
     * @return the study medium Id
     */
    public Integer getStudyMediumId() {
    
        return studyMediumId;
    }
    
    /**
     * Set the study medium id.
     * 
     * @param studyMediumIdValue studyMediumId the studyMediumId to set
     */
    public void setStudyMediumId(Integer studyMediumIdValue) {
    
        this.studyMediumId = studyMediumIdValue;
    }
    
    /**
     * Get study medium name.
     * 
     * @return the study medium name
     */
    public String getStudyMediumName() {
    
        return studyMediumName;
    }
    
    /**
     * Set study medium name.
     * 
     * @param studyMediumNameValue the studyMediumName to set.
     */
    public void setStudyMediumName(String studyMediumNameValue) {
    
        this.studyMediumName = studyMediumNameValue;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDY_MEDIUM_STUDY_MEDIUM_ID + studyMediumId + STUDY_MEDIUM_NAME + studyMediumName + STRING;
    }
    
}
