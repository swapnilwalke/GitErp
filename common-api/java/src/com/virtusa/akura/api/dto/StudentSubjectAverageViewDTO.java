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
 * This class represents all properties of a Province domain object.
 *
 * @author Virtusa Corporation
 */
public class StudentSubjectAverageViewDTO extends BaseDomain implements Serializable {

    /**
     * Default Serial id.
     */
    private static final long serialVersionUID = 1L;

    /** Represents the student id of the studentSubjectAverageViewDTO object.*/
    private int studentId;

    /** Represents the student id of the studentSubjectAverageViewDTO object.*/
    private int marks;
    
    /** Holds the absent. */
    private boolean absent;

    /** Represents the class average marks for a subject of the studentSubjectAverageViewDTO object.*/
    private double classAverage;

    /** Represents the grade average marks for a subject of the studentSubjectAverageViewDTO object.*/
    private double gradeAverage;

    /** Represents the term of the studentSubjectAverageViewDTO object.*/
    private String term;

    /** Represents the classDescription of the studentSubjectAverageViewDTO object.*/
    private String classDescription;

    /** Represents the gradeDescription of the studentSubjectAverageViewDTO object.*/
    private String gradeDescription;

    /** Represents the subject of the studentSubjectAverageViewDTO object.*/
    private String subject;

    /** Represents the year of the studentSubjectAverageViewDTO object.*/
    private Date year;

    /**
     * Returns the student id of the studentSubjectAverageViewDTO object.
     *
     * @return - the student id of the studentSubjectAverageViewDTO object.
     */
    public final int getStudentId() {

        return studentId;
    }

    /**
     * Sets the student id of the studentSubjectAverageViewDTO object.
     *
     * @param studentIdVal - id of the given studentSubjectAverageViewDTO object.
     */
    public final void setStudentId(int studentIdVal) {

        this.studentId = studentIdVal;
    }
    
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
     * Returns the grade average for a student.
     *
     * @return - the grade average for a student.
     */
    public double getGradeAverage() {

        return gradeAverage;
    }

    /**
     * Sets the average marks of the subject for a grade for a year.
     *
     * @param gradeAverageVal - the average marks of the subject for a grade for a year.
     */
    public void setGradeAverage(double gradeAverageVal) {

        this.gradeAverage = gradeAverageVal;
    }

    /**
     * Returns the description of the grade of studentSubjectAverageViewDTO object.
     *
     * @return - the description of the grade of studentSubjectAverageViewDTO object.
     */
    public String getGradeDescription() {

        return gradeDescription;
    }

    /**
     * Sets the description of the grade of studentSubjectAverageViewDTO object.
     *
     * @param gradeDescription - the description of the grade of studentSubjectAverageViewDTO object.
     */
    public void setGradeDescription(String gradeDescription) {

        this.gradeDescription = gradeDescription;
    }

    /**
     * Returns the marks of the student for a subject for a term for a year.
     *
     * @return - the marks of the student for a subject for a term for a year.
     */
    public final int getMarks() {

        return marks;
    }

    /**
     * Sets the marks of the student for a subject for a term for a year.
     *
     * @param marksVal - the marks of the student for a subject for a term for a year.
     */
    public void setMarks(final int marksVal) {

        this.marks = marksVal;
    }

    /**
     * Returns the class average for a student.
     *
     * @return - the class average for a student.
     */
    public double getClassAverage() {

        return classAverage;
    }

    /**
     * Sets the average marks of the subject for a class for a year.
     *
     * @param classAverageVal - the average marks of the subject for a class for a year.
     */
    public final void setClassAverage(final double classAverageVal) {

        this.classAverage = classAverageVal;
    }

    /**
     * Returns the term of the studentSubjectAverageViewDTO object.
     *
     * @return - the term of the studentSubjectAverageViewDTO object.
     */
    public final String getTerm() {

        return term;
    }

    /**
     * Sets the term of the studentSubjectAverageViewDTO object.
     *
     * @param termVal - the term of the studentSubjectAverageViewDTO object.
     */
    public void setTerm(String termVal) {

        this.term = termVal;
    }

    /**
     * Represents the class description of the studentSubjectAverageViewDTO object.
     *
     * @return - the class description of the studentSubjectAverageViewDTO object.
     */
    public final String getClassDescription() {

        return classDescription;
    }

    /**
     * Sets the class description of the studentSubjectAverageViewDTO object.
     *
     * @param classDescriptionVal - the class description of the studentSubjectAverageViewDTO
     * object.
     */
    public final void setClassDescription(final String classDescriptionVal) {

        this.classDescription = classDescriptionVal;
    }

    /**
     * Represents the subject of the studentSubjectAverageViewDTO object.
     *
     * @return - the subject of the studentSubjectAverageViewDTO object.
     */
    public final String getSubject() {

        return subject;
    }

    /**
     * Sets the subject of the studentSubjectAverageViewDTO object.
     *
     * @param subjectVal - the subject of the studentSubjectAverageViewDTO object.
     */
    public final void setSubject(final String subjectVal) {

        this.subject = subjectVal;
    }

    /**
     * Represents the year of the studentSubjectAverageViewDTO object.
     *
     * @return - the year of the studentSubjectAverageViewDTO object.
     */
    public final Date getYear() {

        return year;
    }

    /**
     * Sets the year of the studentSubjectAverageViewDTO object.
     *
     * @param yearVal - the year of the studentSubjectAverageViewDTO object.
     */
    public final void setYear(final Date yearVal) {

        this.year = yearVal;
    }
}
