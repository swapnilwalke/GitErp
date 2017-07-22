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

import java.util.Date;

/**
 * The Class StudentAssignmentMark.
 */
public class StudentAssignmentMark extends AuditableBaseDomain {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The student assignment marks id. */
    private int studentAssignmentMarksId;
    
    /** The student class info id. */
    private int studentClassInfoId;
    
    /** The grade subject id. */
    private int gradeSubjectId;
    
    /** The assignment id. */
    private int assignmentId;
    
    /** The grading id. */
    private Integer gradingId;
    
    /** The marks. */
    private Float marks;
    
    /** The year. */
    private Date year;
    
    /** The is absent. */
    private boolean isAbsent;
    
    /**
     * Gets the student assignment marks id.
     * 
     * @return the studentAssignmentMarksId
     */
    public int getStudentAssignmentMarksId() {

        return studentAssignmentMarksId;
    }
    
    /**
     * Sets the student assignment marks id.
     * 
     * @param intStudentAssignmentMarksId the studentAssignmentMarksId to set
     */
    public void setStudentAssignmentMarksId(final int intStudentAssignmentMarksId) {

        this.studentAssignmentMarksId = intStudentAssignmentMarksId;
    }
    
    /**
     * Gets the student class info id.
     * 
     * @return the studentClassInfoId
     */
    public int getStudentClassInfoId() {

        return studentClassInfoId;
    }
    
    /**
     * Sets the student class info id.
     * 
     * @param intStudentClassInfoId the studentClassInfoId to set
     */
    public void setStudentClassInfoId(final int intStudentClassInfoId) {

        this.studentClassInfoId = intStudentClassInfoId;
    }
    
    /**
     * Gets the grade subject id.
     * 
     * @return the gradeSubjectId
     */
    public int getGradeSubjectId() {

        return gradeSubjectId;
    }
    
    /**
     * Sets the grade subject id.
     * 
     * @param intGradeSubjectId the gradeSubjectId to set
     */
    public void setGradeSubjectId(final int intGradeSubjectId) {

        this.gradeSubjectId = intGradeSubjectId;
    }
    
    /**
     * Gets the assignment id.
     * 
     * @return the assignmentId
     */
    public int getAssignmentId() {

        return assignmentId;
    }
    
    /**
     * Sets the assignment id.
     * 
     * @param intAssignmentId the assignmentId to set
     */
    public void setAssignmentId(final int intAssignmentId) {

        this.assignmentId = intAssignmentId;
    }
    
    /**
     * Gets the year.
     * 
     * @return the year
     */
    public Date getYear() {

        return year;
    }
    
    /**
     * Sets the year.
     * 
     * @param dtYear the year to set
     */
    public void setYear(Date dtYear) {

        this.year = dtYear;
    }
    
    /**
     * Gets the grading id.
     * 
     * @return the gradingId
     */
    public Integer getGradingId() {

        return gradingId;
    }
    
    /**
     * Sets the grading id.
     * 
     * @param intGradingId the gradingId to set
     */
    public void setGradingId(final Integer intGradingId) {

        this.gradingId = intGradingId;
    }
    
    /**
     * Gets the marks.
     * 
     * @return the marks
     */
    public Float getMarks() {

        return marks;
    }
    
    /**
     * Sets the marks.
     * 
     * @param flMarks the marks to set
     */
    public void setMarks(final Float flMarks) {

        this.marks = flMarks;
    }
    
    /**
     * Gets the checks if is absent.
     * 
     * @return the isAbsent
     */
    public boolean getIsAbsent() {

        return isAbsent;
    }
    
    /**
     * Sets the checks if is absent.
     * 
     * @param bollIsAbsent the isAbsent to set
     */
    public void setIsAbsent(final boolean bollIsAbsent) {

        this.isAbsent = bollIsAbsent;
    }
    
    

    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {
    
        return "StudentAssignmentMark [studentClassInfoId=" + studentClassInfoId + ", gradeSubjectId=" + gradeSubjectId
                + ", assignmentId=" + assignmentId + ", gradingId=" + gradingId + ", marks=" + marks + ", year=" + year
                + ", isAbsent=" + isAbsent + "]";
    }
    
}
