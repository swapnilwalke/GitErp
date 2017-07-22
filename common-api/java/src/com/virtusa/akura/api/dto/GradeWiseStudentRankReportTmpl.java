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
 * Collects user inputs to generate disciplinary actions report.
 * 
 * @author Virtusa Corporation
 */
public class GradeWiseStudentRankReportTmpl {
    
    /** Holds Grade object. */
    private Grade grade;
    
    /** Holds Strig value of current year. */
    private String year;
    
    /** Holds term object. */
    private Term term;
    
    /** Holds no of prizes giving. */
    private int noOfPrizes;
    
    /**
     * Getter method for grade.
     * 
     * @return grade
     */
    public Grade getGrade() {
    
        return grade;
    }
    
    /**
     * Setter method for grade.
     * 
     * @param vGrade grade object
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
     * @param vYear string
     */
    public void setYear(String vYear) {
    
        this.year = vYear;
    }
    
    /**
     * Getter method for term.
     * 
     * @return term Term object
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
     * Getter method for noOfPrizes.
     * 
     * @return noOfPrizes int
     */
    public int getNoOfPrizes() {
    
        return noOfPrizes;
    }
    
    /**
     * Setter method for noOfPrizes.
     * 
     * @param vNoOfPrizes int
     */
    public void setNoOfPrizes(int vNoOfPrizes) {
    
        this.noOfPrizes = vNoOfPrizes;
    }
    
}
