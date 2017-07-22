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
 * This Class use to get inputs and store data for generate class wise StudentMarkSheet.
 * 
 * @author Virtusa Corporation
 */
public class StudentMarkSheetTemplate implements java.io.Serializable {
    
    /** serialVersionUID - final. */
    private static final long serialVersionUID = 1L;
    
    /** Holds Grade object. */
    private Grade grade;
    
    /** Holds ClassGrade object. */
    private ClassGrade classGrade;
    
    /** Holds Strig value of current year. */
    private String year;
    
    /** Holds term object. */
    private Term term;
    
    /** represent whether result is sorted by name or rank .*/
    private String sortedBy;
    
    /**
     * Getter method for grade.
     * 
     * @return Grade object
     */
    public Grade getGrade() {
    
        return grade;
    }
    
    /**
     * Setter method for grade.
     * 
     * @param vGrade Grade object
     */
    public void setGrade(Grade vGrade) {
    
        this.grade = vGrade;
    }
    
    /**
     * Getter method for year.
     * 
     * @return year
     */
    public String getYear() {
    
        return year;
    }
    
    /**
     * Setter method for year.
     * 
     * @param vYear String value
     */
    public void setYear(String vYear) {
    
        this.year = vYear;
    }
    
    /**
     * Getter method for class grade.
     * 
     * @return ClassGrade object
     */
    public ClassGrade getClassGrade() {
    
        return classGrade;
    }
    
    /**
     * Setter method for class grade.
     * 
     * @param vClassGrade ClassGrade object
     */
    public void setClassGrade(ClassGrade vClassGrade) {
    
        this.classGrade = vClassGrade;
    }
    
    /**
     * Getter method for term.
     * 
     * @return Term object
     */
    public Term getTerm() {
    
        return term;
    }
    
    /**
     * Setter method for term.
     * 
     * @param vTerm Term object
     */
    public void setTerm(Term vTerm) {
    
        this.term = vTerm;
    }

    /**
     * Getter method for sortedBY property.
     * 
     * @return string value
     */
    public String getSortedBy() {
    
        return sortedBy;
    }

    /**
     * Setter method for sortedBy.
     * 
     * @param vSortedBy pass either rank or name 
     */
    public void setSortedBy(String vSortedBy) {
    
        this.sortedBy = vSortedBy;
    }
}
