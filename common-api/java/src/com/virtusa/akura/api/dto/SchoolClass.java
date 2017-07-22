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
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all the properties of a SchoolClass domain object.
 *
 * @author Virtusa Corporation
 */
public class SchoolClass extends BaseDomain implements Serializable {

    /** The constant for String "SchoolClass id = ". */
    private static final String SCHOOL_CLASS_ID = "SchoolClass id = ";

    /** The constant for String " description = ". */
    private static final String DESCRIPTION = " description = ";

    /** The constant for String " modified date/time". */
    private static final String MODIFIED_DATE_TIME = " modified date/time";

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the id for a SchoolClass.
     */
    private int classId;

    /**
     * Represents the description for a SchoolClass.
     */
    private String description;

    /**
     * Represents a set of ClassGrades for a SchoolClass.
     */
    
    private Set<ClassGrade> classGrades = new HashSet<ClassGrade>();

    /**
     * Constructs a new SchoolClass object.
     */
    public SchoolClass() {

    }

    /**
     * Returns the id for this SchoolClass object.
     *
     * @return the SchoolClass id.
     */
    public final int getClassId() {

        return classId;
    }

    /**
     * Sets the id of this SchoolClass.
     *
     * @param intClassId - SchoolClass id.
     */
    public final void setClassId(final int intClassId) {

        this.classId = intClassId;
    }

    /**
     * Returns the description for this SchoolClass object.
     *
     * @return - the description of the SchoolClass.
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Sets the description for this SchoolClass object with the given information.
     *
     * @param strDescription - the description of the SchoolClass.
     */
    public final void setDescription(final String strDescription) {

        this.description = strDescription;
    }

    /**
     * Returns a set of ClassGrades for this SchoolClass object.
     *
     * @return - a set of classGrades of the SchoolClass
     */
    @JsonIgnore
    public final Set<ClassGrade> getClassGrades() {

        return classGrades;
    }

    /**
     * Sets the ClassGrades for this SchoolClass object.
     *
     * @param collClassGrades - the classGrades of the SchoolClass
     */
    @JsonIgnore
    public final void setClassGrades(final Set<ClassGrade> collClassGrades) {

        this.classGrades = collClassGrades;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return SCHOOL_CLASS_ID + this.classId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
