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

package com.virtusa.akura.common.dao;

import java.math.BigInteger;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the ExamSubject object.
 *
 * @author Virtusa Corporation
 */
public interface ExamSubjectDao extends BaseDao<ExamSubject> {

    /**
     * Retrieves the subjects for a given description of exam.
     *
     * @param description - description of the exam.
     * @return - a list of Exam Subjects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<ExamSubject> findSubjectsByExam(final String description) throws AkuraAppException;

    /**
     * Retrieves Exam Subjects for a given descriptions of the Exam and the Subject.
     *
     * @param examDescription - the description of the Exam.
     * @param subjectDescription - the description of the Subject.
     * @return - a list of ExamSubject.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamSubject> findExamSubjectByDes(final String examDescription, final String subjectDescription)
            throws AkuraAppException;

    /**
     * Checks the existence of the Exam Subject for a given keys of the exam and the subject.
     *
     * @param examId - the key of the Exam.
     * @param subjectId - the key of the Subject.
     * @return - a list of existing Exam Subject.s
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamSubject> isExistExamSubject(final int examId, Integer subjectId) throws AkuraAppException;

    /**
     * Retrieves Exam Subjects for a given exam key.
     *
     * @param examId - the key of the Exam.
     * @return - a list of Exam Subject.s
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<ExamSubject> getExamSubjectIdListByExam(final int examId) throws AkuraAppException;

    /**
     * Deletes the Exam Subjects.
     *
     * @param examSubjectIdList - a list of Exam Subjects to be deleted.
     * @throws AkuraAppException - The exception details that occurred when deleting.
     */
    void deleteExamSubjectList(final List<ExamSubject> examSubjectIdList) throws AkuraAppException;

    /**
     * Finds exam subject by given exam key.
     *
     * @param examId - the key of the Exam.
     * @return - the Exam subject key.
     * @throws AkuraAppException - The exception details that occurred when finding.
     */
    List<Integer> findExamSubjectByExamId(final int examId) throws AkuraAppException;

    /**
     * Returns the count of the exam subjects.
     *
     * @param examId - the key of the exam
     * @return - the count of the exam subjects
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Long> getCountExamSubjects(final int examId) throws AkuraAppException;

    /**
     * Returns the new exam subjects for a given exam key.
     *
     * @param examId - the key of the exam
     * @param year - the relevant year
     * @return - a list of exam subject keys for exam key that is newly added.
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Integer> getNewSubjects(final int examId, final int year) throws AkuraAppException;

    /**
     * Checks wether exam subject is already exist or not.
     *
     * @param examId - the key of the exam
     * @return - the status of the existance
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Integer> isAlreadyExistExamSubject(final int examId)throws AkuraAppException;

    /**
     * Checks weather exam marks are exist for the relevant exam subject key.
     *
     * @param examSubjectId - the key for the exam subject.
     * @return - the count of the exam marks
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<BigInteger> isExistExamMarks(int examSubjectId)throws AkuraAppException;
}
