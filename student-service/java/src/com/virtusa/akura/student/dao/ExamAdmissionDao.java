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

package com.virtusa.akura.student.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the ExamAdmissionDao object.
 *
 * @author Virtusa Corporation
 */
public interface ExamAdmissionDao extends BaseDao<ExamAdmission> {

    /**
     * Finds the Exam Admission by the admission number.
     *
     * @param admissionNo - the admission number of the Exam Admission.
     * @param year - the relevant year
     * @param examId - the key of the exam
     * @return - the relevant Exam Admission object.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamAdmission> findExamAdmissionByAdmission(final String admissionNo, final int year, int examId)
            throws AkuraAppException;

    /**
     * Retrieves the exam admission by the student key and the relevant year.
     *
     * @param admission - the admission of the exam admission
     * @param year - the relevant year
     * @param studentId - the key of the student
     * @param examId - the key of the exam
     * @return - a list of Exam Admission
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamAdmission> findExamAdmissionByStudentId(final String admission, final int year, final int studentId,
            int examId) throws AkuraAppException;

    /**
     * Get the exam admission by the exam admission no.
     *
     * @param examAdmissionNo - the admission of the exam admission
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     * @return examAdmissionNo
     */
    String getExamAdmissionNo(final String examAdmissionNo) throws AkuraAppException;
}
