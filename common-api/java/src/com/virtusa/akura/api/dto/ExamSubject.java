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
 * This class represents all properties of Exam Subject domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamSubject extends BaseDomain implements Serializable{

    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;

    /** Represent the description of the modified date. */
    private static final String MODIFIED_DATE_TIME = "Modified Date  ";

    /** Represent the description of the exam. */
    private static final String EXAM_DESCRIPTION = "Exam Description";

    /** Represent the description of the exam subject id. */
    private static final String EXAM_SUBJECT_ID = "Exam Subject Id ";

    /**
     * Represents the primary key of the ExamSubject.
     */
    private int examSubjectId;

    /** The property to check optional subject. */
    private boolean isOptionalSubject;

    /**
     * property subject {@link Subject}.
     */
    private Subject subject;

    /**
     * property exam {@link Exam}.
     */
    private Exam exam;

    /**
     * Default constructor.
     */
    public ExamSubject() {}

    /**
     * Constructor with parameters.
     *
     * @param intExamSubjectId - the primary key of the ExamSubject.
     * @param objSubject - {@link Subject}
     * @param objExam - {@link Exam}
     * @param dtModifiedTime - modified time
     */
    public ExamSubject(int intExamSubjectId, Subject objSubject, Exam objExam,
            Date dtModifiedTime) {

        this.examSubjectId = intExamSubjectId;
        this.subject = objSubject;
        this.exam = objExam;
    }

    /**
     * Retrieves the primary key of the examSubject id.
     *
     * @return - the primary key of the examSubject id.
     */
    public int getExamSubjectId() {

        return examSubjectId;
    }

    /**
     * Sets the primary key of the examSubject id for an examSubject object.
     *
     * @param intExamSubjectId - the examSubject id for an examSubject object.
     */
    public void setExamSubjectId(final int intExamSubjectId) {

        this.examSubjectId = intExamSubjectId;
    }

    /**
     * Returns the status of that subject weather optional or not.
     *
     * @return - the status of that subject weather optional or not.
     */
    public boolean getOptionalSubject() {

        return isOptionalSubject;
    }

    /**
     * Sets the status of that subject weather optional or not.
     *
     * @param booleanOptionalSubject - Optional subject or not.
     */
    public void setOptionalSubject(boolean booleanOptionalSubject) {

        this.isOptionalSubject = booleanOptionalSubject;
    }

    /**
     * Returns the subject for a given exam.
     *
     * @return - the subject for a given exam.
     */
    public Subject getSubject() {

        return subject;
    }

    /**
     * Sets the subject for an exam.
     *
     * @param objSubject - the subject for an exam.
     */
    public void setSubject(Subject objSubject) {

        this.subject = objSubject;
    }

    /**
     * Returns Exam object for a given ExamSubject.
     *
     * @return - Exam object for a given ExamSubject.
     */
    public Exam getExam() {

        return exam;
    }

    /**
     * Sets an instance of Exam for a ExamSubject.
     *
     * @param objExam - an instance of Exam for a ExamSubject.
     */
    public void setExam(Exam objExam) {

        this.exam = objExam;
    }

    /**
     * Overrides default toString method.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return EXAM_SUBJECT_ID + this.examSubjectId + EXAM_DESCRIPTION + this.exam + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
