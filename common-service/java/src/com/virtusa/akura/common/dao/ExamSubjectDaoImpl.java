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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the ExamSubject object.
 *
 * @author Virtusa Corporation
 */
public class ExamSubjectDaoImpl extends BaseDaoImpl<ExamSubject> implements ExamSubjectDao {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ExamSubjectDaoImpl.class);

    /** Represents the query name for finding subjects for a given exam description. */
    private static final String FIND_SUBJECTS_BY_EXAM = "findSubjectsByExam";

    /** The constant for String object "findGradeSubjectByDes". */
    private static final String FIND_SUBJECTS_BY_DES = "findExamSubjectByDes";

    /** Represents the query name for finding the existence of the given exam subject. */
    private static final String EXIST_EXAM_SUBJECT = "existExamSubject";

    /** Represents the name for the named query. */
    private static final String IS_EXIST_EXAM_MARKS = "isExistExamMarks";

    /** Represents the query name for retrieving exam subjects for a given exam. */
    private static final String GET_EXAM_SUBJECT_ID_LIST_BY_EXAM = "getExamSubjectIdListByExam";

    /** Represents the query name for finding exam subject by key of the exam. */
    private static final String EXAM_SUBJECT_BY_EXAM = "findExamSubjectByExamId";

    /** Represents the count of the exam subjects. */
    private static final String EXAM_SUBJECT_COUNT = "examSubjectCountByExam";

    /** Represents the the query name of the new exam subjects. */
    private static final String NEW_EXAM_SUBJECT = "newExamSubjects";

    /** Represents the query name for the existance of the exam subject. */
    private static final String FIND_EXAM_SUBJECT_EXIST = "findExamSubjectExist";

    /** {@inheritDoc} */
    public List<ExamSubject> findSubjectsByExam(final String description) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_SUBJECTS_BY_EXAM, description);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<ExamSubject> findExamSubjectByDes(final String examDescription, final String subjectDescription)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_SUBJECTS_BY_DES, examDescription, subjectDescription);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<ExamSubject> isExistExamSubject(final int examId, Integer subjectId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(EXIST_EXAM_SUBJECT, examId, subjectId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<ExamSubject> getExamSubjectIdListByExam(final int examId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_EXAM_SUBJECT_ID_LIST_BY_EXAM, examId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public void deleteExamSubjectList(List<ExamSubject> examSubjectIdList) throws AkuraAppException {

        try {
            getHibernateTemplate().deleteAll(examSubjectIdList);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }

    }

    /** {@inheritDoc} */
    public List<Integer> findGradingByDes(String marks) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(EXAM_SUBJECT_BY_EXAM , marks);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public List<Integer> findExamSubjectByExamId(final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(EXAM_SUBJECT_BY_EXAM , examId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public List<Long> getCountExamSubjects(final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(EXAM_SUBJECT_COUNT , examId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public List<Integer> getNewSubjects(int examId, int year) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(NEW_EXAM_SUBJECT ,examId, examId , DateUtil.getDate(year));
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public List<Integer> isAlreadyExistExamSubject(final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_EXAM_SUBJECT_EXIST , examId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }

    /** {@inheritDoc} */
    public List<BigInteger> isExistExamMarks(int examSubjectId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(IS_EXIST_EXAM_MARKS , examSubjectId);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
    }
}
