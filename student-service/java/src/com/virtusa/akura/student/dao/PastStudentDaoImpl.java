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
import com.virtusa.akura.api.dto.PastStudent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the StaffPastService object.
 * 
 * @author Virtusa Corporation
 */
public class PastStudentDaoImpl extends BaseDaoImpl<PastStudent> implements PastStudentDao {
    
    /** The query name for searching past staff services by staff id.  */
    private static final String FIND_PAST_STUDENT_BY_STUDENTID = "findPastStudentByStudentId";
    
    /** The description of the error in SMS error log. */
    private static final String SEARCHING_PAST_STUDENT_FOR_A_GIVEN_STUDENTID 
    = "Searching Past Student for a given Student Id";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PastStudentDaoImpl.class);
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")  
    
    public List<PastStudent> getPastStudentList(int studentId) throws AkuraAppException{
        
        try {
            return getHibernateTemplate().findByNamedQuery(
                    FIND_PAST_STUDENT_BY_STUDENTID, studentId);
        } catch (DataAccessException e) {
            LOG.debug(SEARCHING_PAST_STUDENT_FOR_A_GIVEN_STUDENTID);
          throw new AkuraAppException(
                  AkuraConstant.DB_CONNECTION_ERROR, e);
      }
        
    }

}
