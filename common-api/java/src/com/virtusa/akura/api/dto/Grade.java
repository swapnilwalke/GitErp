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

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all properties of Grade domain object.
 *
 * @author Virtusa Corporation
 */
public class Grade extends BaseDomain {

    /** attribute for holding string. */
    private static final String DESCRIPTION = ", description=";

    /** attribute for holding string. */
    private static final String GRADE_ID = "gradeId=";

    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Property gradeId type int.
     */
    private int gradeId;

    /**
     * Property description type String.
     */
    private String description;

    /**
     * Property gradeSubjects type Set.
     */
    private Set<GradeSubject> gradeSubjects = new HashSet<GradeSubject>(0);

    /**
     * Property classGrades type Set.
     */
    
    private Set<ClassGrade> classGrades = new HashSet<ClassGrade>(0);

    /**
     * Default constructor.
     */
    public Grade() {

    }

    /**
     * Constructor with parameters.
     *
     * @param intGradeId type int
     * @param strDescription type String
     */
    public Grade(int intGradeId, String strDescription) {

        this.gradeId = intGradeId;
        this.description = strDescription;
    }

    /**
     * Overloaded constructor.
     *
     * @param intGradeId type int
     * @param strDescription type String
     * @param collSectionHeads type Set
     * @param collClassGrades type Set
     */
    public Grade(int intGradeId, String strDescription, Set<GradeSubject> collGradeSubjects,
            Set<ClassGrade> collClassGrades) {

        this.gradeId = intGradeId;
        this.description = strDescription;
        this.gradeSubjects = collGradeSubjects;
        this.classGrades = collClassGrades;
    }

    /**
     * @return the gradeId
     */
    public int getGradeId() {

        return gradeId;
    }

    /**
     * @param intGradeId the gradeId to set
     */
    public void setGradeId(int intGradeId) {

        this.gradeId = intGradeId;
    }

    /**
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }

    /**
     * @return the gradeSubjects
     */
    @JsonIgnore
    public Set<GradeSubject> getGradeSubjects() {

        return gradeSubjects;
    }

    /**
     * @param collGradeSubjects the gradeSubjects to set
     */
    @JsonIgnore
    public void setGradeSubjects(Set<GradeSubject> collGradeSubjects) {

        this.gradeSubjects = collGradeSubjects;
    }

    /**
     * @return the classGrades
     */
    @JsonIgnore
    public Set<ClassGrade> getClassGrades() {

        return classGrades;
    }

    /**
     * @param collClassGrades the classGrades to set
     */
    @JsonIgnore
    public void setClassGrades(Set<ClassGrade> collClassGrades) {

        this.classGrades = collClassGrades;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return GRADE_ID + gradeId + DESCRIPTION + description;
    }

}
