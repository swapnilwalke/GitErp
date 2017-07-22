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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the subject object.
 *
 * @author Virtusa Corporation
 */
public class SubjectDaoImpl extends BaseDaoImpl<Subject> implements SubjectDao {

    /**
     * String attribute for query name by description.
     */
    private static final String QUERY_NAME_BY_DESCRIPTION = "findSubjectByDescription";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(SubjectDaoImpl.class);

    private static final String QUERY_NAME_BY_EXAM = "selectSubjectByExam";

    /** Gets the optional subjects for a given exam. */
    private static final String GET_OPTIONAL_SUBJECT = "getOptionaSubjectByExam";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Subject> searchSubject(String searchDescription) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_DESCRIPTION, searchDescription);
        } catch (DataAccessException e) {
            LOG.error("error occured while searching  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * Gets the available subjects list.
     *
     * @param selectedSubjectsIdList the selected subjects id list
     * @return the available subjects list
     * @throws AkuraAppException the akura app exception
     */
    @SuppressWarnings("unchecked")
    public List<Subject> getAvailableSubjectsList(List<Integer> selectedSubjectsIdList) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQueryAndNamedParam("getAvailableSubjectsList",
                    new String[] { "selectedSubjectsIdList" }, new Object[] { selectedSubjectsIdList });
        } catch (DataAccessException e) {
            LOG.error("error occured while getAvailableSubjectsList  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<?> getSubjectListByExam(final int examId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_EXAM, examId);
        } catch (DataAccessException e) {
            LOG.error("error occured while searching  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<?> getOptionalSubjectList(final int examId) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(GET_OPTIONAL_SUBJECT, examId);
        } catch (DataAccessException e) {
            LOG.error("error occured while searching  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}