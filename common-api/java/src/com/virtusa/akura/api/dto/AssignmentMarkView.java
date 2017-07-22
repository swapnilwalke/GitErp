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
 * Domain object to map data for AssignmentMarkView.
 * 
 * @author Virtusa Corporation
 */

public class AssignmentMarkView extends BaseDomain implements Serializable {
    
    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** Holds gradeId. */
    private int gradeId;
    
    /** Holds name. */
    private String name;
    
    /** Holds gradeSubjectId. */
    private int gradeSubjectId;
    
    /** Holds gradeDescription. */
    private String gradeDescription;
    
    /** Holds subjectId. */
    private int subjectId;
    
    /** Holds subjectDescription. */
    private String subjectDescription;
    
    /** Holds gradingId. */
    private Integer gradingId;
    
    /** Holds grading. */
    private String grading;
    
    /** Holds marks. */
    private Float marks;
    
    /** Holds absent. */
    private boolean absent;
    
    /** Holds studentId. */
    private int studentId;
    
    /** Holds assignmentId. */
    private int assignmentId;
    
    /** Holds year. */
    private Date year;
    
    /**
     * gets the Id of the student object.
     * 
     * @return id of the student in integer.
     */
    public int getStudentId() {

        return studentId;
    }
    
    /**
     * sets the studentId of the assignment object.
     * 
     * @param intStudentId the sportId in integer.
     */
    public void setStudentId(int intStudentId) {

        this.studentId = intStudentId;
    }
    
    /**
     * gets the Id of the assignment object.
     * 
     * @return id of the assignment in integer.
     */
    public int getAssignmentId() {

        return assignmentId;
    }
    
    /**
     * sets the assignmentId of the assignment object.
     * 
     * @param intAssignmentId the assignmentId in integer.
     */
    public void setAssignmentId(int intAssignmentId) {

        this.assignmentId = intAssignmentId;
    }
    
    /**
     * gets the year of the assignment object.
     * 
     * @return year of the assignment in Date.
     */
    public Date getYear() {

        return year;
    }
    
    /**
     * sets the year of the assignment object.
     * 
     * @param yearObj the date in Date type.
     */
    public void setYear(Date yearObj) {

        this.year = yearObj;
    }
    
    /**
     * gets the gradeId of the assignment object.
     * 
     * @return the gradeId
     */
    public int getGradeId() {

        return gradeId;
    }
    
    /**
     * sets the gradeId of the assignment object.
     * 
     * @param intGradeId the gradeId to set
     */
    public void setGradeId(int intGradeId) {

        this.gradeId = intGradeId;
    }
    
    /**
     * gets the name of the assignment object.
     * 
     * @return the name
     */
    public String getName() {

        return name;
    }
    
    /**
     * sets the name of the assignment object.
     * 
     * @param strName the name to set
     */
    public void setName(String strName) {

        this.name = strName;
    }
    
    /**
     * gets the gradeSubjectId of the assignment object.
     * 
     * @return the gradeSubjectId
     */
    public int getGradeSubjectId() {

        return gradeSubjectId;
    }
    
    /**
     * sets the gradeSubjectId of the assignment object.
     * 
     * @param intGradeSubjectId the gradeSubjectId to set
     */
    public void setGradeSubjectId(int intGradeSubjectId) {

        this.gradeSubjectId = intGradeSubjectId;
    }
    
    /**
     * gets the gradeDescription of the assignment object.
     * 
     * @return the gradeDescription
     */
    public String getGradeDescription() {

        return gradeDescription;
    }
    
    /**
     * sets the gradeDescription of the assignment object.
     * 
     * @param strGradeDescription the gradeDescription to set
     */
    public void setGradeDescription(String strGradeDescription) {

        this.gradeDescription = strGradeDescription;
    }
    
    /**
     * gets the Id of the subject of assignment object.
     * 
     * @return the subjectId
     */
    public int getSubjectId() {

        return subjectId;
    }
    
    /**
     * sets the subjectId of the assignment object.
     * 
     * @param intSubjectId the subjectId to set
     */
    public void setSubjectId(int intSubjectId) {

        this.subjectId = intSubjectId;
    }
    
    /**
     * gets the subjectDescription of the assignment object.
     * 
     * @return the subjectDescription
     */
    public String getSubjectDescription() {

        return subjectDescription;
    }
    
    /**
     * sets the subjectDescription of the assignment object.
     * 
     * @param strSubjectDescription the subjectDescription to set
     */
    public void setSubjectDescription(String strSubjectDescription) {

        this.subjectDescription = strSubjectDescription;
    }
    
    /**
     * gets the gradingId of the assignment object.
     * 
     * @return the gradingId
     */
    public Integer getGradingId() {

        return gradingId;
    }
    
    /**
     * sets the gradingId of the assignment object.
     * 
     * @param intGradingId the gradingId to set
     */
    public void setGradingId(Integer intGradingId) {

        this.gradingId = intGradingId;
    }
    
    /**
     * gets the grading of the assignment object.
     * 
     * @return the grading
     */
    public String getGrading() {

        return grading;
    }
    
    /**
     * sets the grading of the assignment object.
     * 
     * @param strGrading the grading to set
     */
    public void setGrading(String strGrading) {

        this.grading = strGrading;
    }
    
    /**
     * gets the marks of the assignment object.
     * 
     * @return the marks
     */
    public Float getMarks() {

        return marks;
    }
    
    /**
     * sets the marks of the assignment object.
     * 
     * @param fMarks the marks to set
     */
    public void setMarks(Float fMarks) {

        this.marks = fMarks;
    }
    
    /**
     * check status of present.
     * 
     * @return the absent in boolean
     */
    public boolean isAbsent() {

        return absent;
    }
    
    /**
     * sets the attendance status of the assignment object.
     * 
     * @param absentObj the absent to set
     */
    public void setAbsent(boolean absentObj) {

        this.absent = absentObj;
    }
    
}
