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
 * The Class ClassWiseStudentsSubjectsDTO.
 *
 * @author Virtusa Corporation.
 */
public class ClassWiseStudentsSubjectsDTO extends BaseDomain {

    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;

    /** The student id. */
    private int studentId;

    /** The student class info id. */
    private int studentClassInfoId;

    /** The class grade id. */
    private int classGradeId;

    /** The grade subject id. */
    private int gradeSubjectId;

    /** The full name. */
    private String nameWithInit;

    /** The year. */
    private Date year;

    /**
     * Gets the name with initials.
     *
     * @return the nameWithInit
     */
    public String getNameWithInit() {

        return nameWithInit;
    }

    /**
     * Sets the name with initials.
     *
     * @param strNameWithInit the nameWithInit to set
     */
    public void setNameWithInit(final String strNameWithInit) {

        this.nameWithInit = strNameWithInit;
    }

    /**
     * Gets the student id.
     *
     * @return the studentId
     */
    public int getStudentId() {

        return studentId;
    }

    /**
     * Sets the student id.
     *
     * @param intStudentId the studentId to set
     */
    public void setStudentId(final int intStudentId) {

        this.studentId = intStudentId;
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
     * Gets the class grade id.
     *
     * @return the classGradeId
     */
    public int getClassGradeId() {

        return classGradeId;
    }

    /**
     * Sets the class grade id.
     *
     * @param intClassGradeId the classGradeId to set
     */
    public void setClassGradeId(final int intClassGradeId) {

        this.classGradeId = intClassGradeId;
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
    public void setYear(final Date dtYear) {

        this.year = dtYear;
    }

}
