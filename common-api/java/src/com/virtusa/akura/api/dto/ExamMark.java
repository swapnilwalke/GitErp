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
 * This class represents all properties of Exam Mark domain object.
 * 
 * @author Virtusa Corporation
 */
public class ExamMark extends AuditableBaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** Represent the description of the modified date. */
    private static final String MODIFIED_DATE_TIME = "Modified Date  ";
    
    /** Represent the description of the exam subject id. */
    private static final String EXAM_SUBJECT_ID = "Exam Subject Id ";
    
    /** Represent the description of the exam marks key. */
    private static final String EXAM_MARKS_ID = "Exam Marks Id ";
    
    /** Represent the description of the student key. */
    private static final String STUDENT_ID = "Student Id ";
    
    /** Represents the key of the Exam Marks. */
    private int examMarksId;
    
    /** Represents the key of the exam admission for the relevant exam mark. */
    private int examAdmission;
    
    /** Represents the key of the Exam Subject. */
    private int examSubject;
    
    /** Represents the status of the update. */
    private int status;
    
    /** Represents the key of the Student. */
    private int student;
    
    /** Represents the optional subject or not. */
    private boolean optional;
    
    /** Represents the present or absent status. */
    private boolean absent;
    
    /** Represents the key of the Grading. */
    private Integer grading;
    
    /** Represents the marks for a student for an exam subject. */
    private Float marks;
    
    /** Represents the year of the exam marks. */
    private Date year;
    
    /**
     * Default constructor.
     */
    public ExamMark() {
    
        super();
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param intExamMarksId - the primary key of the ExamMarks.
     * @param intExamSubject - {@link ExamSubject}
     * @param intStudent - {@link Student}
     */
    public ExamMark(final int intExamMarksId, final int intExamSubject, final int intStudent) {
    
        super();
        this.examMarksId = intExamMarksId;
        this.examSubject = intExamSubject;
        this.student = intStudent;
    }
    
    /**
     * Returns the key of the Exam Marks.
     * 
     * @return - the key of the Exam Marks.
     */
    public int getExamMarksId() {
    
        return examMarksId;
    }
    
    /**
     * Sets the key for the Exam Marks.
     * 
     * @param intExamMarksId - the key for the Exam Marks.
     */
    public void setExamMarksId(final int intExamMarksId) {
    
        this.examMarksId = intExamMarksId;
    }
    
    /**
     * Returns the key of the Exam Subject.
     * 
     * @return - the key of the Exam Subject.
     */
    public int getExamSubject() {
    
        return examSubject;
    }
    
    /**
     * Sets the key of the Exam Subject.
     * 
     * @param intExamSubject - the key of the Exam Subject.
     */
    public void setExamSubject(final int intExamSubject) {
    
        this.examSubject = intExamSubject;
    }
    
    /**
     * Returns the key of the grading for the exam marks.
     * 
     * @return - the key of the grading for the exam marks.
     */
    public Integer getGrading() {
    
        return grading;
    }
    
    /**
     * Sets the key of the Grading for the exam Marks.
     * 
     * @param intGrading - the key of the Grading for the exam Marks.
     */
    public void setGrading(final Integer intGrading) {
    
        this.grading = intGrading;
    }
    
    /**
     * Returns the key of the Student.
     * 
     * @return - the key of the Student.
     */
    public int getStudent() {
    
        return student;
    }
    
    /**
     * Sets the key of the Student for exam marks.
     * 
     * @param intStudent - the key of the Student for exam marks.
     */
    public void setStudent(final int intStudent) {
    
        this.student = intStudent;
    }
    
    /**
     * Returns the marks for the Exam Marks.
     * 
     * @return - the marks for the Exam Marks.
     */
    public Float getMarks() {
    
        return marks;
    }
    
    /**
     * Sets the marks for the Exam Marks.
     * 
     * @param fMarks - the marks for the Exam Marks.
     */
    public void setMarks(final Float fMarks) {
    
        this.marks = fMarks;
    }
    
    /**
     * Returns the relevant Year of the Exam Marks.
     * 
     * @return - the relevant Year of the Exam Marks.
     */
    public Date getYear() {
    
        if (year != null) {
            year = (Date) year.clone();
        }
        return year;
    }
    
    /**
     * Sets the year for the Exam Marks.
     * 
     * @param dYear - the year for the Exam Marks.
     */
    public void setYear(final Date dYear) {
    
        if (dYear != null) {
            
            this.year = (Date) dYear.clone();
        }
        this.year = dYear;
    }
    
    /**
     * Returns the status of the update.
     * 
     * @return - the status of the update.
     */
    public int getStatus() {
    
        return status;
    }
    
    /**
     * Sets the status of the update.
     * 
     * @param intStatus - the status of the update.
     */
    public void setStatus(int intStatus) {
    
        this.status = intStatus;
    }
    
    /**
     * Returns the status of the subject. Optional or not.
     * 
     * @return - the status of the subject
     */
    public boolean getOptional() {
    
        return optional;
    }
    
    /**
     * Sets the status of the subject.
     * 
     * @param bOptional - the status of the subject
     */
    public void setOptional(final boolean bOptional) {
    
        this.optional = bOptional;
    }
    
    /**
     * Returns the presentation for the subject for a particular exam.
     * 
     * @return - the presentation for the subject for a particular exam.
     */
    public boolean getAbsent() {
    
        return absent;
    }
    
    /**
     * Sets the presentation for the subject.
     * 
     * @param bAbsent - the presentation for the subject.
     */
    public void setAbsent(final boolean bAbsent) {
    
        this.absent = bAbsent;
    }
    
    /**
     * Returns the exam admission of the exam mark.
     * 
     * @return - the exam admission of the exam mark.
     */
    public int getExamAdmission() {
    
        return examAdmission;
    }
    
    /**
     * Sets the exam admission of the exam marks.
     * 
     * @param objExamAdmission - the exam admission key of the exam admission for the exam mark.
     */
    public void setExamAdmission(final int objExamAdmission) {
    
        this.examAdmission = objExamAdmission;
    }
    
    /**
     * Overrides default toString method.
     * 
     * @return - the current object details.
     */
    public final String toString() {
    
        return EXAM_SUBJECT_ID + this.examSubject + EXAM_MARKS_ID + this.examMarksId + MODIFIED_DATE_TIME
                + this.getModifiedTime() + STUDENT_ID + this.student;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {
    
        return "ExamMark [examSubject=" + examSubject + ", status=" + status + ", student=" + student + ", absent="
                + absent + ", grading=" + grading + ", marks=" + marks + ", year=" + year + "]";
    }
    
}
