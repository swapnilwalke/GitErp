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

import java.util.List;

/**
 * dto for data transfer between JSP to controller for Average Test Mark report.
 * 
 * @author Virtusa Corporation
 */

public class CRAverageTestMarkTemplate {
    
    /** String attribute for to grade description. */
    private static final String GRADE_DESCRIPTION = ", gradeDescription=";
    
    /** String attribute for to grade id. */
    private static final String GRADE_ID = ", gradeId=";
    
    /** String attribute for to class description. */
    private static final String CLASS_DESCRIPTION = "CRAverageTestMarkTemplate-->classDescription=";
    
    /**
     * holds the name of the class.
     */
    private String classDescription;
    
    /**
     * Property gradeId type int.
     */
    private int gradeId;
    
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
     * holds the list of subjects.
     */
    private List<Integer> subjectList;
    
    /**
     * holds the list of terms.
     */
    private List<Term> termList;
    
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
     * @return the gradeId
     */
    public int getGradeId() {
    
        return gradeId;
    }
    
    /**
     * @param intGradeId the gradeId to set
     */
    public void setGradeId(int intGradeId) {
    
        this.gradeId = intGradeId;
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
     * @return subjectList Returns list of subjects.
     */
    public List<Integer> getSubjectList() {
    
        return subjectList;
    }
    
    /**
     * @param subList Sets the values for the list of subjects.
     */
    public void setSubjectList(List<Integer> subList) {
    
        this.subjectList = subList;
    }
    
    /**
     * @return termList Returns list of terms.
     */
    public List<Term> getTermList() {
    
        return termList;
    }
    
    /**
     * @param terList Sets the values for the list of terms.
     */
    public void setTermList(List<Term> terList) {
    
        this.termList = terList;
    }
    
    /**
     * Returns the details for the CRAverageTestMarkTemplate object.
     * 
     * @return - the CRAverageTestMarkTemplate object details.
     */
    @Override
    public String toString() {
    
        return CLASS_DESCRIPTION + classDescription + GRADE_ID + gradeId + GRADE_DESCRIPTION + gradeDescription;
    }
    
}
