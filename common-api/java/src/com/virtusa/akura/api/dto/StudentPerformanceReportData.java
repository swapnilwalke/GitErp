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

import java.util.List;

/**
 * The Class ClassWiseStudentReportTemplate.
 *
 * @author Virtusa Corporation
 */
public class StudentPerformanceReportData {

    /** Holds admission no. */
    private String admissionNo;

    /** Holds name. */
    private String name;

    /** Holds list of marks to display in report. */
    private List<String> marks;

    /** Holds study medium . */
    private String medium;

    /** Holds the term description. */
    private String term;

    /**
     * holds the name of the class.
     */
    private String classDescription;

    /**
     * Getter method for admission no.
     *
     * @return admission no
     */
    public String getAdmissionNo() {

        return admissionNo;
    }

    /**
     * Setter method for admission no.
     *
     * @param vAdmissionNumber admission no
     */
    public void setAdmissionNo(String vAdmissionNumber) {

        this.admissionNo = vAdmissionNumber;
    }

    /**
     * Getter method for name.
     *
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * Setter method for name.
     *
     * @param vName name
     */
    public void setName(String vName) {

        this.name = vName;
    }

    /**
     * Getter method for marks.
     *
     * @return list of marks
     */
    public List<String> getMarks() {

        return marks;
    }

    /**
     * Setter method for marks.
     *
     * @param vMarks list of marks
     */
    public void setMarks(List<String> vMarks) {

        this.marks = vMarks;
    }

    /**
     * Getter method for student study medium.
     *
     * @return medium.
     */
    public String getMedium() {

        return medium;
    }

    /**
     * Setter method for medium.
     *
     * @param mMedium grade study medium.
     */
    public void setMedium(String mMedium) {

        this.medium = mMedium;
    }

    /**
     * Gets the term.
     *
     * @return the term.
     */
    public String getTerm() {

        return term;
    }

    /**
     * Sets the term.
     *
     * @param termValue the term.
     */
    public void setTerm(String termValue) {

        this.term = termValue;
    }

    /**
     * @return classDescription Returns the name of the class.
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * @param classDes Sets the value for the name of the class.
     */
    public void setClassDescription(final String classDes) {

        this.classDescription = classDes;
    }
}
