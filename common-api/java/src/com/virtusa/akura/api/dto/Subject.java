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
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all the properties of a Subject domain object.
 * 
 * @author Virtusa Corporation
 */
public class Subject extends BaseDomain implements Serializable {
    
    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** String attribute for to DESCRIPTION. */
    private static final String DESCRIPTION_VALUE = " description = ";
    
    /** String attribute for to SUBJECT_ID. */
    private static final String SUBJECT_ID = "subject id = ";
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Represents the id for a Subject.
     */
    private Integer subjectId;
    
    /**
     * Represents the description for a Subject.
     */
    private String description;
    
    /**
     * Represents the Stream for a Subject.
     */
    private Stream stream;
    
    /**
     * Property to hold code for subject.
     * Maximum length of subject code is 5.
     */
    private String subjectCode;
    
    /**
     * Property to hold code for subject given by the government.
     * Maximum length of subject code is 5.
     */
    private String govSubjectCode;
    
    /**
     * Represents a set of GradeSubjects for a Subject.
     */
    private Set<GradeSubject> gradeSubjects = new HashSet<GradeSubject>();
    
    /**
     * Constructs a new Subject object.
     */
    public Subject() {
    
    }
    
    /**
     * Returns the subject id.
     * 
     * @return - the id of the subject.
     */
    public final Integer getSubjectId() {
    
        return subjectId;
    }
    
    /**
     * Sets the id for this Subject object with the given key.
     * 
     * @param intSubjectId - the id of the Subject.
     */
    public final void setSubjectId(final Integer intSubjectId) {
    
        this.subjectId = intSubjectId;
    }
    
    /**
     * Returns the description of a Subject.
     * 
     * @return the description of the Subject.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description for this Subject object with the given information.
     * 
     * @param strDescription - description of the Subject.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns a Stream object of this Subject.
     * 
     * @return - the stream of the Subject
     */
    public final Stream getStream() {
    
        return stream;
    }
    
    /**
     * Sets the Stream of this Subject object.
     * 
     * @param objStream - the stream of the Subject
     */
    public final void setStream(final Stream objStream) {
    
        this.stream = objStream;
    }
    
    /**
     * Returns a set of GradeSubjects of the Subject.
     * 
     * @return - the gradeSubjects of the Subject object.
     */
    @JsonIgnore
    public final Set<GradeSubject> getGradeSubjects() {
    
        return gradeSubjects;
    }
    
    /**
     * Sets a set of GradeSubjects for this Subject object.
     * 
     * @param collGradeSubjects - the gradeSubjects of the Subject
     */
   @JsonIgnore
    public final void setGradeSubjects(final Set<GradeSubject> collGradeSubjects) {
    
        this.gradeSubjects = collGradeSubjects;
    }
    
    /**
     * Getter method to get subject code.
     * 
     * @return string value
     */
    public String getSubjectCode() {
    
        return subjectCode;
    }

    /**
     * Setter method to set code for subject.
     * 
     * @param vSubjectCode string value
     */
    public void setSubjectCode(String vSubjectCode) {
    
        this.subjectCode = vSubjectCode;
    }

    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return SUBJECT_ID + this.subjectId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
    
  
    /**
     * Getter method to get subject code.
     * 
     * @return string value
     */
    
    public String getGovSubjectCode() {
		return govSubjectCode;
	}

    /**
     * Setter method to set code for subject.
     * 
     * @param  vGovSubjectCode string value
     */
    public void setGovSubjectCode(String vGovSubjectCode) {
		this.govSubjectCode = vGovSubjectCode;
	}
}
