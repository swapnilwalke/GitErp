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

import java.math.BigInteger;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Exam Mark object.
 *
 * @author Virtusa Corporation
 */
public interface ExamMarksDao extends BaseDao<ExamMark> {

    /**
     * Retrieves the size of the exam marks for a given exam key and the year.
     *
     * @param year - the relevant year.
     * @param examId - the key of the exam object.
     * @return - the size of the exam marks for a given exam key and the year.
     * @throws AkuraAppException - The exception details that occurred when deleting a examSubject instance.
     */
    List<BigInteger> getAllExamMarksList(final int year, final int examId) throws AkuraAppException;

    /**
     * Returns the list of objects for relvant data.
     *
     * @param examMarksListSize - the size of the exam marks.
     * @param year - the relevant year.
     * @param gradeId - the key of the Grade.
     * @param examId - the key of the Exam.
     * @return - the list of objects for relvant data.
     * @throws AkuraAppException - The exception details that occurred when deleting a examSubject instance.
     */
    List searchExamMarks(int examMarksListSize, int year, String gradeId, final int examId) throws AkuraAppException;

    /**
     * Searches the Exam Marks for new subjects.
     *
     * @param year - the relevant year
     * @param gradeId - the key of the grade
     * @param examId - the key of the exam
     * @param examSubjectKey - the key of the exam subject
     * @return - a list of the exam marks for new subjects
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List searchExamMarksWithArray(final int year, final String gradeId, final int examId, final Integer examSubjectKey)
            throws AkuraAppException;
}
