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
 * This class is to use to hold the inputs to generate subject prize list report.
 * 
 * @author Virtusa Corporation
 */
public class PrizeListReportTemplate {
    
    /** Holds Grade objects. */
    private Grade grade;
    
    /** Holds GradeSubject object. */
    private GradeSubject gradeSubject;
    
    /** Holds year. */
    private String year;
    
    /** Holds no of prizes. */
    private int noOfPrizes;
    
    /**
     * Getter method for Grade.
     * 
     * @return grade.
     */
    public Grade getGrade() {
    
        return grade;
    }
    
    /**
     * Setter method for Grade.
     * 
     * @param vGrade Grade object
     */
    public void setGrade(Grade vGrade) {
    
        this.grade = vGrade;
    }
    
    /**
     * Getter method for Grade.
     * 
     * @return grade.
     */
    public GradeSubject getGradeSubject() {
    
        return gradeSubject;
    }
    
    /**
     * Setter method for Grade.
     * 
     * @param vGradeSubject GradeSubject object
     */
    public void setGradeSubject(GradeSubject vGradeSubject) {
    
        this.gradeSubject = vGradeSubject;
    }
    
    /**
     * Getter method for year.
     * 
     * @return year.
     */
    public String getYear() {
    
        return year;
    }
    
    /**
     * Setter method for Grade.
     * 
     * @param vYear year
     */
    public void setYear(String vYear) {
    
        this.year = vYear;
    }

    /**
     * Getter method for noOfPrizes.
     * 
     * @return no of prizes
     */
    public int getNoOfPrizes() {
    
        return noOfPrizes;
    }

    /**
     * Setter method for noOfPrizes.
     * 
     * @param vNoOfPrizes no of prizes
     */
    public void setNoOfPrizes(int vNoOfPrizes) {
    
        this.noOfPrizes = vNoOfPrizes;
    }
}
