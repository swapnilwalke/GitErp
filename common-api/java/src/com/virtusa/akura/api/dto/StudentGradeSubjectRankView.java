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
 * The DTO class to holds the data for Student Grade Subject Rank View .
 * 
 * @author Virtusa Corporation
 */
public class StudentGradeSubjectRankView extends BaseDomain implements Serializable {
    
    /** Holds serialisation ID. */
    private static final long serialVersionUID = 1L;
    
    /** Holds the marks. */
    private float marks;
    
    /** Holds the subject description. */
    private String subject;
    
    /** Holds the student id. */
    private int studentId;
    
    /** Holds the year. */
    private Date year;
    
    /** Holds the student term mark id. */
    private int studentTermMarkId;
    
    /** Holds the absent. */
    private boolean absent;
    
    /** Holds the admissionNo. */
    private String admissionNo;
    
    /** Holds the nameWithInitials. */
    private String nameWithInitials;
    
    /** Holds the classGradeId. */
    private int classGradeId;
    
    /** Holds the studentClassInfoId. */
    private int studentClassInfoId;
    
    /** Holds the gradeSubjectId. */
    private int gradeSubjectId;
    
    /** Holds average. */
    private double average;
    
    /** Holds rank. */
    private int rank;
    
    /** Holds total. */
    private int total;
    /**
     * Gets the absent status.
     * 
     * @return the absent status.
     */
    public boolean isAbsent() {
    
        return absent;
    }
    
    /**
     * Sets the absent status.
     * 
     * @param isabsent the absent status
     */
    public void setAbsent(boolean isabsent) {
    
        this.absent = isabsent;
    }
    
    /**
     * Gets the term marks id.
     * 
     * @return the teram mark id.
     */
    public int getStudentTermMarkId() {
    
        return studentTermMarkId;
    }
    
    /**
     * Sets the term mark id.
     * 
     * @param id the term mark id.
     */
    public void setStudentTermMarkId(int id) {
    
        this.studentTermMarkId = id;
    }
    
    /**
     * Gets the students id.
     * 
     * @return student id.
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * Sets the student id.
     * 
     * @param id the student id.
     */
    public void setStudentId(int id) {
    
        this.studentId = id;
    }
    
    /**
     * Gets the year.
     * 
     * @return the year.
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        
        return null;
    }
    
    /**
     * Sets the year.
     * 
     * @param yearValue the year.
     */
    public void setYear(Date yearValue) {
    
        if (yearValue != null) {
            
            this.year = (Date) yearValue.clone();
        }
    }
    
    /**
     * Gets the marks.
     * 
     * @return the marks.
     */
    public float getMarks() {
    
        return marks;
    }
    
    /**
     * Sets the marks.
     * 
     * @param marksVlaue the marks.
     */
    public void setMarks(float marksVlaue) {
    
        this.marks = marksVlaue;
    }
    
    /**
     * Gets the subject.
     * 
     * @return the subject.
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * Sets the subject.
     * 
     * @param subjectValue the subject.
     */
    public void setSubject(String subjectValue) {
    
        this.subject = subjectValue;
    }
    
    /**
     * Gets the admission no.
     * 
     * @return admission no
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }
    
    /**
     * Sets the admission no.
     * 
     * @param vAdmissionNo admission no
     */
    public void setAdmissionNo(String vAdmissionNo) {
    
        this.admissionNo = vAdmissionNo;
    }
    
    /**
     * Getter method for nameWithInitials.
     * 
     * @return name with initials
     */
    public String getNameWithInitials() {
    
        return nameWithInitials;
    }
    
    /**
     * Setter method for nameWithInitials.
     * 
     * @param vNameWithInitials name with initials
     */
    public void setNameWithInitials(String vNameWithInitials) {
    
        this.nameWithInitials = vNameWithInitials;
    }
    
    /**
     * Getter method for student class info id.
     * 
     * @return student class info id
     */
    public int getStudentClassInfoId() {
    
        return studentClassInfoId;
    }
    
    /**
     * Setter method for student class info id.
     * 
     * @param vStudentClassInfoId student class info id
     */
    public void setStudentClassInfoId(int vStudentClassInfoId) {
    
        this.studentClassInfoId = vStudentClassInfoId;
    }
    
    /**
     * Getter method for class grade id.
     * 
     * @return class grade id
     */
    public int getClassGradeId() {
    
        return classGradeId;
    }
    
    /**
     * Setter method for class grade id.
     * 
     * @param vClassGradeId class grade id
     */
    public void setClassGradeId(int vClassGradeId) {
    
        this.classGradeId = vClassGradeId;
    }
    
    /**
     * Getter method for grade subject id.
     * 
     * @return grade subject id.
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
     * Getter method for average.
     * 
     * @return average.
     */
    public double getAverage() {
    
        return average;
    }
    
    /**
     * Setter method for average.
     * 
     * @param vAverage average
     */
    public void setAverage(double vAverage) {
    
        this.average = vAverage;
    }
    
    /**
     * Getter method for rank.
     * 
     * @return rank.
     */
    public int getRank() {
    
        return rank;
    }
    
    /**
     * Setter method for rank.
     * 
     * @param vRank rank
     */
    public void setRank(int vRank) {
    
        this.rank = vRank;
    }

    /**
     * Getter method for total.
     * 
     * @return total
     */
    public int getTotal() {
    
        return total;
    }

    /**
     * Setter method for total.
     * 
     * @param vTotal int
     */
    public void setTotal(int vTotal) {
    
        this.total = vTotal;
    }
    
    
}
