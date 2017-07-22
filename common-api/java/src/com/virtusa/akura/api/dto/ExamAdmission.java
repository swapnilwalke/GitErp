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
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents all the properties of a ExamAdmission domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamAdmission extends AuditableBaseDomain implements Serializable {

    /** Default Serial Id. */
    private static final long serialVersionUID = 1L;

    /** Represents the description of the Exam Admission Id. */
    private static final String EXAM_ADMISSION_ID = "Exam Admission Id = ";

    /** Represents the description of the Student Id. */
    private static final String STUDENT_ID = "Student Id = ";

    /** Represents the description of the Exam Modified Time. */
    private static final String MODIFIED_DATE_TIME = "Modified Time = ";

    /** Represents the key for the exam Admission. */
    private int examAdmissionId;

    /** Represents the key for the exam. */
    private int examId;

    /** Represents the key for the student. */
    private int studentId;

    /** Represents the admission number of the student for a relevant exam. */
    private String admissionNo;

    /** Represents the year of the exam admission. */
    private Date year;

    /** Represents the set of the exam marks. */
    private Set<ExamMark> examMarks = new HashSet<ExamMark>();

    /**
     * Constructs a new Exam Admission object.
     */
    public ExamAdmission() {

    }

    /**
     * Retrieves the key of the Exam Admission.
     *
     * @return - the key of the Exam Admission.
     */
    public int getExamAdmissionId() {

        return examAdmissionId;
    }

    /**
     * Sets the key for the Exam Admission.
     *
     * @param intExamAdmissionId - the key for the Exam Admission.
     */
    public void setExamAdmissionId(final int intExamAdmissionId) {

        this.examAdmissionId = intExamAdmissionId;
    }

    /**
     * Retrieves the key of the exam.
     *
     * @return - the key of the exam.
     */
    public int getExamId() {

        return examId;
    }

    /**
     * Sets the key for the exam.
     *
     * @param intExamId - the key for the exam.
     */
    public void setExamId(final int intExamId) {

        this.examId = intExamId;
    }

    /**
     * Retrieves the key for the student.
     *
     * @return - the key for the student.
     */
    public int getStudentId() {

        return studentId;
    }

    /**
     * Sets the key for the student.
     *
     * @param intStudentId - the key for the student.
     */
    public void setStudentId(final int intStudentId) {

        this.studentId = intStudentId;
    }

    /**
     * Retrieves the admission number for the student for an exam.
     *
     * @return - the admission number for the student for an exam.
     */
    public String getAdmissionNo() {

        return admissionNo;
    }

    /**
     * Sets the admission number for the student for an exam.
     *
     * @param strAdmissionNo - the admission number for the student for an exam.
     */
    public void setAdmissionNo(final String strAdmissionNo) {

        this.admissionNo = strAdmissionNo;
    }

    /**
     * Retrieves the year of the exam admission.
     *
     * @return - the year of the exam admission.
     */
    public Date getYear() {

        return year;
    }

    /**
     * Sets the year of the Exam Admission.
     *
     * @param objYear - the year of the Exam Admission.
     */
    public void setYear(final Date objYear) {

        this.year = objYear;
    }

   /**
     * Returns the set of exam marks for a relevant exam admission.
     *
     * @return - the set of exam marks for a relevant exam admission.
     */
    public Set<ExamMark> getExamMarks() {

        return examMarks;
    }

    /**
     * Sets the exam marks for the relevant exam admission.
     *
     * @param setExamMarks - the exam marks for the relevant exam admission.
     */
    public void setExamMarks(Set<ExamMark> setExamMarks) {

        this.examMarks = setExamMarks;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return EXAM_ADMISSION_ID + this.examAdmissionId + STUDENT_ID + this.studentId + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String auditToString() {
        
         return "ExamAdmission [examAdmissionId=" + examAdmissionId + ", examId=" + examId + ", studentId=" + studentId
                + ", admissionNo=" + admissionNo + ", year=" + year + "]";
    }
    
    
}
