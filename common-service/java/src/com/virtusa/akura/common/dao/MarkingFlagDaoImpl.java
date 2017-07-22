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

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.MarkingFlag;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for MarkingFlagDao interface. It handles all the persistent related
 * functionalities for the MarkingFlag object.
 * 
 * @author Virtusa Corporation
 */
public class MarkingFlagDaoImpl extends BaseDaoImpl<MarkingFlag> implements MarkingFlagDao {
    
    /** Represents the query name for the marking completion status for all status for the class grade. */
    private static final String IS_EXIST_MARKING_COMPLETED = "isExistMarkingCompleted";

    /** Represents the query name for the marking completion status for the class grade. */
    private static final String FIND_MARKING_COMPLETION_STATUS = "findMarkingCompletionStatus";
    
    /** Represents the query name for the marking completion status for the grade. */
    private static final String FIND_MARKING_COMPLETION_FOR_GRADE = "findMarkingCompletionForGrade";
    
    /** Constant to hold findMarkingFlag query name. */
    private static final String FIND_MARKING_FLAG = "findMarkingFlag";
    
    /** Constant to hold isMarkingCompletedForTerm query name. */
    private static final String IS_MARKING_COMPLETED_FOR_TERM = "isMarkingCompletedForTerm";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean isMarkingCompletedForTerm(int classGradeId, int termId, int year) throws AkuraAppException {
    
        try {
            boolean isMarkingCompleted = false;
            List<Boolean> list =
                    getHibernateTemplate().findByNamedQuery(IS_MARKING_COMPLETED_FOR_TERM, classGradeId, termId, year);
            if (list.size() > 0) {
                isMarkingCompleted = list.get(0);
            }
            return isMarkingCompleted;
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public MarkingFlag findMarkingFlag(int classGradeId, int termId, Date year) throws AkuraAppException {
    
        MarkingFlag markingFlag = null;
        try {
            List<MarkingFlag> list =
                    getHibernateTemplate().findByNamedQuery(FIND_MARKING_FLAG, classGradeId, termId, year);
            
            if (list.size() > 0) {
                markingFlag = list.get(0);
            }
            return markingFlag;
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public List<Boolean> isMarkingCompleted(final int clsGradeId, final Date year, final boolean markingStatus)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_MARKING_COMPLETION_STATUS, clsGradeId, year,
                    markingStatus);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /** {@inheritDoc} */
    public List<Boolean> findMarkingStatusForGrade(final int gradeId, final int termId, final Date currentYear)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_MARKING_COMPLETION_FOR_GRADE, gradeId, termId,
                    currentYear);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<Boolean> isExistMarkingCompleted(int clsGradeId, Date currentYear) throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(IS_EXIST_MARKING_COMPLETED, clsGradeId,
                    currentYear);
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
