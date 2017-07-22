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
import java.util.Date;

/**
 * This class represents all properties of Donation domain object.
 * 
 * @author Virtusa Corporation
 */
public class MediumWiseClassSubjectAverageView extends BaseDomain implements Serializable {
    
    /** Generated serial id. */
    private static final long serialVersionUID = 8291099135026141617L;
    
    /** Holds grade description property. */
    private String gradeDescription;
    
    /** Holds class description property. */ 
    private String classDescription;
    
    /** Holds class grade id property. */
    private int classGradeId;
    
    /** Holds year property. */ 
    private Date year;
    
    /** Holds grade subject id property. */
    private int gradeSubjectId;
    
    /** Holds subject property. */
    private String subject;
    
    /** Holds term property. */
    private String term;
    
    /** Holds language id property. */
    private int languageId;
    
    /** Holds totalMarks property. */
    private int totalMarks;
    
    /** Holds average studentCount. */
    private int studentCount;
    
    /** Holds average property. */
    private double average;
    
    /**
     * Getter method to get grade description.
     * 
     * @return grade description.
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * Setter method to set grade description.
     * 
     * @param vGradeDescription grade description
     */
    public void setGradeDescription(String vGradeDescription) {
    
        this.gradeDescription = vGradeDescription;
    }
    
    /**
     * Getter method for class description.
     * 
     * @return class description.
     */
    public String getClassDescription() {
    
        return classDescription;
    }
    
    /**
     * Setter method for class description.
     * 
     * @param vClassDescription class description.
     */
    public void setClassDescription(String vClassDescription) {
    
        this.classDescription = vClassDescription;
    }
    
    /**
     * Getter method to get class grade id.
     * 
     * @return class grade id.
     */
    public int getClassGradeId() {
    
        return classGradeId;
    }

    /**
     * Setter method to set class grade id.
     * 
     * @param classGradeId class grade id
     */
    public void setClassGradeId(int classGradeId) {
    
        this.classGradeId = classGradeId;
    }

    /**
     * Getter method to get year.
     * 
     * @return year
     */
    public Date getYear() {
    
        return year;
    }
    
    /**
     * Setter method to set year.
     * 
     * @param vYear year
     */
    public void setYear(Date vYear) {
    
        this.year = vYear;
    }
    
    /**
     * Getter method to get grade subject id.
     * 
     * @return grade subject id.
     */
    public int getGradeSubjectId() {
    
        return gradeSubjectId;
    }
    
    /**
     * Setter method to set grade subject id.
     * 
     * @param vGradeSubjectId grade subject id.
     */
    public void setGradeSubjectId(int vGradeSubjectId) {
    
        this.gradeSubjectId = vGradeSubjectId;
    }
    
    /**
     * Getter method to get subject.
     * 
     * @return subject
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * Setter method to set subject.
     * 
     * @param vSubject subject
     */
    public void setSubject(String vSubject) {
    
        this.subject = vSubject;
    }
    
    /**
     * Getter method to get term.
     * 
     * @return term
     */
    public String getTerm() {
    
        return term;
    }
    
    /**
     * Setter method to set term.
     * 
     * @param vTerm term
     */
    public void setTerm(String vTerm) {
    
        this.term = vTerm;
    }
    
    /**
     * Getter method to get language id.
     * 
     * @return language id.
     */
    public int getLanguageId() {
    
        return languageId;
    }
    
    /**
     * Setter method to set language id.
     * 
     * @param vLanguageId language id
     */
    public void setLanguageId(int vLanguageId) {
    
        this.languageId = vLanguageId;
    }
    
    /**
     * Getter method to get total marks.
     * 
     * @return total marks.
     */
    public int getTotalMarks() {
    
        return totalMarks;
    }
    
    /**
     * Setter method to set total marks.
     * 
     * @param vTotalMarks total marks
     */
    public void setTotalMarks(int vTotalMarks) {
    
        this.totalMarks = vTotalMarks;
    }
    
    /**
     * Getter method to get student count.
     * 
     * @return student count.
     */
    public int getStudentCount() {
    
        return studentCount;
    }
    
    /**
     * Setter method to set student count.
     * 
     * @param vStudentCount student count.
     */
    public void setStudentCount(int vStudentCount) {
    
        this.studentCount = vStudentCount;
    }
    
    /**
     * Getter method to get average.
     * 
     * @return average
     */
    public double getAverage() {
    
        return average;
    }
    
    /**
     * Setter method to set average.
     * 
     * @param vAverage average
     */
    public void setAverage(double vAverage) {
    
        this.average = vAverage;
    }
}
