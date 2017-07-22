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
 * The class ExamResults.
 *
 * @author Virtusa Corporation
 */

public class ExamResults extends BaseDomain implements Serializable {

    /** Constant definition. */
    private static final long serialVersionUID = 1L;

    /** Holds the student id. */
    private int studentID;

    /** Holds the full name. */
    private String fullName;

    /** Holds the exam admission no. */
    private String examAdmissionNo;

    /** Holds the subject description. */
    private String subjectDescription;

    /** Holds the year. */
    private Date year;

    /** Holds the marks. */
    private Float marks;

    /** Holds the grading acronym. */
    private String gradingAcronym;

    /** Holds the grading description. */
    private String gradingDescription;

    /** Holds the exam description. */
    private String examDescription;

    /** Holds the mark type. */
    private boolean markType;

    /** Holds the is optional subject. */
    private boolean isOptionalSubject;

    /** Holds the examId. */
    private int examId;

    /** Represents the optional subject or not. */
    private boolean optional;

    /** Represents the present or absent status. */
    private boolean absent;

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
     * Get student id.
     *
     * @return studentID
     */
    public int getStudentID() {

        return studentID;
    }

    /**
     * Set student id.
     *
     * @param studentIDRef studentIDRef
     */
    public void setStudentID(int studentIDRef) {

        this.studentID = studentIDRef;
    }

    /**
     * Get full name.
     *
     * @return fullName
     */
    public String getFullName() {

        return fullName;
    }

    /**
     * Set full name.
     *
     * @param fullNameRef fullName
     */
    public void setFullName(String fullNameRef) {

        this.fullName = fullNameRef;
    }

    /**
     * Get exam admission no.
     *
     * @return examAdmissionNo
     */
    public String getExamAdmissionNo() {

        return examAdmissionNo;
    }

    /**
     * Set Exam Admission No.
     *
     * @param examAdmissionNoRef examAdmissionNo
     */
    public void setExamAdmissionNo(String examAdmissionNoRef) {

        this.examAdmissionNo = examAdmissionNoRef;
    }

    /**
     * Get subject description.
     *
     * @return subjectDescription
     */
    public String getSubjectDescription() {

        return subjectDescription;
    }

    /**
     * Set subject description.
     *
     * @param subjectDescriptionRef subjectDescription
     */
    public void setSubjectDescription(String subjectDescriptionRef) {

        this.subjectDescription = subjectDescriptionRef;
    }

    /**
     * Get year.
     *
     * @return year
     */
    public Date getYear() {

        return year;
    }

    /**
     * Set year.
     *
     * @param yearRef - year
     */
    public void setYear(Date yearRef) {

        this.year = yearRef;
    }

    /**
     * Get marks.
     *
     * @return marks
     */
    public Float getMarks() {

        return marks;
    }

    /**
     * Set marks.
     *
     * @param marksRef - marks
     */
    public void setMarks(Float marksRef) {

        this.marks = marksRef;
    }

    /**
     * Get grading acronym.
     *
     * @return gradingAcronym
     */
    public String getGradingAcronym() {

        return gradingAcronym;
    }

    /**
     * Set grading acronym.
     *
     * @param gradingAcronymRef - gradingAcronym
     */
    public void setGradingAcronym(String gradingAcronymRef) {

        this.gradingAcronym = gradingAcronymRef;
    }

    /**
     * Get Grading Description.
     *
     * @return gradingDescription
     */
    public String getGradingDescription() {

        return gradingDescription;
    }

    /**
     * Set Grading Description.
     *
     * @param gradingDescriptionRef - gradingDescription
     */
    public void setGradingDescription(String gradingDescriptionRef) {

        this.gradingDescription = gradingDescriptionRef;
    }

    /**
     * Get Exam description.
     *
     * @return gradingAcronym
     */
    public String getExamDescription() {

        return examDescription;
    }

    /**
     * Set exam description.
     *
     * @param examDescriptionRef - examDescription
     */
    public void setExamDescription(String examDescriptionRef) {

        this.examDescription = examDescriptionRef;
    }

    /**
     * Get mark type.
     *
     * @return markType
     */
    public boolean isMarkType() {

        return markType;
    }

    /**
     * Set mark type.
     *
     * @param markTypeRef - markType
     */
    public void setMarkType(boolean markTypeRef) {

        this.markType = markTypeRef;
    }

    /**
     * Get is optional subject.
     *
     * @return isOptionalSubject
     */
    public boolean getIsOptionalSubject() {

        return isOptionalSubject;
    }

    /**
     * Set optional subject.
     *
     * @param isOptionalSubjectRef - isOptionalSubject
     */
    public void setIsOptionalSubject(boolean isOptionalSubjectRef) {

        this.isOptionalSubject = isOptionalSubjectRef;
    }

    /**
     * Get Exam id.
     *
     * @return examId
     */
    public int getExamId() {

        return examId;
    }

    /**
     * Set exam Id.
     *
     * @param examIdRef - examId
     */
    public void setExamId(int examIdRef) {

        this.examId = examIdRef;
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
}
