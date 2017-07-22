/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;

import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public class StudentSuspendDaoImpl extends BaseDaoImpl<SuspendStudent> implements StudentSuspendDao {
    
    /** The query name for searching past staff services by staff id. */
    private static final String FIND_SUSPENDED_STUDENT_DETAILS_BY_STUDENTID = "findSuspendedStudentByStudentId";
    
    /** The description of the error in SMS error log. */
    private static final String SEARCHING_SUSPENDED_STUDENT_DETAILS_FOR_A_GIVEN_STUDENTID =
            "Searching Suspended Student Details for a given Staff Id";
    
    /** The query name for searching latest student suspended record by student id. */
    private static final String ERROR_IN_SEARCHING_LATEST_SUSPENDED_RECORD =
            "Error in Retrieveing latest suspended student details by student Id.";
    
    /** The error message for failure in retrieval of latest student suspended record. */
    private static final String GET_LATEST_STUDENT_SUSPEND_RECORD_BY_STUDENT_ID =
            "getLatestStudentSuspendRecordByStudentId";
    
    /**
     * Key representing a DB Connection error.
     */
    public static final String DB_CONNECTION_ERROR = "GEN.DB.CONN.ERROR";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentSuspendDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SuspendStudent> getSuspendedSudentDetailList(int studentId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_SUSPENDED_STUDENT_DETAILS_BY_STUDENTID, studentId);
        } catch (DataAccessException e) {
            LOG.debug(SEARCHING_SUSPENDED_STUDENT_DETAILS_FOR_A_GIVEN_STUDENTID);
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SuspendStudent> getLatestStudentSuspendRecordByStudentId(int studentId) throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(GET_LATEST_STUDENT_SUSPEND_RECORD_BY_STUDENT_ID, studentId,
                    studentId);
        } catch (DataAccessException e) {
            LOG.error(ERROR_IN_SEARCHING_LATEST_SUSPENDED_RECORD + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
