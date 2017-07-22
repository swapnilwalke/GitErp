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
 * @author Virtusa Corporation
 */
public class SubjectAverageTermMarks extends BaseDomain implements Serializable {
    
    /** Generated serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds composite id. */
    private SubjectAverageTermMarksId id;
    
    /** Holds grade. */
    private String grade;
    
    /** Holds subject. */
    private String subject;
    
    /** Holds totalMarks. */
    private int totalMarks;
    
    /** Holds studentCount. */
    private int studentCount;
    
    /** Holds average. */
    private double average;
    
    /**
     * Getter method for id.
     * 
     * @return id
     */
    public SubjectAverageTermMarksId getId() {
    
        return id;
    }
    
    /**
     * Setetr method for id.
     * 
     * @param vId id
     */
    public void setId(SubjectAverageTermMarksId vId) {
    
        this.id = vId;
    }
    
    /**
     * Getter method for grade.
     * 
     * @return grade
     */
    public String getGrade() {
    
        return grade;
    }
    
    /**
     * Setter method for grade.
     * 
     * @param vGrade grade
     */
    public void setGrade(String vGrade) {
    
        this.grade = vGrade;
    }
    
    /**
     * Getter method for subject.
     * 
     * @return subject
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * Setter method for subject.
     * 
     * @param vSubject subject
     */
    public void setSubject(String vSubject) {
    
        this.subject = vSubject;
    }
    
    /**
     * Getter method for total marks.
     * 
     * @return total marks
     */
    public int getTotalMarks() {
    
        return totalMarks;
    }
    
    /**
     * Setter method for total marks.
     * 
     * @param vTotalMarks total marks
     */
    public void setTotalMarks(int vTotalMarks) {
    
        this.totalMarks = vTotalMarks;
    }
    
    /**
     * Getter method for student count.
     * 
     * @return student count
     */
    public int getStudentCount() {
    
        return studentCount;
    }
    
    /**
     * Setter method for student count.
     * 
     * @param vStudentCount no of students doing this subject
     */
    public void setStudentCount(int vStudentCount) {
    
        this.studentCount = vStudentCount;
    }
    
    /**
     * Getter method for average.
     * 
     * @return average
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
}
