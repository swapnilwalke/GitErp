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
 * This class represents all the properties of a Assignment domain object.
 *
 * @author Virtusa Corporation
 */
public class Assignment extends BaseDomain implements Serializable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /** Field is to decide whether enter marks or grading for assignment. */
    private boolean isMarks;

    /**
     * Represents the id for a Assignment.
     */
    private int assignmentId;

    /**
     * holds the name of the subject.
     */
    private String subjectDescription;

    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;

    /**
     * Represents the name for a assignment.
     */
    private String name;

    /**
     * Represents the description for a assignment.
     */
    private String description;

    /**
     * Represents the submission date for a assignment.
     */
    private Date date;

    /**
     * property gradeSubjectId type int.
     */
    private GradeSubject gradeSubject;

    /**
     * Constructs a new Assignment object.
     */
    public Assignment() {

    }

    /**
     * Returns the id for this Assignment object.
     *
     * @return the Assignment id.
     */
    public int getAssignmentId() {

        return assignmentId;
    }

    /**
     * Sets the id of this Assignment.
     *
     * @param intassignmentId - Assignment id.
     */
    public void setAssignmentId(int intassignmentId) {

        this.assignmentId = intassignmentId;
    }

    /**
     * Gets the checks if is marks.
     *
     * @return the isMarks
     */
    public boolean getIsMarks() {

        return isMarks;
    }

    /**
     * Sets the marks.
     *
     * @param boolIsMarks the isMarks to set
     */
    public void setIsMarks(boolean boolIsMarks) {

        this.isMarks = boolIsMarks;
    }

    /**
     * Returns the name for this Assignment object.
     *
     * @return the Assignment name.
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the name of this Assignment.
     *
     * @param sname - Assignment name.
     */
    public void setName(String sname) {

        this.name = sname;
    }

    /**
     * Returns the description for this Assignment object.
     *
     * @return the Assignment description.
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets the description of this Assignment.
     *
     * @param sdescription - Assignment description.
     */
    public void setDescription(String sdescription) {

        this.description = sdescription;
    }

    /**
     * Returns the date for this Assignment object.
     *
     * @return the Assignment submission date.
     */
    public Date getDate() {

        return date;
    }

    /**
     * Sets the date of this Assignment.
     *
     * @param sdate - Assignment submission date.
     */
    public void setDate(Date sdate) {

        this.date = sdate;
    }

    /**
     * Returns the grade subject id for this Assignment object.
     *
     * @return the gradeSubject object.
     */
    public GradeSubject getGradeSubject() {

        return gradeSubject;
    }

    /**
     * Sets the grade subject of this Assignment.
     *
     * @param sgradeSubject - Assignment grade subject.
     */
    public void setGradeSubject(GradeSubject sgradeSubject) {

        this.gradeSubject = sgradeSubject;
    }

    /**
     * Returns the subject description for this Assignment object.
     *
     * @return the subject description.
     */
    public String getSubjectDescription() {

        return subjectDescription;
    }

    /**
     * Sets the subject description of this Assignment.
     *
     * @param subjectDescriptionVal - Assignment subject.
     */
    public void setSubjectDescription(String subjectDescriptionVal) {

        this.subjectDescription = subjectDescriptionVal;
    }

    /**
     * Returns the grade description for this Assignment.
     *
     * @return the grade description.
     */
    public String getGradeDescription() {

        return gradeDescription;
    }

    /**
     * Sets the grade description of this Assignment.
     *
     * @param gradeDescriptionVal - Assignment subject.
     */
    public void setGradeDescription(String gradeDescriptionVal) {

        this.gradeDescription = gradeDescriptionVal;
    }

}
