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

/**
 * Domain object to map data for Exam absent list.
 *
 * @author Virtusa Corporation
 */

public class ReportAbsenteeList implements Serializable {

    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 972211049155061353L;

    /** The constant for String "ReportAbsenteeList fullName=". */
    private static final String REPORT_ABSENTEE_LIST_FULL_NAME = "ReportAbsenteeList fullName=";

    /** The constant for String ", marks=". */
    private static final String MARKS = ", marks=";

    /** The constant for String ", admissionNumber=". */
    private static final String ADMISSION_NUMBER = ", admissionNumber=";

    /**
     * holds the id number of studentTermMarkId.
     */
    private int studentTermMarkId;

    /**
     * holds the full name of the student.
     */
    private String fullName;

    /**
     * holds the admission number of the student.
     */
    private String admissionNumber;

    /**
     * holds the name of the class.
     */
    private String classDescription;

    /**
     * holds the year description of the grade.
     */
    private String gradeDescription;

    /**
     * holds the name of the term.
     */
    private String termDescription;

    /**
     * holds the name of the subject.
     */
    private String subjectDescription;

    /**
     * float variable to hold marks.
     */
    private float marks;

    /**
     * Holds absence.
     */
    private boolean absent;

    /**
     * Holds marking completed.
     */
    private boolean markingCompleted;

    /**
     * Holds nameWithInitials.
     */
    private String nameWithInitials;

    /**
     * @return nameWithInitials Returns the value of student name.
     */
    public String getNameWithInitials() {

        return nameWithInitials;
    }

    /**
     * @param nameWithInitialsRef Sets the student name with initials.
     */
    public void setNameWithInitials(String nameWithInitialsRef) {

        this.nameWithInitials = nameWithInitialsRef;
    }

    /**
     * @return studentTermMarkId Returns the value of term mark if used in "student_term_mark" table.
     */
    public int getStudentTermMarkId() {

        return studentTermMarkId;
    }

    /**
     * @param stuTermMarkId Sets the id number of studentTermMarkId.
     */
    public void setStudentTermMarkId(int stuTermMarkId) {

        this.studentTermMarkId = stuTermMarkId;
    }

    /**
     * @return fullName Returns full name of the student.
     */
    public String getFullName() {

        return fullName;
    }

    /**
     * @param strFullName Sets the value for the full name of the student.
     */
    public void setFullName(final String strFullName) {

        this.fullName = strFullName;
    }

    /**
     * @return admissionNumber Returns the admission number of the student.
     */
    public String getAdmissionNumber() {

        return admissionNumber;
    }

    /**
     * @param admissionNum Sets the value for the admission number of the student.
     */
    public void setAdmissionNumber(final String admissionNum) {

        this.admissionNumber = admissionNum;
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

    /**
     * @return gradeDescription Returns the description of the grade.
     */
    public String getGradeDescription() {

        return gradeDescription;
    }

    /**
     * @param gradeDes Sets the value for the description of the grade.
     */
    public void setGradeDescription(final String gradeDes) {

        this.gradeDescription = gradeDes;
    }

    /**
     * @return termDescription Returns the name of the term.
     */
    public String getTermDescription() {

        return termDescription;
    }

    /**
     * @param termDes Sets the value for the name of the term.
     */
    public void setTermDescription(final String termDes) {

        this.termDescription = termDes;
    }

    /**
     * @return subjectDescription Returns the name of the subject.
     */
    public String getSubjectDescription() {

        return subjectDescription;
    }

    /**
     * @param subjectDes Sets the value for the name of the subject.
     */
    public void setSubjectDescription(final String subjectDes) {

        this.subjectDescription = subjectDes;
    }

    /**
     * @return Returns the value of marks.
     */
    public float getMarks() {

        return marks;
    }

    /**
     * @param floatMarks Sets the value for the value of marks.
     */
    public void setMarks(final float floatMarks) {

        this.marks = floatMarks;
    }

    /**
     * @return Returns student absent or not.
     */
    public boolean isAbsent() {

        return absent;
    }

    /**
     * @param isAbsent Sets student absent or not.
     */
    public void setAbsent(final boolean isAbsent) {

        this.absent = isAbsent;
    }

    /**
     * @return Returns student mark is completed or not.
     */
    public boolean isMarkingCompleted() {

        return markingCompleted;
    }

    /**
     * @param isMarkingCompleted Sets student isMarkingCompleted or not.
     */
    public void setMarkingCompleted(boolean isMarkingCompleted) {

        this.markingCompleted = isMarkingCompleted;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return - the current object details.
     */
    public String toString() {

        return REPORT_ABSENTEE_LIST_FULL_NAME + fullName + ADMISSION_NUMBER + admissionNumber + MARKS + marks;
    }

}
