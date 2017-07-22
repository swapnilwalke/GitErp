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
 * Domain object to map data for Average Test Mark list.
 * 
 * @author Virtusa Corporation
 */

public class ReportAverageTestMark implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 676608671207584036L;
    
    /** The constant for String "ReportAverageTestMark classDescription=". */
    private static final String REPORT_AVERAGE_TEST_MARK_CLASS_DESCRIPTION = "ReportAverageTestMark classDescription=";
    
    /** The constant for String ", gradeDescription=". */
    private static final String GRADE_DESCRIPTION = ", gradeDescription=";
    
    /** The constant for String ", termDescription=". */
    private static final String TERM_DESCRIPTION = ", termDescription=";
    
    /** The constant for String ", average=". */
    private static final String AVERAGE = ", average=";
    
    /** The constant for String ", subjectDescription=". */
    private static final String SUBJECT_DESCRIPTION = ", subjectDescription=";
    
    /**
     * holds the name of the class.
     */
    private String classDescription;
    
    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;
    
    /**
     * holds the name of the term.
     */
    private String termDescription;
    
    /**
     * holds the name of the subject.
     */
    private String subjectDescription;
    
    /**
     * float variable to hold marks.
     */
    private double average;
    
    /**
     * @return classDescription Returns the name of the class.
     */
    public String getClassDescription() {
    
        return classDescription;
    }
    
    /**
     * @param classDes Sets the value for the name of the class.
     */
    public void setClassDescription(final String classDes) {
    
        this.classDescription = classDes;
    }
    
    /**
     * @return gradeDescription Returns the description of the grade.
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * @param gradeDes Sets the value for the description of the grade.
     */
    public void setGradeDescription(final String gradeDes) {
    
        this.gradeDescription = gradeDes;
    }
    
    /**
     * @return termDescription Returns the name of the term.
     */
    public String getTermDescription() {
    
        return termDescription;
    }
    
    /**
     * @param termDes Sets the value for the name of the term.
     */
    public void setTermDescription(final String termDes) {
    
        this.termDescription = termDes;
    }
    
    /**
     * @return subjectDescription Returns the name of the subject.
     */
    public String getSubjectDescription() {
    
        return subjectDescription;
    }
    
    /**
     * @param subjectDes Sets the value for the name of the subject.
     */
    public void setSubjectDescription(final String subjectDes) {
    
        this.subjectDescription = subjectDes;
    }
    
    /**
     * @return Returns the value of average.
     */
    public double getAverage() {
    
        return average;
    }
    
    /**
     * @param setAverage Sets the value for the value of average.
     */
    public void setAverage(final double setAverage) {
    
        this.average = setAverage;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return REPORT_AVERAGE_TEST_MARK_CLASS_DESCRIPTION + classDescription + GRADE_DESCRIPTION + gradeDescription
                + TERM_DESCRIPTION + termDescription + SUBJECT_DESCRIPTION + subjectDescription + AVERAGE + average;
    }
    
}
