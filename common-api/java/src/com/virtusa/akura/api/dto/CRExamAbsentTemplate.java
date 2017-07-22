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
 * dto for data transfer between JSP to controller for Exam absent list report.
 * 
 * @author Virtusa Corporation
 */

public class CRExamAbsentTemplate {
    
    /** String attribute for to grade description. */
    private static final String GRADE_DESCRIPTION = ", gradeDescription=";
    
    /** String attribute for to class description. */
    private static final String CLASS_DESCRIPTION = "CRExamAbsentTemplate [classDescription=";
    
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
     * Holds absence.
     */
    private boolean absent;
    
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
     * @return Returns student absent or not.
     */
    public boolean isAbsent() {
    
        return absent;
    }
    
    /**
     * @param isAbsent Sets student absent or not.
     */
    public void setAbsent(final boolean isAbsent) {
    
        this.absent = isAbsent;
    }
    
    /**
     * Returns the details for the CRExamAbsentTemplate object.
     * 
     * @return - the CRExamAbsentTemplate object details.
     */
    @Override
    public String toString() {
    
        return CLASS_DESCRIPTION + classDescription + GRADE_DESCRIPTION + gradeDescription;
    }
    
}
