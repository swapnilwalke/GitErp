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
 * @author Virtusa Corporation
 */
public class SubjectAverageTermMarksId implements java.io.Serializable {
    
    /** Generated serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds gradeSubjectId. */
    private int gradeSubjectId;
    
    /** Holds term. */
    private String term;
    
    /** Holds year. */
    private Date year;
    
    /** Holds classDescription. */
    private String classDescription;

    /**
     * Getter method for gradeSubjectId.
     * 
     * @return grade subject id
     */
    public int getGradeSubjectId() {
    
        return gradeSubjectId;
    }

    /**
     * Setter method for gradeSubjectId.
     * 
     * @param vGradeSubjectId grade subject id
     */
    public void setGradeSubjectId(int vGradeSubjectId) {
    
        this.gradeSubjectId = vGradeSubjectId;
    }

    /**
     * Getter method for term.
     * 
     * @return term
     */
    public String getTerm() {
    
        return term;
    }

    /**
     * Setter method for term.
     * 
     * @param vTerm term
     */
    public void setTerm(String vTerm) {
    
        this.term = vTerm;
    }

    /**
     * Getter method for year.
     * 
     * @return Date object 
     */
    public Date getYear() {
    
        return year;
    }

    /**
     * Setter method for year.
     * 
     * @param vYear Date object
     */
    public void setYear(Date vYear) {
    
        this.year = vYear;
    }

    /**
     * Getter method for class description.
     * 
     * @return class description
     */
    public String getClassDescription() {
    
        return classDescription;
    }

    /**
     * Setter method for class description.
     * 
     * @param vClassDescription class description
     */
    public void setClassDescription(String vClassDescription) {
    
        this.classDescription = vClassDescription;
    }
  
}
