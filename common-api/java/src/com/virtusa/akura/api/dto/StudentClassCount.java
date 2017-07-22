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
 * This class represents all properties of a StudentClassCount domain object.
 *
 * @author Virtusa Corporation
 */
public class StudentClassCount extends BaseDomain implements Serializable {

    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;

    /** Represents the grade of the studentClassCount object. */
    private int studentGradeCount;

    /** Represents the grade of the student count. */
    private String grade;

    /** Represents the grade class of the studentClassCount object. */
    private String classGrade;

    /** Represents the subject of the studentClassCount object. */
    private String subject;

    /** Represents the term of the studentClassCount object. */
    private String term;

    /** Represents the year of the studentClassCount object. */
    private Date year;

    /** Constructs a new Province object. */
    public StudentClassCount() {

    }

    /**
     * Returns the grade of the studentClassCount object.
     *
     * @return - the grade of the studentClassCount object.
     */
    public String getGrade() {

        return grade;
    }

    /**
     * Sets the grade of the studentClassCount object.
     *
     * @param strGrade - sets the grade of the studentClassCount object.
     */
    public void setGrade(String strGrade) {

        this.grade = strGrade;
    }

    /**
     * Returns the grade of the studentClassCount object.
     *
     * @return - the grade of the studentClassCount object.
     */
    public String getClassGrade() {

        return classGrade;
    }

    /**
     * Sets the gradeClass of the studentClassCount object.
     *
     * @param strClassGrade - sets the gradeClass of the studentClassCount object.
     */
    public void setClassGrade(String strClassGrade) {

        this.classGrade = strClassGrade;
    }

    /**
     * Returns the year of the studentClassCount object.
     *
     * @return - the year of the studentClassCount object.
     */
    public Date getYear() {

        return year;
    }

    /**
     * Sets the year of the studentClassCount object.
     *
     * @param dateYear - sets the year of the studentClassCount object.
     */
    public void setYear(Date dateYear) {

        this.year = dateYear;
    }

    /**
     * Returns the subject of the studentClassCount object.
     *
     * @return - the subject of the studentClassCount object.
     */
    public String getSubject() {

        return subject;
    }

    /**
     * Sets the subject of the studentClassCount object.
     *
     * @param strSubject - sets the subject of the studentClassCount object.
     */
    public void setSubject(String strSubject) {

        this.subject = strSubject;
    }

    /**
     * Returns the term of the studentClassCount object.
     *
     * @return - the term of the studentClassCount object.
     */
    public String getTerm() {

        return term;
    }

    /**
     * Sets the term of the studentClassCount object.
     *
     * @param strTerm - sets the term of the studentClassCount object.
     */
    public void setTerm(String strTerm) {

        this.term = strTerm;
    }

    /**
     * Returns the count of for grades of the studentClassCount object.
     *
     * @return - the count of for grades of the studentClassCount object.
     */
    public int getStudentGradeCount() {

        return studentGradeCount;
    }

    /**
     * Sets the count of for grades of the studentClassCount object.
     *
     * @param intStudentGradeCount - sets the count of for grades of the studentClassCount object.
     */
    public void setStudentGradeCount(int intStudentGradeCount) {

        this.studentGradeCount = intStudentGradeCount;
    }

}
