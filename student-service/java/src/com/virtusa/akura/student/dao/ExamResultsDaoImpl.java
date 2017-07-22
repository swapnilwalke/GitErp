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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * The Exam Results Dao Impl class.
 * 
 * @author Virtusa Corporation
 */

public class ExamResultsDaoImpl extends BaseDaoImpl<ExamResults> implements ExamResultsDao {

    /** query name to get student exam result marks. */
    private static final String GET_EXAM_RESULTS_BY_EXAM_ADMISSION_NO = "getExamResultsByExamAdmissionNo";

    /** query name to get all student's exam result marks. */
    private static final String GET_EXAM_RESULTS_BY_STUDENT_ID = "getExamResultsByStudentId";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ExamResultsDaoImpl.class);

    /** Represents the query name for the retrieving exam marks result. */
    private static final String GET_EXAM_RESULTS = "findExamResults";

    /**
     * Get Exam Results by Exam Admission No.
     *
     * @param examAdmissionNo - examAdmissionNo
     * @param examId - examId
     * @return list of exam results objects
     * @throws AkuraAppException - AkuraAppException
     */
    @SuppressWarnings("unchecked")
    public List<ExamResults> getExamResultsByExamAdmissionNo(String examAdmissionNo, int examId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_EXAM_RESULTS_BY_EXAM_ADMISSION_NO, examAdmissionNo,
                    examId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<ExamResults> getExamResultsByStudentId(int studentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_EXAM_RESULTS_BY_STUDENT_ID, studentId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }

    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<ExamResults> getExamResults(final int studentId, final Date date) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(GET_EXAM_RESULTS, studentId,
                    date);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
