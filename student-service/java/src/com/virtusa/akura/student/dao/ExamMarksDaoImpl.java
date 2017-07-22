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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the Exam Mark object.
 *
 * @author Virtusa Corporation
 */
public class ExamMarksDaoImpl extends BaseDaoImpl<ExamMark> implements ExamMarksDao {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ExamMarksDaoImpl.class);

    /** Represents the query name for retrieving the size of exam marks for a given exam id. */
    private static final String EXAM_MARKS_SIZE = "findExamMarksSize";

    /** Represents the query name for the stored procedure. */
    private static final String CALL_EXAM_MARKS = "callExamMarks";

    /** Represents the query name for the new exam subjects. */
    private static final String MARKS_FOR_NEW_SUBJECTS = "newExamMarksForNewSubjects";

    /** {@inheritDoc} */
    public List<BigInteger> getAllExamMarksList(final int year, final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(EXAM_MARKS_SIZE, DateUtil.getDate(year), examId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<?> searchExamMarks(int examMarksListSize, int year, String gradeId,
            final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(CALL_EXAM_MARKS, examMarksListSize,
                    gradeId, DateUtil.getDate(year), examId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<?> searchExamMarksWithArray(int year, String gradeId, int examId, Integer o)
            throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(MARKS_FOR_NEW_SUBJECTS,
                    gradeId, DateUtil.getDate(year), examId, o);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.DB_CONNECTION_ERROR + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
